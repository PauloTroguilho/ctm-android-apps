package com.ctm.eadvogado.tasks;

import java.io.IOException;

import android.content.Context;

import com.ctm.eadvogado.db.EAdvogadoDbHelper;
import com.ctm.eadvogado.endpoints.processoEndpoint.ProcessoEndpoint;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Processo;
import com.ctm.eadvogado.util.EndpointUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

/**
 * the user.
 */
public class CarregarProcessoTask extends
		AbstractTask<String, Void, Processo> {

	private ProcessoEndpoint processoEndpoint;
	private EAdvogadoDbHelper dbHelper;

	/**
	 * @param context
	 */
	public CarregarProcessoTask(Context context) {
		super(context);
		processoEndpoint = EndpointUtils.initProcessoEndpoint();
		dbHelper = new EAdvogadoDbHelper(context);
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
		Boolean atualizar = Boolean.parseBoolean(params[3]);
		Processo processoLocal = dbHelper.selectProcesso(npu, idTribunal, tipoJuizo, true);
		Processo processoRemoto = null;
		if (processoLocal == null || processoLocal.getProcessoJudicial() == null || atualizar) {
			processoRemoto = processoEndpoint.consultarProcesso(
								npu.replaceAll("[.-]", ""), idTribunal, tipoJuizo,
								atualizar).execute();
		}
 		if (processoRemoto != null) {
			if (processoLocal == null) {
				processoLocal = processoRemoto;
			}
			dbHelper.insertOrUpdateProcesso(processoRemoto);
		}
		return processoLocal;
	}

}