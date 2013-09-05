package com.ctm.eadvogado.tasks;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.ctm.eadvogado.db.EAdvogadoDbHelper;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal;

/**
 * Task rate this app.
 */
public class CarregarTribunaisTask extends
		AsyncTask<Void, Void, List<Tribunal>> {

	Context context;
	private EAdvogadoDbHelper dbHelper;
	
	/**
	 * @param context
	 */
	public CarregarTribunaisTask(Context context) {
		this.context = context;
		this.dbHelper = new EAdvogadoDbHelper(context);
	}

	@Override
	protected List<Tribunal> doInBackground(Void... params) {
		List<Tribunal> tribunais = dbHelper.selectTribunais();
		return tribunais;
	}

}