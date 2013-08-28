package com.ctm.eadvogado.tasks;

import java.io.IOException;

import android.app.Activity;
import android.util.Log;

import com.ctm.eadvogado.R;
import com.ctm.eadvogado.endpoints.usuarioEndpoint.UsuarioEndpoint;
import com.ctm.eadvogado.endpoints.usuarioEndpoint.model.Usuario;
import com.ctm.eadvogado.util.EndpointUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class LoginTask extends BaseTask<String, Void, Usuario> {
	
	private String mensagem;

	/**
	 * @param parentActivity
	 * @param listener
	 */
	public LoginTask(Activity parentActivity, TaskListener listener) {
		super(parentActivity, listener);
	}

	@Override
	protected Usuario doInBackground(String... params) {
		UsuarioEndpoint usuarioEndpoint = EndpointUtils.initUsuarioEndpoint();
		String email = params[0];
		String senha = params[1];
		Usuario usuario = null;
		mensagem = "";
		
		boolean isSuccess = false;
		int tries = 3;
		int attempt = 0;
		while (attempt < tries && !isSuccess) {
			try {
				usuario = usuarioEndpoint.autenticar(email, senha).execute();
				isSuccess = true;
			} catch(GoogleJsonResponseException e) {
				Log.e(TAG, "Erro ao executar a operação!", e);
				mensagem = (e.getDetails() != null && e.getDetails().getMessage() != null) ? 
						e.getDetails().getMessage() : parentActivity.getString(R.string.msg_erro_operacao_nao_realizada);
				break;
			} catch (IOException e) {
				Log.e(TAG, "Erro de comunicação ao executar a operação!", e);
				mensagem = parentActivity.getString(R.string.msg_erro_comunicacao_op_nao_realizada);
			}
			attempt++;
		}
		return usuario;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}