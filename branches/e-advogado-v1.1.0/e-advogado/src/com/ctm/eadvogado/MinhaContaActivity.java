package com.ctm.eadvogado;

import java.io.IOException;
import java.util.Locale;

import org.apache.http.HttpStatus;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ctm.eadvogado.billingutils.IabHelper;
import com.ctm.eadvogado.billingutils.IabResult;
import com.ctm.eadvogado.billingutils.Inventory;
import com.ctm.eadvogado.billingutils.Purchase;
import com.ctm.eadvogado.db.EAdvogadoDbHelper;
import com.ctm.eadvogado.endpoints.compraEndpoint.CompraEndpoint;
import com.ctm.eadvogado.endpoints.compraEndpoint.model.Compra;
import com.ctm.eadvogado.endpoints.usuarioEndpoint.UsuarioEndpoint;
import com.ctm.eadvogado.endpoints.usuarioEndpoint.model.Usuario;
import com.ctm.eadvogado.util.Consts;
import com.ctm.eadvogado.util.EndpointUtils;
import com.ctm.eadvogado.util.UserEmailFetcher;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class MinhaContaActivity extends SlidingActivity {
	
	private static Usuario USUARIO;
	private UsuarioEndpoint usuarioEndpoint;
	private CompraEndpoint compraEndpoint;
	private CarregarMinhaContaTask minhaContaTask = null;
	
	private EAdvogadoDbHelper dbHelper;
	
	// SKUs for our products: the premium upgrade (non-consumable) and gas (consumable)
    static final String SKU_PROCESSOS_10 = "processos_10";
    static final String SKU_PROCESSOS_25 = "processos_25";
    static final String SKU_PROCESSOS_100 = "processos_100";
    // SKU for our subscription (conta premium)
    static final String SKU_CONTA_PREMIUM = "conta_premium";
    
    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10001;
	
	// The helper object
    IabHelper mHelper;
	
	private Button btContaPremium;
	private Button btCompraPacote;
	
	private TextView tvQtdeCadastrados;
	private TextView tvQtdeDisponivel;
	private TextView tvCategoria;
	
	private Spinner spinnerPacotes;
	
	private boolean isContaPremium = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_minha_conta);
		
		dbHelper = new EAdvogadoDbHelper(this);
		
		usuarioEndpoint = EndpointUtils.initUsuarioEndpoint();
		compraEndpoint = EndpointUtils.initCompraEndpoint();
		
		
		spinnerPacotes = (Spinner) findViewById(R.id.spinnerPacotes);
		btContaPremium = (Button) findViewById(R.id.buttonContaPremium);
		btContaPremium.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBuyProcessosButtonClicked(SKU_CONTA_PREMIUM);
			}
		});
		btCompraPacote = (Button) findViewById(R.id.buttonCompraPacote);
		btCompraPacote.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int position = spinnerPacotes.getSelectedItemPosition();
				String sku = null;
				switch (position) {
					case 1:
						sku = SKU_PROCESSOS_10;
						break;
					case 2:
						sku = SKU_PROCESSOS_25;
						break;
					case 3:
						sku = SKU_PROCESSOS_100;
						break;
				}
				if (sku != null) {
					onBuyProcessosButtonClicked(sku);
				} else {
					alert("Selecione um dos pacotes!");
				}
			}
		});
		
		tvQtdeCadastrados = (TextView) findViewById(R.id.textViewQtdeCadastrada);
		tvQtdeDisponivel = (TextView) findViewById(R.id.textViewQtdeDisponivel);
		tvCategoria = (TextView) findViewById(R.id.textViewCategoriaConta);
		
		setUpInAppBilling();
		
		doCarregarMinhaConta(true);
	}
	
	private void setUpInAppBilling() {
		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo4oE0XJZUc2OrTAFXswN13u/TjEKLUewEmAyP/Mnq8F9QYNQwKK5vUXlC5a3zQJ/HBPO25cIcfEX2rwMiQKCkBdPUKLx3LRP+M85xrYgFWcOP1GK8HNFyMF2MJZpblxhW2Yx6D36FYFhfzfkpY9eUDNY7rH26p/5xcVsuRwFG0iogvM91WT/YiOe4voj3uw3g9cSmFpGzZ85GT4RG4FcmhhlKyXIVgmaqnks1np/9ijJ/v970HW+iz5e1r2cj6cox0N7ZFSLGZ7G0ZDGREe2xh8K9VaQiJ4JRhYIAOcakQL4rd1OF08LH7wJ5dR0qMAZuW6GbCdxlqb5FCP1Ls9x7wIDAQAB";
		// Create the helper, passing it our context and the public key to verify signatures with
        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);
        
        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Iniciando in-App Billing setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "In-App Billing setup finalizado.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Ocorreu um problema iniciando o in-app billing: " + result);
                    return;
                }

                // Hooray, IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "In-App Billing inicializado com sucesso. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
	}
	
	// Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Consulta de produtos finalizada.");
            if (result.isFailure()) {
                complain("Ocorreu um problema ao consultar seus produtos: " + result);
                return;
            }
            Log.d(TAG, "Consulta de produtos foi realizada com sucesso.");
            
			// Do we have the premium account?
			Purchase premiumPurchase = inventory.getPurchase(SKU_CONTA_PREMIUM);
			isContaPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
			Log.d(TAG, "A conta do usuário é "
					+ (isContaPremium ? Consts.TIPO_CONTA_PREMIUM : Consts.TIPO_CONTA_BASICA));
			
			tvCategoria.setText(isContaPremium ? Consts.TIPO_CONTA_PREMIUM : Consts.TIPO_CONTA_BASICA);

			String tipoConta = preferences.getString(
					PreferencesActivity.PREFS_KEY_TIPO_CONTA,
					Consts.TIPO_CONTA_BASICA);

			if (isContaPremium
					&& tipoConta.equals(Consts.TIPO_CONTA_BASICA)) {
            	try {
					compraEndpoint.confirmarCompraPendente(
							preferences.getString(PreferencesActivity.PREFS_KEY_EMAIL, ""),
							preferences.getString(PreferencesActivity.PREFS_KEY_SENHA, ""), 
							premiumPurchase.getSku(), premiumPurchase.getDeveloperPayload()).execute();
					USUARIO = usuarioEndpoint.autenticar(preferences.getString(PreferencesActivity.PREFS_KEY_EMAIL, ""),
							preferences.getString(PreferencesActivity.PREFS_KEY_SENHA, "")).execute();
					preferences.edit().putString(
						PreferencesActivity.PREFS_KEY_TIPO_CONTA, USUARIO.getTipoConta()).commit();
				} catch (IOException e) {
					Log.d(TAG, "Erro registrando upgrade de compra. Purchase: " + premiumPurchase);
				}
            }
            // Check for gas delivery -- if we own gas, we should fill up the tank immediately
            Purchase processos10Purchase = inventory.getPurchase(SKU_PROCESSOS_10);
            if (processos10Purchase != null && verifyDeveloperPayload(processos10Purchase)) {
                Log.d(TAG, String.format("O produto %s foi adquirido. Consumindo-o.", SKU_PROCESSOS_10));
                mHelper.consumeAsync(processos10Purchase, mConsumeFinishedListener);
                return;
            }
            Purchase processos25Purchase = inventory.getPurchase(SKU_PROCESSOS_25);
            if (processos10Purchase != null && verifyDeveloperPayload(processos25Purchase)) {
                Log.d(TAG, String.format("O produto %s foi adquirido. Consumindo-o.", SKU_PROCESSOS_25));
                mHelper.consumeAsync(processos25Purchase, mConsumeFinishedListener);
                return;
            }
            Purchase processos100Purchase = inventory.getPurchase(SKU_PROCESSOS_100);
            if (processos100Purchase != null && verifyDeveloperPayload(processos100Purchase)) {
            	Log.d(TAG, String.format("O produto %s foi adquirido. Consumindo-o.", SKU_PROCESSOS_100));
                mHelper.consumeAsync(processos100Purchase, mConsumeFinishedListener);
                return;
            }
            setSupportProgressBarIndeterminateVisibility(false);
    		setControlsEnabled(true);
            Log.d(TAG, "Consulta inicial das compras finalizada.");
        }
    };
    
    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumo finalizado. Compra: " + purchase + ", result: " + result);
            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
            if (result.isSuccess()) {
            	try {
            		USUARIO = usuarioEndpoint.autenticar(
							preferences.getString(PreferencesActivity.PREFS_KEY_EMAIL, ""),
							preferences.getString(PreferencesActivity.PREFS_KEY_SENHA, "")).execute();
				} catch (IOException e) {
					dbHelper.inserirLancamento(purchase.getSku(), purchase.getOrderId());
					Log.d(TAG, "Erro registrando a compra. Purchase: " + purchase + ", result: " + result);
				}
            	Long saldo = USUARIO.getSaldo();
            	if (saldo != null) {
            		tvQtdeDisponivel.setText(saldo.toString());
            		alert(String.format("Obrigado pela compra! Seu novo saldo é: %s", saldo));
            	} else {
            		complain("Houve um erro registrando a compra! Favor reiniciar o aplicativo.");
            	}
            }
            else {
            	complain("Houve um erro registrando a compra! Favor reiniciar o aplicativo." + result);
            }
            setSupportProgressBarIndeterminateVisibility(false);
    		setControlsEnabled(true);
            Log.d(TAG, "Finalizando consumo da compra.");
        }
    };
    
 // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Compra finalizada: " + result + ", purchase: " + purchase);
            if (result.isFailure()) {
                complain("Erro realizando a compra: " + result);
                setSupportProgressBarIndeterminateVisibility(false);
        		setControlsEnabled(true);
                return;
            }
            Compra compra = null;
            try {
            	compra = compraEndpoint.confirmarCompraPendente(
                		preferences.getString(PreferencesActivity.PREFS_KEY_EMAIL, ""), 
                		preferences.getString(PreferencesActivity.PREFS_KEY_SENHA, ""), 
                		purchase.getSku(), purchase.getDeveloperPayload()).execute();
            } catch(IOException e) {
            	Log.d(TAG, "Erro ao registrar compra, purchase: " + purchase, e);
            	alert("Não foi possivel confirmar a compra no momento. Favor reiniciar o aplicativo.");
            }
            if (compra != null) {
            	if (purchase.getSku().equals(SKU_PROCESSOS_10)
    					|| purchase.getSku().equals(SKU_PROCESSOS_25)
    					|| purchase.getSku().equals(SKU_PROCESSOS_100)) {
                	// bought the premium upgrade!
                	Log.d(TAG, String.format("Comprou o produto %s. Registrando compra", purchase.getSku()));
                	alert("Obrigado pela realização da compra!");
                	mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                } else if (purchase.getSku().equals(SKU_CONTA_PREMIUM)) {
                    // bought the infinite gas subscription
                    Log.d(TAG, "Fez o upgrade para conta premium.");
                    alert("Obrigado por adquirir a conta premium!");
                    try {
    					USUARIO = usuarioEndpoint.autenticar(
    							preferences.getString(PreferencesActivity.PREFS_KEY_EMAIL, ""),
    							preferences.getString(PreferencesActivity.PREFS_KEY_SENHA, "")).execute();
    					preferences.edit().putString(
    						PreferencesActivity.PREFS_KEY_TIPO_CONTA, USUARIO.getTipoConta()).commit();
    					isContaPremium = true;
    				} catch (IOException e) {
    					Log.d(TAG, "Erro registrando upgrade de compra. Purchase: " + purchase);
    				}
                }
            }
            updateUi();
            setSupportProgressBarIndeterminateVisibility(false);
    		setControlsEnabled(true);
    		
            Log.d(TAG, "Compra realizada com sucesso.");
        }
    };
	
    public void onBuyProcessosButtonClicked(String sku) {
        Log.d(TAG, "Buy gas button clicked.");

        if (isContaPremium) {
            complain("Você já possui a conta PREMIUM! Pode cadastrar processos ilimitados.");
            return;
        }

        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setSupportProgressBarIndeterminateVisibility(true);
		setControlsEnabled(false);
        Log.d(TAG, "Iniciando o processo de compra do produto: " + sku);
        
        /* TODO: for security, generate your payload here for verification. See the comments on 
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use 
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = UserEmailFetcher.getEmail(this) + "_" + SKU_PROCESSOS_10; 
        
        mHelper.launchPurchaseFlow(this, sku, RC_REQUEST, 
                mPurchaseFinishedListener, payload);
    }
	
	
	void complain(String message) {
        Log.e(TAG, "**** MinhaConta Error: " + message);
        alert("Error: " + message);
    }

    protected void updateUi() {
		// TODO Auto-generated method stub
		
	}

	void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }
	
	/**
	 * Carrega os dados da minha conta.
	 */
	private void doCarregarMinhaConta(boolean useCache) {
		setSupportProgressBarIndeterminateVisibility(true);
		setControlsEnabled(false);
		minhaContaTask = new CarregarMinhaContaTask();
		minhaContaTask.execute(useCache);
	}
 	
	/**
	 * @param enabled
	 */
	private void setControlsEnabled(boolean enabled) {
		btContaPremium.setEnabled(enabled);
		btCompraPacote.setEnabled(enabled);
		tvCategoria.setVisibility(enabled ? View.VISIBLE : View.GONE);
		tvQtdeCadastrados.setVisibility(enabled ? View.VISIBLE : View.GONE);
		tvQtdeDisponivel.setVisibility(enabled ? View.VISIBLE : View.GONE);
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class CarregarMinhaContaTask extends AsyncTask<Boolean, Void, Usuario> {
		
		private int errorCode = -1;
		
		@Override
		protected Usuario doInBackground(Boolean... params) {
			Usuario usuario = null;
			boolean useCache = params[0]; 
			if (useCache && USUARIO != null) {
				usuario = USUARIO;
			}
			if (usuario == null) {
				try {
					usuario = usuarioEndpoint.autenticar(
							preferences.getString(PreferencesActivity.PREFS_KEY_EMAIL, ""), 
							preferences.getString(PreferencesActivity.PREFS_KEY_SENHA, "")).execute();
				} catch(GoogleJsonResponseException e) {
					if ((e.getDetails() != null && e.getDetails().getCode() == HttpStatus.SC_UNAUTHORIZED)
							|| (e.getContent() != null && e.getContent().toLowerCase(Locale.ENGLISH).contains("unauthorized"))) {
						errorCode = HttpStatus.SC_UNAUTHORIZED;
					}
				} catch (Exception e) {
					Log.e(TAG, "Falha ao realizar login.", e);
				}
			}
			return usuario;
		}

		@Override
		protected void onPostExecute(Usuario usuario) {
			minhaContaTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
			if (usuario != null) {
				USUARIO = usuario;
				tvCategoria.setText(usuario.getTipoConta());
				tvQtdeCadastrados.setText((usuario.getProcessos() != null ? usuario.getProcessos().size() : 0) + "");
				tvQtdeDisponivel.setText(usuario.getSaldo().toString());
			} else {
				if (errorCode == HttpStatus.SC_UNAUTHORIZED) {
					Toast.makeText(MinhaContaActivity.this,
							R.string.conta_erro_carregando_dados_nao_autorizado,
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(MinhaContaActivity.this,
							R.string.conta_erro_carregando_dados,
							Toast.LENGTH_LONG).show();
				}
				
			}
		}

		@Override
		protected void onCancelled() {
			minhaContaTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
		}
	}
	
	/** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        String validatingPayload = UserEmailFetcher.getEmail(this) + "_" + p.getSku();
        return payload.equals(validatingPayload);
    }

}
