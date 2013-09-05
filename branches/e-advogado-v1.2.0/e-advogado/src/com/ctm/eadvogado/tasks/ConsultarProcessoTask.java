package com.ctm.eadvogado.tasks;

import java.io.IOException;

import android.content.Context;

import com.ctm.eadvogado.endpoints.processoEndpoint.ProcessoEndpoint;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Processo;
import com.ctm.eadvogado.util.EndpointUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

/**
 * the user.
 */
public class ConsultarProcessoTask extends
		AbstractTask<String, Void, Processo> {

	private ProcessoEndpoint processoEndpoint;

	/**
	 * @param context
	 */
	public ConsultarProcessoTask(Context context) {
		super(context);
		processoEndpoint = EndpointUtils.initProcessoEndpoint();
	}

	/* (non-Javadoc)
	 * @see com.ctm.eadvogado.tasks.AbstractTask#executeTask(Params[])
	 */
	@Override
	protected Processo executeTask(String... params)
			throws GoogleJsonResponseException, IOException {
		String npu = params[0];
		Long idTribunal = Long.parseLong(params[1]);
		String tipoJuizo = params[2];
		Boolean ignorarCache = Boolean.parseBoolean(params[3]);
		return processoEndpoint.consultarProcesso(npu.replaceAll("[.-]", ""), idTribunal, tipoJuizo, ignorarCache).execute();
	}

}