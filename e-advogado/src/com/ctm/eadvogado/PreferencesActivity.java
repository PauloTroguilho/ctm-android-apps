package com.ctm.eadvogado;

import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.ctm.eadvogado.util.Consts;

public class PreferencesActivity extends SherlockPreferenceActivity implements
		OnSharedPreferenceChangeListener {

	public static final String PREFS_KEY_PRIMEIRA_EXEC = "primeiraExec";
	public static final String PREFS_KEY_EMAIL = "email";
	public static final String PREFS_KEY_SENHA = "senha";
	public static final String PREFS_KEY_TIPO_CONTA = "tipoConta";
	public static final String PREFS_KEY_RATE_THIS_APP = "rateThisApp";
	public static final String PREFS_KEY_ULTIMA_RATE_THIS_APP = "ultimaRateThisApp";
	public static final String PREFS_KEY_ULTIMA_ATUALIZACAO = "ultimaAtualizacao";

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(Consts.THEME);
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			addPreferencesFromResource(R.xml.preferences_conta);
		}
	}

	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.preference_headers, target);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(PREFS_KEY_EMAIL)/* || key.equals(PREFS_KEY_SENHA) */) {
			Log.i("E-Android", String.format("Prefs > Key: %s / Value: %s",
					key, sharedPreferences.getString(key, "")));
			sharedPreferences.edit().commit();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		PreferenceManager.getDefaultSharedPreferences(this)
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		PreferenceManager.getDefaultSharedPreferences(this)
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	public static SharedPreferences preferences;

	private static SharedPreferences getPreferences(Context context) {
		if (preferences == null)
			preferences = PreferenceManager
					.getDefaultSharedPreferences(context);
		return preferences;
	}

	public static String getEmail(Context context) {
		return getPreferences(context).getString(
				PreferencesActivity.PREFS_KEY_EMAIL, "");
	}

	public static String getSenha(Context context) {
		return getPreferences(context).getString(
				PreferencesActivity.PREFS_KEY_SENHA, "");
	}

	public static boolean isAppRated(Context context) {
		return getPreferences(context).getBoolean(
				PreferencesActivity.PREFS_KEY_RATE_THIS_APP, false);
	}

	public static String getTipoConta(Context context) {
		return getPreferences(context).getString(
				PreferencesActivity.PREFS_KEY_TIPO_CONTA,
				Consts.TIPO_CONTA_BASICA);
	}

	public static boolean isContaPremium(Context context) {
		return getTipoConta(context).equals(Consts.TIPO_CONTA_PREMIUM);
	}

	public static long getUltimaAtualizacao(Context context) {
		return getPreferences(context).getLong(
				PreferencesActivity.PREFS_KEY_ULTIMA_ATUALIZACAO,
				System.currentTimeMillis() - Consts.UM_DIA_EM_MILLIS);
	}

	public static long getUltimaRateThisApp(Context context) {
		return getPreferences(context).getLong(
				PreferencesActivity.PREFS_KEY_ULTIMA_RATE_THIS_APP,
				System.currentTimeMillis() - Consts.TRES_DIAS_EM_MILLIS);
	}

}