package com.ctm.eadvogado.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.ctm.eadvogado.db.EAdvogadoContract;
import com.ctm.eadvogado.db.EAdvogadoDbHelper;
import com.ctm.eadvogado.endpoints.processoEndpoint.ProcessoEndpoint;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Key;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Processo;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.ProcessoUsuario;
import com.ctm.eadvogado.util.EndpointUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

/**
 * the user.
 */
public class CarregarMeusProcessosTask extends
		AbstractTask<String, Void, List<Processo>> {

	private ProcessoEndpoint processoEndpoint;
	private EAdvogadoDbHelper dbHelper;

	/**
	 * @param context
	 */
	public CarregarMeusProcessosTask(Context context) {
		super(context);
		processoEndpoint = EndpointUtils.initProcessoEndpoint();
		dbHelper = new EAdvogadoDbHelper(context);
	}

	/* (non-Javadoc)
	 * @see com.ctm.eadvogado.tasks.AbstractTask#executeTask(Params[])
	 */
	@Override
	protected List<Processo> executeTask(String... params)
			throws GoogleJsonResponseException, IOException {
		String email = params[0];
		String senha = params[1];
		Boolean efetuarSync = Boolean.parseBoolean(params[2]);
		
		List<Processo> processosResult = null;
		List<Processo> processosLocal = dbHelper.selectProcessos(false);
		List<ProcessoUsuario> processosDoUsuario = null;
		if ((processosLocal == null || processosLocal.isEmpty()) || efetuarSync) {
			processosResult = new ArrayList<Processo>();
			processosDoUsuario = processoEndpoint.consultarProcessosDoUsuario(email, senha).execute().getItems();
			for (ProcessoUsuario pUsu : processosDoUsuario) {
				Processo processo = dbHelper.selectProcesso(pUsu.getNpu(),
						pUsu.getIdTribunal(), pUsu.getTipoJuizo(), false);
				if (processo == null) { //insert
					processo = new Processo();
					processo.setKey(new Key());
					processo.getKey().setId(pUsu.getIdProcesso());
					processo.setNpu(pUsu.getNpu());
					processo.setTipoJuizo(pUsu.getTipoJuizo());
					processo.setTribunal(new Key());
					processo.getTribunal().setId(pUsu.getIdTribunal());
					processo.set(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_ATIVO, pUsu.getPoloAtivo());
					processo.set(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_PASSIVO, pUsu.getPoloPassivo());
					dbHelper.insertOrUpdateProcesso(processo);
				}
				if (processo != null) {
					processosResult.add(processo);
				}
			}
		}
		
		if (processosLocal != null && processosResult != null) {
			for (Processo pLocal : processosLocal) {
				boolean found = false;
				for (Processo pResult : processosResult) {
					found = pResult.getKey().getId().equals(pLocal.getKey().getId());
					if (found)
						break;
				}
				if (!found) {
					dbHelper.deleteProcesso(pLocal);
				}
			}
		} else {
			processosResult = processosLocal;
		}
		
		return processosResult;
	}

}