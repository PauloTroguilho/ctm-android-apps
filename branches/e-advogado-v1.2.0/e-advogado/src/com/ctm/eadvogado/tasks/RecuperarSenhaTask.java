package com.ctm.eadvogado.tasks;

import java.io.IOException;

import android.content.Context;

import com.ctm.eadvogado.endpoints.usuarioEndpoint.UsuarioEndpoint;
import com.ctm.eadvogado.endpoints.usuarioEndpoint.model.Usuario;
import com.ctm.eadvogado.util.EndpointUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

/**
 * the user.
 */
public class RecuperarSenhaTask extends
		AbstractTask<String, Void, Usuario> {

	private UsuarioEndpoint usuarioEndpoint;

	/**
	 * @param context
	 */
	public RecuperarSenhaTask(Context context) {
		super(context);
		usuarioEndpoint = EndpointUtils.initUsuarioEndpoint();
	}

	/* (non-Javadoc)
	 * @see com.ctm.eadvogado.tasks.AbstractTask#executeTask(Params[])
	 */
	@Override
	protected Usuario executeTask(String... params)
			throws GoogleJsonResponseException, IOException {
		return usuarioEndpoint.resetarSenha(params[0]).execute();
	}

}