package com.ctm.eadvogado;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ctm.eadvogado.fragment.PreferencesFragment;

public class PreferencesActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	
	public static final String PREFS_KEY_EMAIL = "email";
	public static final String PREFS_KEY_SENHA = "senha";
	
	@SuppressWarnings("deprecation")
	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			getFragmentManager().beginTransaction()
	            .replace(android.R.id.content, new PreferencesFragment())
	            .commit();
		} else {
			addPreferencesFromResource(R.xml.preferences);
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(PREFS_KEY_EMAIL)/* || key.equals(PREFS_KEY_SENHA)*/) {
			Log.i("E-Android", String.format("Prefs > Key: %s / Value: %s", key, sharedPreferences.getString(key, "")));
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
}