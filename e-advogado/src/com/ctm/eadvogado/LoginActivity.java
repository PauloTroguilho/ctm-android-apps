package com.ctm.eadvogado;

import java.io.IOException;
import java.util.Locale;

import org.apache.http.HttpStatus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;
import com.ctm.eadvogado.advogadoendpoint.Advogadoendpoint;
import com.ctm.eadvogado.advogadoendpoint.model.Advogado;
import com.ctm.eadvogado.util.Consts;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

public class LoginActivity extends SherlockActivity {
	
	//protected AccountManager accountManager;
	//protected Account[] accounts;
	
	public static enum LoginStatus {
		LOGIN_OK, LOGIN_ERROR, INVALID_PASSWORD, ACCOUNT_NOT_FOUND, NETWORK_ERROR;
	}
	
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
	
	private Advogadoendpoint advogadoEndpoint;
	private SharedPreferences preferences;

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;
	
	private boolean byPassLogin = false;

	// Values for email and password at the time of the login attempt.
	private String email;
	private String password;

	// UI references.
	private EditText emailEt;
	private EditText passwordEt;
	private Button loginBt;
	private Button cancelLoginBt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(Consts.THEME);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_login);
		getSupportActionBar().setTitle(R.string.title_activity_login);
		
		initAdvogadoEndpoint();
		/*accountManager = AccountManager.get(getApplicationContext());
        accounts = accountManager.getAccountsByType("com.google");
        if (accounts != null && accounts.length > 0) {
        	ListView listViewAccounts = (ListView) findViewById(R.id.listViewAccounts);
            listViewAccounts.setAdapter(new ArrayAdapter(this, R.layout.account_list_item, accounts));
        }*/

		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String email = preferences.getString(PreferencesActivity.PREFS_KEY_EMAIL, "");
		
		if (email == null || email.trim().length() == 0) {
			// Set up the login form.
			email = getIntent().getStringExtra(EXTRA_EMAIL);
			emailEt = (EditText) findViewById(R.id.email);
			emailEt.setText(email);

			passwordEt = (EditText) findViewById(R.id.password);
			passwordEt
					.setOnEditorActionListener(new TextView.OnEditorActionListener() {
						@Override
						public boolean onEditorAction(TextView textView, int id,
								KeyEvent keyEvent) {
							if (id == R.id.login || id == EditorInfo.IME_NULL) {
								doLogin();
								return true;
							}
							return false;
						}
					});
			loginBt = (Button) findViewById(R.id.sign_in_button);
			loginBt.setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							doLogin();
						}
					});
			cancelLoginBt = (Button) findViewById(R.id.login_cancel_button);
			cancelLoginBt.setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							if (mAuthTask != null) {
								mAuthTask.cancel(true);
							}
						}
					});
		} else {
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
	/**
	 * Inicializa o endpoint de Advogado
	 */
	private void initAdvogadoEndpoint() {
		Advogadoendpoint.Builder endpointBuilder = new Advogadoendpoint.Builder(
		        AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
		        new HttpRequestInitializer() {
		          public void initialize(HttpRequest httpRequest) {
		          }
		        });
		advogadoEndpoint = CloudEndpointUtils.updateBuilder(endpointBuilder).build();
	}
	
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void doLogin() {
		if (mAuthTask != null) {
			return;
		}
		// Reset errors.
		emailEt.setError(null);
		passwordEt.setError(null);
		// Store values at the time of the login attempt.
		email = emailEt.getText().toString();
		password = passwordEt.getText().toString();

		boolean cancel = false;
		View focusView = null;
		// Check for a valid password.
		if (TextUtils.isEmpty(password)) {
			passwordEt.setError(getString(R.string.error_field_required));
			focusView = passwordEt;
			cancel = true;
		} else if (password.length() < 3) {
			passwordEt.setError(getString(R.string.error_invalid_password));
			focusView = passwordEt;
			cancel = true;
		}
		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			emailEt.setError(getString(R.string.error_field_required));
			focusView = emailEt;
			cancel = true;
		} else if (!email.contains("@")) {
			emailEt.setError(getString(R.string.error_invalid_email));
			focusView = emailEt;
			cancel = true;
		}

		if (cancel) {
			focusView.requestFocus();
		} else {
			setSupportProgressBarIndeterminateVisibility(true);
			setControlsEnabled(false);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}
	
	/**
	 * @param enabled
	 */
	private void setControlsEnabled(boolean enabled) {
		emailEt.setEnabled(enabled);
		passwordEt.setEnabled(enabled);
		loginBt.setVisibility(enabled ? View.VISIBLE : View.GONE);
		cancelLoginBt.setVisibility(enabled ? View.GONE : View.VISIBLE);
	}
	
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, LoginStatus> {
		
		String errorMessage = null;
		
		@Override
		protected LoginStatus doInBackground(Void... params) {
			LoginStatus status = null;
			errorMessage = "";
			if (!byPassLogin) {
				try {
					Advogado advogado = advogadoEndpoint.autenticarAdvogado(email, password).execute();
					status = advogado != null ? LoginStatus.LOGIN_OK : LoginStatus.LOGIN_ERROR;
				} catch(GoogleJsonResponseException e) {
					if ((e.getDetails() != null && e.getDetails().getCode() == HttpStatus.SC_NOT_FOUND)
							|| (e.getContent() != null && e.getContent().toLowerCase(Locale.ENGLISH).contains("not found"))) {
						status = LoginStatus.ACCOUNT_NOT_FOUND;
					} else if ((e.getDetails() != null && e.getDetails().getCode() == HttpStatus.SC_UNAUTHORIZED)
							|| (e.getContent() != null && e.getContent().toLowerCase(Locale.ENGLISH).contains("unauthorized"))) {
						status = LoginStatus.INVALID_PASSWORD;
					}
				} catch (Exception e) {
					Log.e("E-Advogado", "Falha ao realizar login.", e);
					status = LoginStatus.NETWORK_ERROR;
				}
			} else {
				status = LoginStatus.LOGIN_OK;
			}
			if (status == LoginStatus.ACCOUNT_NOT_FOUND) {
				try {
					Advogado advogado = new Advogado();
					advogado.setEmail(email);
					advogado.setSenha(password);
					advogadoEndpoint.insertAdvogado(advogado).execute();
					status = LoginStatus.LOGIN_OK;
				} catch (IOException e) {
					Log.e("E-Advogado", "Falha comunicação ao realizar cadastro.", e);
					errorMessage = getString(R.string.error_cadastro_fail);
				} catch (Exception e) {
					Log.e("E-Advogado", "Erro ao realizar cadastro.", e);
					errorMessage = getString(R.string.error_cadastro_fail);
				}
			}
			return status;
		}

		@Override
		protected void onPostExecute(LoginStatus status) {
			mAuthTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
			switch (status) {
				case LOGIN_OK:
					Editor editor = preferences.edit();
					editor.putString(PreferencesActivity.PREFS_KEY_EMAIL, email);
					//editor.putString(PreferencesActivity.PREFS_KEY_SENHA, password);
					editor.commit();
					
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
					break;
				case INVALID_PASSWORD:
					passwordEt.setError(getString(R.string.error_incorrect_password));
					passwordEt.requestFocus();
					break;
				case ACCOUNT_NOT_FOUND:
					if (errorMessage != null && errorMessage.trim().length() > 0) {
						Toast.makeText(LoginActivity.this,
								errorMessage,
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(LoginActivity.this,
								R.string.error_cadastro_fail,
								Toast.LENGTH_LONG).show();
					}
					break;
				case LOGIN_ERROR:
					loginBt.setError(getString(R.string.error_login_fail));
					loginBt.requestFocus();
					break;
				case NETWORK_ERROR:
					loginBt.setError(getString(R.string.error_communication));
					loginBt.requestFocus();
			}

		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
		}
	}

}
