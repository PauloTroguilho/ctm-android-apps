package com.ctm.eadvogado;

import java.util.List;
import java.util.Locale;

import org.apache.http.HttpStatus;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.ctm.eadvogado.billingutils.IabHelper;
import com.ctm.eadvogado.billingutils.IabResult;
import com.ctm.eadvogado.billingutils.Inventory;
import com.ctm.eadvogado.billingutils.Purchase;
import com.ctm.eadvogado.db.EAdvogadoDbHelper;
import com.ctm.eadvogado.endpoints.compraEndpoint.CompraEndpoint;
import com.ctm.eadvogado.endpoints.compraEndpoint.model.Compra;
import com.ctm.eadvogado.endpoints.compraEndpoint.model.WrappedBoolean;
import com.ctm.eadvogado.endpoints.usuarioEndpoint.UsuarioEndpoint;
import com.ctm.eadvogado.endpoints.usuarioEndpoint.model.Usuario;
import com.ctm.eadvogado.util.Consts;
import com.ctm.eadvogado.util.EndpointUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class MinhaContaActivity extends SlidingActivity {
	
	private static Usuario USUARIO;
	private UsuarioEndpoint usuarioEndpoint;
	private CompraEndpoint compraEndpoint;
	private CarregarMinhaContaTask minhaContaTask = null;
	private EfetuarCompraTask efetuarCompraTask = null;
	private ConfirmarCompraTask confirmarCompraTask = null;
	private CancelarContaPremiumTask cancelarContaPremiumTask = null;
	
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
	
	private boolean isOnPurchase = false;
	
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
				doEfetuarCompra(SKU_CONTA_PREMIUM);
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
					doEfetuarCompra(sku);
				} else {
					alert("Selecione um dos pacotes!");
				}
			}
		});
		
		tvQtdeCadastrados = (TextView) findViewById(R.id.textViewQtdeCadastrada);
		tvQtdeDisponivel = (TextView) findViewById(R.id.textViewQtdeDisponivel);
		tvCategoria = (TextView) findViewById(R.id.textViewCategoriaConta);
		
		setUpInAppBilling();
		
		doCarregarMinhaConta();
	}
	
	private String getEmail() {
		return preferences.getString(PreferencesActivity.PREFS_KEY_EMAIL, "");
	}
	private String getSenha() {
		return preferences.getString(PreferencesActivity.PREFS_KEY_SENHA, "");
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
			if (premiumPurchase != null) {
				// confirmar compra de conta premium
				doConfirmarCompra(premiumPurchase);
			} else {
				String tipoConta = preferences.getString(PreferencesActivity.PREFS_KEY_TIPO_CONTA, Consts.TIPO_CONTA_BASICA);
				if (tipoConta.equals(Consts.TIPO_CONTA_PREMIUM)) {
					doCancelarContaPremium();
				}
			}
			String[] skus = new String[] {SKU_PROCESSOS_10, SKU_PROCESSOS_25, SKU_PROCESSOS_100};
			for (String sku : skus) {
				// Check for gas delivery -- if we own gas, we should fill up the tank immediately
	            Purchase purchase = inventory.getPurchase(sku);
	            if (purchase == null)
	            	continue;

	            Log.d(TAG, String.format("O produto %s foi adquirido. Confirmando compra.", sku));
	            doConfirmarCompra(purchase);
			}
            setSupportProgressBarIndeterminateVisibility(false);
    		setControlsEnabled(true);
            Log.d(TAG, "Consulta inicial das compras finalizada.");
        }
    };
    
    IabHelper.OnConsumeMultiFinishedListener mConsumeMultiFinishedListener = new IabHelper.OnConsumeMultiFinishedListener() {
		@Override
		public void onConsumeMultiFinished(List<Purchase> purchases,
				List<IabResult> results) {
			for (int i = 0; i < results.size(); i++) {
				IabResult iabResult = results.get(i);
				if (iabResult.isSuccess()) {
					Log.d(TAG, "Compra realizada com sucesso: " + purchases.get(i));
				} else {
	            	complain("Houve um erro registrando a compra! Favor reiniciar o aplicativo." + iabResult);
	            }
			}
            setSupportProgressBarIndeterminateVisibility(false);
    		setControlsEnabled(true);
            Log.d(TAG, "Finalizando consumo da compra.");
		}
	};
    
    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumo finalizado. Compra: " + purchase + ", result: " + result);
			if (result.isSuccess()) {
				Log.d(TAG, "Compra realizada com sucesso: " + purchase);
			} else {
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
        	isOnPurchase = false;
            Log.d(TAG, "Compra finalizada: " + result + ", purchase: " + purchase);
            if (result.isFailure()) {
            	Log.e(TAG, "Falha na realização da compra: " + result);
            	if (result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_USER_CANCELED) {
            		alert("O processo de compra foi cancelado!");
            	} else {
            		alert("Desculpe! Não foi possível realizar a compra neste momento!");
            	}
                setSupportProgressBarIndeterminateVisibility(false);
        		setControlsEnabled(true);
                return;
            }
            // Faz a confirmação e consume o item.
            doConfirmarCompra(purchase);
            Log.d(TAG, "Compra realizada com sucesso.");
        }
    };
    
    /**
     * @param sku
     */
    private void doEfetuarCompra(String sku) {
    	if (efetuarCompraTask != null){
    		complain("Desculpe! Já existe um processo de compra em andamento. Aguarde alguns instantes.");
    		return;
    	}
    	if (isContaPremium) {
            complain("Você já possui a conta PREMIUM! Pode cadastrar processos ilimitados.");
            return;
        }
    	setSupportProgressBarIndeterminateVisibility(true);
		setControlsEnabled(false);
		efetuarCompraTask = new EfetuarCompraTask();
		efetuarCompraTask.execute(sku);
    }
    
    /**
     * @author Cleber
     *
     */
    public class EfetuarCompraTask extends AsyncTask<String, Void, Compra> {
    	
    	int errorCode = -1;
    	String sku = null;

		@Override
		protected Compra doInBackground(String... skus) {
			sku = skus[0];
			Compra compra = null;
	        try {
				compra = compraEndpoint.gerarCompraPendente(getEmail(), getSenha(), sku).execute();
			} catch(GoogleJsonResponseException e) {
				if ((e.getDetails() != null && e.getDetails().getCode() == HttpStatus.SC_NOT_FOUND)
						|| (e.getContent() != null && e.getContent().toLowerCase(Locale.ENGLISH).contains("not found"))) {
					errorCode = HttpStatus.SC_NOT_FOUND;
				} else if ((e.getDetails() != null && e.getDetails().getCode() == HttpStatus.SC_UNAUTHORIZED)
						|| (e.getContent() != null && e.getContent().toLowerCase(Locale.ENGLISH).contains("unauthorized"))) {
					errorCode = HttpStatus.SC_UNAUTHORIZED;
				} else if ((e.getDetails() != null && e.getDetails().getCode() == HttpStatus.SC_BAD_REQUEST)
						|| (e.getContent() != null && e.getContent().toLowerCase(Locale.ENGLISH).contains("bad request"))) {
					errorCode = HttpStatus.SC_BAD_REQUEST;
				}
			} catch (Exception e) {
				Log.e(TAG, "Falha ao realizar login.", e);
			}
			return compra;
		}
		
		@Override
		protected void onPostExecute(Compra compra) {
			if (compra != null) {
				mHelper.launchPurchaseFlow(MinhaContaActivity.this, sku,
						RC_REQUEST, mPurchaseFinishedListener,
						compra.getPayload());
				isOnPurchase = true;
	        } else {
	        	String msg = null;
	        	switch (errorCode) {
					case HttpStatus.SC_NOT_FOUND:
						msg = "Desculpe! OS dados informados não foram encontrados no servidor.";
						break;
					case HttpStatus.SC_UNAUTHORIZED:
						msg = "Usuário e/ou senha inválidos.";
						break;
					case HttpStatus.SC_BAD_REQUEST:
						msg = "O SKU informado é válido.";
						break;
					default:
						msg = "Desculpe! Não foi possível realizar a compra neste momento. Tente novamente em alguns minutos.";
						break;
				}
	        	alert(msg);
	        }
			efetuarCompraTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
		}
		
		@Override
		protected void onCancelled(Compra result) {
			efetuarCompraTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
		}
    	
    }
    
    /**
     * @param purchase
     */
    private void doConfirmarCompra(Purchase purchase) {
    	if (confirmarCompraTask != null){
    		complain("Desculpe! Já existe um processo de confirmação de compra em andamento. Aguarde alguns instantes.");
    		return;
    	}
    	setSupportProgressBarIndeterminateVisibility(true);
		setControlsEnabled(false);
		confirmarCompraTask = new ConfirmarCompraTask();
		confirmarCompraTask.execute(purchase);
    }
    
    /**
     * 
     */
    private void doCancelarContaPremium() {
    	if (cancelarContaPremiumTask != null){
    		complain("Desculpe! Já existe um processo de cancelamento de compra em andamento. Aguarde alguns instantes.");
    		return;
    	}
    	setSupportProgressBarIndeterminateVisibility(true);
		setControlsEnabled(false);
		cancelarContaPremiumTask = new CancelarContaPremiumTask();
		cancelarContaPremiumTask.execute((Void) null);
    }
    
    
    /**
     * @author Cleber
     *
     */
    public class ConfirmarCompraTask extends AsyncTask<Purchase, Void, Compra> {
    	
    	int errorCode = -1;
    	Purchase purchase = null;
    	
		@Override
		protected Compra doInBackground(Purchase... params) {
			purchase = params[0];
			Log.d(TAG, String.format(
					"Confirmando compra. sku: %s, payload: %s, token: %s",
					purchase.getSku(), purchase.getDeveloperPayload(),
					purchase.getToken()));
			Compra compra = null;
	        try {
				compra = compraEndpoint.confirmarCompra(getEmail(),
						getSenha(), purchase.getSku(),
						purchase.getDeveloperPayload(), purchase.getToken(), purchase.getOrderId()).execute();
			} catch(GoogleJsonResponseException e) {
				if ((e.getDetails() != null && e.getDetails().getCode() == HttpStatus.SC_NOT_FOUND)
						|| (e.getContent() != null && e.getContent().toLowerCase(Locale.ENGLISH).contains("not found"))) {
					errorCode = HttpStatus.SC_NOT_FOUND;
				} else if ((e.getDetails() != null && e.getDetails().getCode() == HttpStatus.SC_UNAUTHORIZED)
						|| (e.getContent() != null && e.getContent().toLowerCase(Locale.ENGLISH).contains("unauthorized"))) {
					errorCode = HttpStatus.SC_UNAUTHORIZED;
				} else if ((e.getDetails() != null && e.getDetails().getCode() == HttpStatus.SC_BAD_REQUEST)
						|| (e.getContent() != null && e.getContent().toLowerCase(Locale.ENGLISH).contains("bad request"))) {
					errorCode = HttpStatus.SC_BAD_REQUEST;
				}
			} catch (Exception e) {
				Log.e(TAG, "Falha ao realizar login.", e);
			}
			return compra;
		}
		
		@Override
		protected void onPostExecute(Compra compra) {
			if (compra != null) {
				if (compra.getSituacao().equals(Consts.SITUACAO_COMPRA_CONFIMADA)) {
					if (purchase.getSku().equals(SKU_PROCESSOS_10)
	    					|| purchase.getSku().equals(SKU_PROCESSOS_25)
	    					|| purchase.getSku().equals(SKU_PROCESSOS_100)) {
	                	Log.d(TAG, String.format("Comprou o produto %s. Registrando compra", purchase.getSku()));
	                	alert("Obrigado pela realização da compra!");
	                	mHelper.consumeAsync(purchase, mConsumeFinishedListener);
	                } else if (purchase.getSku().equals(SKU_CONTA_PREMIUM)) {
	                    // bought the infinite gas subscription
	                    Log.d(TAG, "Fez o upgrade para conta premium.");
	                    preferences.edit().putString(
	    						PreferencesActivity.PREFS_KEY_TIPO_CONTA, USUARIO.getTipoConta()).commit();
	    				isContaPremium = true;
	                    alert("Obrigado por adquirir a conta premium!");
	                }
					doCarregarMinhaConta();
				} else {
					alert("Desculpe! Não foi possível confirmar a compra. Tente novamente em alguns minutos.");
				}
            } else {
            	String msg = null;
	        	switch (errorCode) {
					case HttpStatus.SC_NOT_FOUND:
						msg = "Desculpe! Os dados informados não foram encontrados no servidor.";
						break;
					case HttpStatus.SC_UNAUTHORIZED:
						msg = "Usuário e/ou senha inválidos.";
						break;
					case HttpStatus.SC_BAD_REQUEST:
						msg = "O SKU informado é válido.";
						break;
					default:
						msg = "Desculpe! Não foi possível realizar a compra neste momento. Tente novamente em alguns minutos.";
						break;
				}
	        	alert(msg);
            }
			confirmarCompraTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
		}
		
		@Override
		protected void onCancelled(Compra result) {
			confirmarCompraTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
		}
    	
    }
    
    public class CancelarContaPremiumTask extends AsyncTask<Void, Void, Compra> {
    	
    	int errorCode = -1;
    	WrappedBoolean wBool = null;
    	
		@Override
		protected Compra doInBackground(Void... params) {
			Log.d(TAG, "Cancelando conta premium.");
			Compra compra = null;
	        try {
	        	wBool = compraEndpoint.cancelarContaPremium(getEmail(), getSenha()).execute();
			} catch(GoogleJsonResponseException e) {
				Log.e(TAG, "Falha na comunicação com o server.", e);
				if ((e.getDetails() != null && e.getDetails().getCode() == HttpStatus.SC_NOT_FOUND)
						|| (e.getContent() != null && e.getContent().toLowerCase(Locale.ENGLISH).contains("not found"))) {
					errorCode = HttpStatus.SC_NOT_FOUND;
				} else if ((e.getDetails() != null && e.getDetails().getCode() == HttpStatus.SC_UNAUTHORIZED)
						|| (e.getContent() != null && e.getContent().toLowerCase(Locale.ENGLISH).contains("unauthorized"))) {
					errorCode = HttpStatus.SC_UNAUTHORIZED;
				}
			} catch (Exception e) {
				Log.e(TAG, "Falha ao realizar login.", e);
			}
			return compra;
		}
		
		@Override
		protected void onPostExecute(Compra compra) {
			if (wBool != null) {
				if (wBool.getValue().booleanValue()) {
					Log.d(TAG, "Conta premium foi cancelada.");
					doCarregarMinhaConta();
					alert("Sua assinatura da conta PREMIUM foi cancelada!");
				}
            } else {
            	String msg = null;
	        	switch (errorCode) {
					case HttpStatus.SC_NOT_FOUND:
						msg = "Desculpe! Os dados informados não foram encontrados no servidor.";
						break;
					case HttpStatus.SC_UNAUTHORIZED:
						msg = "Usuário e/ou senha inválidos.";
						break;
					default:
						msg = "Desculpe! Não foi possivel se comunicar com nossos servidores. Verifique seu acesso a internet!";
						break;
				}
	        	alert(msg);
            }
			cancelarContaPremiumTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
		}
		
		@Override
		protected void onCancelled(Compra result) {
			cancelarContaPremiumTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
		}
    	
    }
    
    
    
	void complain(String message) {
        Log.e(TAG, "**** MinhaConta Error: " + message);
        alert(message);
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
	private void doCarregarMinhaConta() {
		setSupportProgressBarIndeterminateVisibility(true);
		setControlsEnabled(false);
		minhaContaTask = new CarregarMinhaContaTask();
		minhaContaTask.execute((Void) null);
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
	 * the user.
	 */
	public class CarregarMinhaContaTask extends AsyncTask<Void, Void, Usuario> {
		
		private int errorCode = -1;
		
		@Override
		protected Usuario doInBackground(Void... params) {
			Usuario usuario = null;
			try {
				usuario = usuarioEndpoint.autenticar(getEmail(), getSenha()).execute();
			} catch(GoogleJsonResponseException e) {
				if ((e.getDetails() != null && e.getDetails().getCode() == HttpStatus.SC_UNAUTHORIZED)
						|| (e.getContent() != null && e.getContent().toLowerCase(Locale.ENGLISH).contains("unauthorized"))) {
					errorCode = HttpStatus.SC_UNAUTHORIZED;
				}
			} catch (Exception e) {
				Log.e(TAG, "Falha ao realizar login.", e);
			}
			return usuario;
		}

		@Override
		protected void onPostExecute(Usuario usuario) {
			if (usuario != null) {
				USUARIO = usuario;
				tvCategoria.setText(usuario.getTipoConta());
				tvQtdeCadastrados.setText((usuario.getProcessos() != null ? usuario.getProcessos().size() : 0) + "");
				tvQtdeDisponivel.setText(usuario.getSaldo().toString());
			} else {
				if (errorCode == HttpStatus.SC_UNAUTHORIZED) {
					alert(getString(R.string.conta_erro_carregando_dados_nao_autorizado));
				} else {
					alert(getString(R.string.conta_erro_carregando_dados));
				}
			}
			minhaContaTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
		}

		@Override
		protected void onCancelled() {
			minhaContaTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
		}
	}
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

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


}
