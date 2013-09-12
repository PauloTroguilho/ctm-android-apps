/**
 * 
 */
package com.ctm.eadvogado.tasks;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ctm.eadvogado.PreferencesActivity;
import com.ctm.eadvogado.R;
import com.ctm.eadvogado.db.EAdvogadoDbHelper;
import com.ctm.eadvogado.util.Consts;
import com.ctm.eadvogado.util.MessageUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

/**
 * @author Cleber
 * 
 */
public abstract class AbstractTask<Params, Progress, Result> extends
		AsyncTask<Params, Progress, Result> {
	
	public static final int MAX_ATTEMPTS = 3;
	
	public static final String TAG = "e-Advogado";

	protected String errorMessage;
	protected Throwable cause;
	private Context context;
	private SharedPreferences preferences;
	private EAdvogadoDbHelper dbHelper;

	/**
	 * @param context
	 */
	public AbstractTask(Context context) {
		this.context = context;
		this.dbHelper = new EAdvogadoDbHelper(context);
		this.preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
	}
	
	@Override
	protected Result doInBackground(Params... params) {
		Result result = null;
		int attempt = 0;
		while (attempt < MAX_ATTEMPTS && result == null) {
			try {
				result = executeTask(params);
			} catch (GoogleJsonResponseException e) {
				Log.e(TAG, "Erro ao executar a operação!", e);
				errorMessage = 
					(e.getDetails() != null && e.getDetails().getMessage() != null) 
						? e.getDetails().getMessage()
						: getContext().getString(R.string.msg_erro_operacao_nao_realizada);
			} catch (IOException e) {
				Log.e(TAG, "Erro de comunicação ao executar a operação!", e);
				errorMessage = getContext().getString(
						R.string.msg_erro_comunicacao_op_nao_realizada);
			}
			attempt++;
		}
		return result;
	}
	
	/**
	 * Implementação da Task.
	 * 
	 * @param params
	 * @return
	 * @throws GoogleJsonResponseException
	 * @throws IOException
	 */
	protected abstract Result executeTask(Params... params) throws GoogleJsonResponseException, IOException;
	

	protected String getErrorMessage() {
		if (errorMessage == null)
			errorMessage = "";
		return errorMessage;
	}

	protected Throwable getCause() {
		return cause;
	}

	protected void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	protected void setCause(Throwable cause) {
		this.cause = cause;
	}

	protected Context getContext() {
		return context;
	}

	protected SharedPreferences getPreferences() {
		return preferences;
	}

	protected String getEmail() {
		return preferences.getString(PreferencesActivity.PREFS_KEY_EMAIL, "");
	}

	protected String getSenha() {
		return preferences.getString(PreferencesActivity.PREFS_KEY_SENHA, "");
	}

	protected boolean isAppRated() {
		return preferences.getBoolean(
				PreferencesActivity.PREFS_KEY_RATE_THIS_APP, false);
	}

	protected boolean isContaPremium() {
		return preferences.getString(PreferencesActivity.PREFS_KEY_TIPO_CONTA,
				Consts.TIPO_CONTA_BASICA).equals(Consts.TIPO_CONTA_PREMIUM);
	}
	
	
	protected EAdvogadoDbHelper getDbHelper() {
		return dbHelper;
	}

	/**
	 * @param message
	 */
	protected void alert(String message) {
		MessageUtils.alert(message, getContext());
	}
	
	/**
	 * @param resId
	 */
	protected void alert(int resId) {
		MessageUtils.alert(getContext().getString(resId), getContext());
	}
}
