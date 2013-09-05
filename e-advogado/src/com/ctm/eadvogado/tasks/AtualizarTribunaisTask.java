package com.ctm.eadvogado.tasks;

import java.io.IOException;
import java.util.List;

import android.content.Context;

import com.ctm.eadvogado.endpoints.tribunalEndpoint.TribunalEndpoint;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal;
import com.ctm.eadvogado.util.EndpointUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

/**
 * the user.
 */
public class AtualizarTribunaisTask extends
		AbstractTask<Void, Void, List<Tribunal>> {

	private TribunalEndpoint tribunalEndpoint;

	/**
	 * @param context
	 */
	public AtualizarTribunaisTask(Context context) {
		super(context);
		tribunalEndpoint = EndpointUtils.initTribunalEndpoint();
	}

	/* (non-Javadoc)
	 * @see com.ctm.eadvogado.tasks.AbstractTask#executeTask(Params[])
	 */
	@Override
	protected List<Tribunal> executeTask(Void... params)
			throws GoogleJsonResponseException, IOException {
		return tribunalEndpoint.listAll().set("sortField", "sigla").set("sortOrder", "ASC")
				.execute().getItems();
	}

}