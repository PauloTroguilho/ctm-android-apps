package com.ctm.eadvogado;

import java.util.Locale;

import org.apache.http.HttpStatus;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ctm.eadvogado.endpoints.usuarioEndpoint.UsuarioEndpoint;
import com.ctm.eadvogado.endpoints.usuarioEndpoint.model.Usuario;
import com.ctm.eadvogado.util.EndpointUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class MinhaContaActivity extends SlidingActivity {
	
	private static Usuario USUARIO;
	private UsuarioEndpoint usuarioEndpoint;
	private CarregarMinhaContaTask minhaContaTask = null;
	
	private Button btContaPremium;
	private Button btCompraPacote;
	
	private TextView tvQtdeCadastrados;
	private TextView tvQtdeDisponivel;
	private TextView tvCategoria;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_minha_conta);
		//initAdmobBanner(R.id.adView);
		
		usuarioEndpoint = EndpointUtils.initUsuarioEndpoint();
		btContaPremium = (Button) findViewById(R.id.buttonContaPremium);
		btCompraPacote = (Button) findViewById(R.id.buttonCompraPacote);
		
		tvQtdeCadastrados = (TextView) findViewById(R.id.textViewQtdeCadastrada);
		tvQtdeDisponivel = (TextView) findViewById(R.id.textViewQtdeDisponivel);
		tvCategoria = (TextView) findViewById(R.id.textViewCategoriaConta);
		
		doCarregarMinhaConta(true);
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
				//tvQtdeDisponivel.setText(usuario.getSaldo());
				tvQtdeDisponivel.setText("0");
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

}
