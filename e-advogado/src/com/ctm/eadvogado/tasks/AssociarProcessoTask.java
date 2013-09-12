package com.ctm.eadvogado.tasks;

import java.io.IOException;

import android.content.Context;

import com.ctm.eadvogado.endpoints.processoEndpoint.ProcessoEndpoint;
import com.ctm.eadvogado.util.EndpointUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

/**
 * the user.
 */
public class AssociarProcessoTask extends
		AbstractTask<String, Void, Boolean> {

	private ProcessoEndpoint processoEndpoint;
	
	/**
	 * @param context
	 */
	public AssociarProcessoTask(Context context) {
		super(context);
		processoEndpoint = EndpointUtils.initProcessoEndpoint();
	}

	/* (non-Javadoc)
	 * @see com.ctm.eadvogado.tasks.AbstractTask#executeTask(Params[])
	 */
	@Override
	protected Boolean executeTask(String... params)
			throws GoogleJsonResponseException, IOException {
		String email = params[0];
		Long idProcesso = Long.parseLong(params[1]);
		processoEndpoint.associarProcessoAoUsuario(email, idProcesso).execute();
		return Boolean.TRUE;
	}

}