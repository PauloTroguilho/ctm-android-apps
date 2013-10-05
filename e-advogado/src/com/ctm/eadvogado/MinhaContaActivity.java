package com.ctm.eadvogado;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpStatus;

import android.content.Intent;
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
import com.ctm.eadvogado.endpoints.compraEndpoint.model.WrappedBoolean;
import com.ctm.eadvogado.endpoints.usuarioEndpoint.model.Usuario;
import com.ctm.eadvogado.tasks.AutenticarUsuarioTask;
import com.ctm.eadvogado.util.Consts;
import com.ctm.eadvogado.util.EndpointUtils;
import com.ctm.eadvogado.util.MessageUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class MinhaContaActivity extends SlidingActivity {
	
	private CompraEndpoint compraEndpoint;
	
	private AutenticarUsuarioTask carregarMinhaContaTask = null;
	private EfetuarCompraTask efetuarCompraTask = null;
	private ConfirmarCompraTask confirmarCompraTask = null;
	private CancelarContaPremiumTask cancelarContaPremiumTask = null;
	
	private EAdvogadoDbHelper dbHelper;
	
	// SKUs for our products: the premium upgrade (non-consumable) and gas (consumable)
    static final String SKU_PROCESSOS_10 = "processos_10";
    static final String SKU_PROCESSOS_25 = "processos_25";
    static final String SKU_PROCESSOS_100 = "processos_100";
    static final String SKU_PROCESSOS_ILIMITADOS = "processos_ilimitados";
     
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
		compraEndpoint = EndpointUtils.initCompraEndpoint();
		
		spinnerPacotes = (Spinner) findViewById(R.id.spinnerPacotes);
		btContaPremium = (Button) findViewById(R.id.buttonContaPremium);
		btContaPremium.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doEfetuarCompra(SKU_PROCESSOS_ILIMITADOS);
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

		doCarregarMinhaConta();
		setUpInAppBilling();
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
            boolean isThreadStarted = false;
            Log.d(TAG, "Consulta de produtos foi realizada com sucesso.");
			// Do we have the premium account?
			/*Purchase premiumPurchase = inventory.getPurchase(SKU_CONTA_PREMIUM);
			if (premiumPurchase != null && premiumPurchase.getPurchaseState() == IabHelper.BILLING_RESPONSE_RESULT_OK) {
				// confirmar compra de conta premium
				doConfirmarCompra(premiumPurchase);
				isThreadStarted = true;
			} else {
				String tipoConta = preferences.getString(PreferencesActivity.PREFS_KEY_TIPO_CONTA, Consts.TIPO_CONTA_BASICA);
				if (tipoConta.equals(Consts.TIPO_CONTA_PREMIUM)) {
					doCancelarContaPremium();
					isThreadStarted = true;
				}
			}*/
			String[] skus = new String[] {SKU_PROCESSOS_10, SKU_PROCESSOS_25, SKU_PROCESSOS_100, SKU_PROCESSOS_ILIMITADOS};
			for (String sku : skus) {
				// Check for gas delivery -- if we own gas, we should fill up the tank immediately
	            Purchase purchase = inventory.getPurchase(sku);
	            if (purchase == null)
	            	continue;

	            Log.d(TAG, String.format("O produto %s foi adquirido. Confirmando compra.", sku));
	            doConfirmarCompra(purchase);
	            isThreadStarted = true;
			}
			if (!isThreadStarted) {
				setSupportProgressBarIndeterminateVisibility(false);
	    		setControlsEnabled(true);
			}
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
					Purchase purchase = purchases.get(i);
					Log.d(TAG, "Compra realizada com sucesso: " + purchase);
					if (purchase.getSku().equals(SKU_PROCESSOS_ILIMITADOS)) {
						preferences.edit().putString(
	    					PreferencesActivity.PREFS_KEY_TIPO_CONTA, Consts.TIPO_CONTA_PREMIUM).commit();
	    				isContaPremium = true;
					}
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
				if (purchase.getSku().equals(SKU_PROCESSOS_ILIMITADOS)) {
					preferences.edit().putString(
    					PreferencesActivity.PREFS_KEY_TIPO_CONTA, Consts.TIPO_CONTA_PREMIUM).commit();
    				isContaPremium = true;
				}
				doCarregarMinhaConta();
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
            if (result.isSuccess()) {
            	Log.i(TAG, "Compra realizada com sucesso: " + result);
            	// Faz a confirmação e consume o item.
                doConfirmarCompra(purchase);
            } else {
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
    	if (PreferencesActivity.isContaPremium(this)) {
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
    	
    	String mensagem = null;
    	String sku = null;

		@Override
		protected Compra doInBackground(String... skus) {
			sku = skus[0];
			Compra compra = null;
			int tries = 3;
			int attempt = 0;
			while (attempt < tries) {
				try {
					compra = compraEndpoint
							.gerarCompraPendente(
									PreferencesActivity
											.getEmail(MinhaContaActivity.this),
									PreferencesActivity
											.getSenha(MinhaContaActivity.this),
									sku).execute();
					if (compra != null)
						break;
				} catch(GoogleJsonResponseException e) {
					Log.e(TAG, "Erro ao executar a operação!", e);
					mensagem = (e.getDetails() != null && e.getDetails() .getMessage() != null) ? 
							e.getDetails().getMessage() : getString(R.string.msg_erro_operacao_nao_realizada);
				} catch (IOException e) {
					Log.e(TAG, "Erro de comunicação ao executar a operação!", e);
					mensagem = getString(R.string.msg_erro_comunicacao_op_nao_realizada);
				}
				attempt++;
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
	        	if (mensagem.length() == 0) {
					Toast.makeText(MinhaContaActivity.this,
							R.string.msg_erro_inesperado,
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(MinhaContaActivity.this,
							mensagem, Toast.LENGTH_LONG).show();
				}
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
    	
    	Purchase purchase = null;
    	String mensagem = null;
    	
		@Override
		protected Compra doInBackground(Purchase... params) {
			purchase = params[0];
			Log.d(TAG, String.format(
					"Confirmando compra. sku: %s, payload: %s, token: %s",
					purchase.getSku(), purchase.getDeveloperPayload(),
					purchase.getToken()));
			Compra compra = null;
			int tries = 3;
			int attempt = 0;
			while (attempt < tries) {
				try {
					compra = compraEndpoint
							.confirmarCompra(
									PreferencesActivity
											.getEmail(MinhaContaActivity.this),
									PreferencesActivity
											.getSenha(MinhaContaActivity.this),
									purchase.getSku(),
									purchase.getDeveloperPayload(),
									purchase.getToken(), purchase.getOrderId())
							.execute();
					if (compra != null)
						break;
				} catch(GoogleJsonResponseException e) {
					Log.e(TAG, "Erro ao executar a operação!", e);
					mensagem = (e.getDetails() != null && e.getDetails() .getMessage() != null) ? 
							e.getDetails().getMessage() : getString(R.string.msg_erro_operacao_nao_realizada);
				} catch (IOException e) {
					Log.e(TAG, "Erro de comunicação ao executar a operação!", e);
					mensagem = getString(R.string.msg_erro_comunicacao_op_nao_realizada);
				}
				attempt++;
			}
			return compra;
		}
		
		@Override
		protected void onPostExecute(Compra compra) {
			if (compra != null) {
				if (compra.getSituacao().equals(Consts.SITUACAO_COMPRA_CONFIMADA)) {
					if (purchase.getSku().equals(SKU_PROCESSOS_10)
	    					|| purchase.getSku().equals(SKU_PROCESSOS_25)
	    					|| purchase.getSku().equals(SKU_PROCESSOS_100)
	    					|| purchase.getSku().equals(SKU_PROCESSOS_ILIMITADOS)) {
	                	Log.d(TAG, String.format("Comprou o produto %s. Registrando compra", purchase.getSku()));
	                	mHelper.consumeAsync(purchase, mConsumeFinishedListener);
	                	if (purchase.getSku().equals(SKU_PROCESSOS_ILIMITADOS)) {
		                    MessageUtils.alert("Obrigado por adquirir a conta premium!", MinhaContaActivity.this);
		                } else {
		                	MessageUtils.alert("Obrigado pela realização da compra!", MinhaContaActivity.this);
		                }
	                } else {
	                    Log.e(TAG, "SKU invalido");
	                    throw new RuntimeException("Compra confirmada e SKU invalido");
	                }
				} else {
					MessageUtils.alert("Desculpe! Não foi possível confirmar a compra. Tente novamente em alguns minutos.", MinhaContaActivity.this);
				}
            } else {
            	if (mensagem.length() == 0) {
            		MessageUtils.alert(R.string.msg_erro_inesperado, MinhaContaActivity.this);
				} else {
					MessageUtils.alert(mensagem, MinhaContaActivity.this);
				}
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
				wBool = compraEndpoint.cancelarContaPremium(
						PreferencesActivity.getEmail(MinhaContaActivity.this),
						PreferencesActivity.getSenha(MinhaContaActivity.this))
						.execute();
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
	
	/**
	 * @return
	 */
	private boolean isContaPremium() {
		return PreferencesActivity.isContaPremium(this);
	}

	/**
	 * Carrega os dados da minha conta.
	 */
	private void doCarregarMinhaConta() {
		setSupportProgressBarIndeterminateVisibility(true);
		setControlsEnabled(false);
		carregarMinhaContaTask = new AutenticarUsuarioTask(this) {
			@Override
			protected void onPostExecute(Usuario result) {
				if (result != null) {
					if (!result.getTipoConta().equals(PreferencesActivity.getTipoConta(MinhaContaActivity.this))){
						preferences.edit()
							.putString(
								PreferencesActivity.PREFS_KEY_TIPO_CONTA,
								result.getTipoConta()).commit();
					}
					tvCategoria.setText(result.getTipoConta());
					tvQtdeCadastrados.setText(dbHelper.selectProcessosCount() + "");
					if (isContaPremium()){
						tvQtdeDisponivel.setText(getString(R.string.qtd_ilimitada));
						btContaPremium.setEnabled(false);
					} else {
						tvQtdeDisponivel.setText(result.getSaldo().toString());
					}
				} else {
					if (getErrorMessage().length() == 0) {
						alert(R.string.msg_erro_inesperado);
					} else {
						alert(getErrorMessage());
					}
				}
				carregarMinhaContaTask = null;
				setSupportProgressBarIndeterminateVisibility(false);
				setControlsEnabled(true);
			}
			@Override
			protected void onCancelled(Usuario result) {
				carregarMinhaContaTask = null;
				setSupportProgressBarIndeterminateVisibility(false);
				setControlsEnabled(true);
			}
		};
		carregarMinhaContaTask.execute(PreferencesActivity.getEmail(MinhaContaActivity.this), PreferencesActivity.getSenha(MinhaContaActivity.this));
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
