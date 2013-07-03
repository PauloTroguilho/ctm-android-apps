package com.ctm.eadvogado;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TermosDeUsoActivity extends Activity {
	
	private Button btnAceito;
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		boolean primeiraExec = preferences.getBoolean(PreferencesActivity.PREFS_KEY_PRIMEIRA_EXEC, true);
		if (primeiraExec) {
			setContentView(R.layout.activity_termos_de_uso);
			btnAceito = (Button) findViewById(R.id.buttonTermos);
			btnAceito.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					preferences.edit().putBoolean(PreferencesActivity.PREFS_KEY_PRIMEIRA_EXEC, false).commit();
					goToLoginView();
				}
			});
		} else {
			goToLoginView();
		}
	}
	
	
	private void goToLoginView() {
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

}
