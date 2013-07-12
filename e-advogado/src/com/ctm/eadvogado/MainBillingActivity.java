/* Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ctm.eadvogado;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ctm.eadvogado.billingutils.IabHelper;
import com.ctm.eadvogado.billingutils.IabResult;
import com.ctm.eadvogado.billingutils.Inventory;
import com.ctm.eadvogado.billingutils.Purchase;
import com.ctm.eadvogado.db.EAdvogadoContract;
import com.ctm.eadvogado.db.EAdvogadoDbHelper;
import com.ctm.eadvogado.util.UserEmailFetcher;

/**
 * @author Cleber
 *
 */
public class MainBillingActivity extends Activity {
	
    private static final String LANCAMENTO_CREDITO_PREMIUM = "CREDITO_PREMIUM";
	private static final String LANCAMENTO_CREDITO = "CREDITO";

	// Debug tag, for logging
    static final String TAG = "e-Advogado";
    
    private EAdvogadoDbHelper dbHelper;

    // Does the user have the premium upgrade?
    boolean mSubscribedToContaPremium = false;
    
    // SKUs for our products: the premium upgrade (non-consumable) and gas (consumable)
    static final String SKU_PROCESSOS_10 = "processos_10";
    static final String SKU_PROCESSOS_100 = "processos_100";
    
    // SKU for our subscription (infinite gas)
    static final String SKU_CONTA_PREMIUM = "conta_premium";

    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10001;

    // Current amount of gas in tank, in units
    long mQtdeProcessosDisponiveis;

    // The helper object
    IabHelper mHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new EAdvogadoDbHelper(this);

        // load game data
        loadData();

        /* base64EncodedPublicKey should be YOUR APPLICATION'S PUBLIC KEY
         * (that you got from the Google Play developer console). This is not your
         * developer public key, it's the *app-specific* public key.
         *
         * Instead of just storing the entire literal string here embedded in the
         * program,  construct the key at runtime from pieces or
         * use bit manipulation (for example, XOR with some other string) to hide
         * the actual key.  The key itself is not secret information, but we don't
         * want to make it easy for an attacker to replace the public key with one
         * of their own and then fake messages from the server.
         */
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo4oE0XJZUc2OrTAFXswN13u/TjEKLUewEmAyP/Mnq8F9QYNQwKK5vUXlC5a3zQJ/HBPO25cIcfEX2rwMiQKCkBdPUKLx3LRP+M85xrYgFWcOP1GK8HNFyMF2MJZpblxhW2Yx6D36FYFhfzfkpY9eUDNY7rH26p/5xcVsuRwFG0iogvM91WT/YiOe4voj3uw3g9cSmFpGzZ85GT4RG4FcmhhlKyXIVgmaqnks1np/9ijJ/v970HW+iz5e1r2cj6cox0N7ZFSLGZ7G0ZDGREe2xh8K9VaQiJ4JRhYIAOcakQL4rd1OF08LH7wJ5dR0qMAZuW6GbCdxlqb5FCP1Ls9x7wIDAQAB";
        
        // Some sanity checks to see if the developer (that's you!) really followed the
        // instructions to run this sample (don't put these checks on your app!)
        if (base64EncodedPublicKey.contains("CONSTRUCT_YOUR")) {
            throw new RuntimeException("Please put your app's public key in MainBillingActivity.java. See README.");
        }
        if (getPackageName().startsWith("com.example")) {
            throw new RuntimeException("Please change the sample's package name! See README.");
        }
        
        // Create the helper, passing it our context and the public key to verify signatures with
        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        
        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                // Hooray, IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");
            
            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */
            
            // Do we have the premium account?
            Purchase premiumPurchase = inventory.getPurchase(SKU_CONTA_PREMIUM);
            mSubscribedToContaPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
            Log.d(TAG, "User is " + (mSubscribedToContaPremium ? "PREMIUM" : "NOT PREMIUM"));
            
            // Check for gas delivery -- if we own gas, we should fill up the tank immediately
            Purchase processos10Purchase = inventory.getPurchase(SKU_PROCESSOS_10);
            if (processos10Purchase != null && verifyDeveloperPayload(processos10Purchase)) {
                Log.d(TAG, "We have 10 processos. Consuming it.");
                mHelper.consumeAsync(inventory.getPurchase(SKU_PROCESSOS_10), mConsumeFinishedListener);
                return;
            }
            
            // Check for gas delivery -- if we own gas, we should fill up the tank immediately
            Purchase processos100Purchase = inventory.getPurchase(SKU_PROCESSOS_100);
            if (processos100Purchase != null && verifyDeveloperPayload(processos100Purchase)) {
                Log.d(TAG, "We have 100 processos. Consuming it.");
                mHelper.consumeAsync(inventory.getPurchase(SKU_PROCESSOS_100), mConsumeFinishedListener);
                return;
            }

            updateUi();
            setWaitScreen(false);
            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };

    // User clicked the "Buy Gas" button
    public void onBuyProcessos10ButtonClicked(View arg0) {
        Log.d(TAG, "Buy gas button clicked.");

        if (mSubscribedToContaPremium) {
            complain("No need! You're subscribed to conta premium. Isn't that awesome?");
            return;
        }

        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        Log.d(TAG, "Launching purchase flow for gas.");
        
        /* TODO: for security, generate your payload here for verification. See the comments on 
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use 
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = UserEmailFetcher.getEmail(this) + "_" + SKU_PROCESSOS_10; 
        
        mHelper.launchPurchaseFlow(this, SKU_PROCESSOS_10, RC_REQUEST, 
                mPurchaseFinishedListener, payload);
    }
    
    public void onBuyProcessos100ButtonClicked(View arg0) {
        Log.d(TAG, "Buy gas button clicked.");

        if (mSubscribedToContaPremium) {
            complain("No need! You're subscribed to conta premium. Isn't that awesome?");
            return;
        }

        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        Log.d(TAG, "Launching purchase flow for gas.");
        
        /* TODO: for security, generate your payload here for verification. See the comments on 
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use 
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = UserEmailFetcher.getEmail(this) + "_" + SKU_PROCESSOS_100; 
        
        mHelper.launchPurchaseFlow(this, SKU_PROCESSOS_100, RC_REQUEST, 
                mPurchaseFinishedListener, payload);
    }

    // User clicked the "Upgrade to Premium" button.
    public void onUpgradeAppButtonClicked(View arg0) {
        Log.d(TAG, "Upgrade button clicked; launching purchase flow for upgrade.");
        setWaitScreen(true);
        
        /* TODO: for security, generate your payload here for verification. See the comments on 
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use 
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = UserEmailFetcher.getEmail(this) + "_" + SKU_CONTA_PREMIUM;

        mHelper.launchPurchaseFlow(this, SKU_CONTA_PREMIUM, RC_REQUEST, 
                mPurchaseFinishedListener, payload);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }
    
    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        String validatingPayload = UserEmailFetcher.getEmail(this) + "_" + p.getSku();
        return payload.equals(validatingPayload);
        
        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         * 
         * WARNING: Locally generating a random string when starting a purchase and 
         * verifying it here might seem like a good approach, but this will fail in the 
         * case where the user purchases an item on one device and then uses your app on 
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         * 
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         * 
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on 
         *    one device work on other devices owned by the user).
         * 
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */
        //return true;
    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Compra finalizada: " + result + ", purchase: " + purchase);
            if (result.isFailure()) {
                complain("Erro realizando a compra: " + result);
                setWaitScreen(false);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Erro realizando a compra. Falhou na verificação de autenticidade.");
                setWaitScreen(false);
                return;
            }
            Log.d(TAG, "Compra realizada com sucesso.");

            if (purchase.getSku().equals(SKU_PROCESSOS_10)) {
            	// bought the premium upgrade!
            	Log.d(TAG, "Comprou 10 processos. Creditando... ");
            	alert("Obrigado pela realização da compra!");
            	mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }
            else if (purchase.getSku().equals(SKU_PROCESSOS_100)) {
                // bought the premium upgrade!
            	Log.d(TAG, "Comprou 100 processos. Creditando... ");
            	alert("Obrigado pela realização da compra!");
            	mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            } else if (purchase.getSku().equals(SKU_CONTA_PREMIUM)) {
                // bought the infinite gas subscription
                Log.d(TAG, "Fez o upgrade para conta premium.");
                alert("Obrigado por adquirir a conta premium!");
                mSubscribedToContaPremium = true;
                updateUi();
                setWaitScreen(false);
            }
        }
    };

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);
            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
            if (result.isSuccess()) {
            	int qtde = 0;
            	if (purchase.getSku().equals(SKU_PROCESSOS_10)) {
            		qtde = 10;
                } else if (purchase.getSku().equals(SKU_PROCESSOS_100)) {
                	qtde = 100;
                }
            	if (qtde > 0) {
            		Log.d(TAG, "Registrou o crédito de "+qtde+" processos.");
                    // successfully consumed, so we apply the effects of the item in our
                    // game world's logic, which in our case means filling the gas tank a bit
                    Log.d(TAG, "Consumação efetuada com sucesso. Provisionando.");
                    mQtdeProcessosDisponiveis = dbHelper.selectQtdeProcessosDisponiveis();
                    saveData();
                    alert("You filled 1/4 tank. Your tank is now " + String.valueOf(mQtdeProcessosDisponiveis) + "/4 full!");
            	}
            }
            else {
                complain("Error while consuming: " + result);
            }
            updateUi();
            setWaitScreen(false);
            Log.d(TAG, "End consumption flow.");
        }
    };

    // Drive button clicked. Burn gas!
    public void onDriveButtonClicked(View arg0) {
        Log.d(TAG, "Drive button clicked.");
        /*if (!mSubscribedToInfiniteGas && mQtdeProcessosDisponiveis <= 0) alert("Oh, no! You are out of gas! Try buying some!");
        else {
            if (!mSubscribedToInfiniteGas) --mQtdeProcessosDisponiveis;
            saveData();
            alert("Vroooom, you drove a few miles.");
            updateUi();
            Log.d(TAG, "Vrooom. Tank is now " + mQtdeProcessosDisponiveis);
        }*/
    }
    
    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    // updates UI to reflect model
    public void updateUi() {
        // update the car color to reflect premium status or lack thereof
        /*((ImageView)findViewById(R.id.free_or_premium)).setImageResource(mSubscribedToContaPremium ? R.drawable.premium : R.drawable.free);

        // "Upgrade" button is only visible if the user is not premium
        findViewById(R.id.upgrade_button).setVisibility(mSubscribedToContaPremium ? View.GONE : View.VISIBLE);

        // "Get infinite gas" button is only visible if the user is not subscribed yet
        findViewById(R.id.infinite_gas_button).setVisibility(mSubscribedToInfiniteGas ? 
                View.GONE : View.VISIBLE);

        // update gas gauge to reflect tank status
        if (mSubscribedToInfiniteGas) {
            ((ImageView)findViewById(R.id.gas_gauge)).setImageResource(R.drawable.gas_inf);
        }
        else {
            int index = mQtdeProcessosDisponiveis >= TANK_RES_IDS.length ? TANK_RES_IDS.length - 1 : mQtdeProcessosDisponiveis;
            ((ImageView)findViewById(R.id.gas_gauge)).setImageResource(TANK_RES_IDS[index]);
        }    */    
    }

    // Enables or disables the "please wait" screen.
    void setWaitScreen(boolean set) {
        /*findViewById(R.id.screen_main).setVisibility(set ? View.GONE : View.VISIBLE);
        findViewById(R.id.screen_wait).setVisibility(set ? View.VISIBLE : View.GONE);*/
    }

    void complain(String message) {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    void saveData() {
        
        /*
         * WARNING: on a real application, we recommend you save data in a secure way to
         * prevent tampering. For simplicity in this sample, we simply store the data using a
         * SharedPreferences.
         */
        
        /*SharedPreferences.Editor spe = getPreferences(MODE_PRIVATE).edit();
        spe.putInt("tank", mQtdeProcessosDisponiveis);
        spe.commit();
        Log.d(TAG, "Saved data: tank = " + String.valueOf(mQtdeProcessosDisponiveis));*/
    }

    void loadData() {
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        mQtdeProcessosDisponiveis = sp.getInt("tank", 2);
        Log.d(TAG, "Loaded data: tank = " + String.valueOf(mQtdeProcessosDisponiveis));
    }
}
