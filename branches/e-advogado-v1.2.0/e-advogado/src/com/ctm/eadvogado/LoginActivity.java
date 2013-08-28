package com.ctm.eadvogado;

import java.io.IOException;

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

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;
import com.ctm.eadvogado.endpoints.usuarioEndpoint.UsuarioEndpoint;
import com.ctm.eadvogado.endpoints.usuarioEndpoint.model.Usuario;
import com.ctm.eadvogado.util.Consts;
import com.ctm.eadvogado.util.EndpointUtils;
import com.ctm.eadvogado.util.MessageUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class LoginActivity extends SherlockActivity {
	
	private static final String TAG = "e-Advogado"; 
	
	//protected AccountManager accountManager;
	//protected Account[] accounts;
	
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
	
	private UsuarioEndpoint usuarioEndpoint;
	private SharedPreferences preferences;

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;
	private RecuperaSenhaTask recuperaSenhaTask = null;
	
	// Values for email and password at the time of the login attempt.
	private String email;
	private String password;

	// UI references.
	private EditText emailEt;
	private EditText passwordEt;
	private Button loginBt;
	private Button cancelLoginBt;
	private Button registroBt;
	private Button esqueciSenhaBt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(Consts.THEME);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_login);
		getSupportActionBar().setTitle(R.string.title_activity_login);
		
		usuarioEndpoint = EndpointUtils.initUsuarioEndpoint();
		
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
							if (recuperaSenhaTask != null) {
								recuperaSenhaTask.cancel(true);
							}
						}
					});
			registroBt = (Button) findViewById(R.id.btnRegistrese);
			registroBt.setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							if (mAuthTask != null) {
								mAuthTask.cancel(true);
							}
							startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
							finish();
						}
					});
			esqueciSenhaBt = (Button) findViewById(R.id.btnEsqueciSenha);
			esqueciSenhaBt.setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							if (recuperaSenhaTask != null) {
								recuperaSenhaTask.cancel(true);
							}
							doRecuperarSenha();
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
		esqueciSenhaBt.setEnabled(enabled);
		registroBt.setEnabled(enabled);
	}
	
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Usuario> {
		
		String mensagem = null;
		
		@Override
		protected Usuario doInBackground(Void... params) {
			Usuario usuario = null;
			int tries = 3;
			int attempt = 0;
			while (attempt < tries) {
				try {
					usuario = usuarioEndpoint.autenticar(email, password).execute();
					if (usuario != null)
						break;
				} catch(GoogleJsonResponseException e) {
					Log.e(TAG, "Erro ao executar a operação!", e);
					mensagem = (e.getDetails() != null && e.getDetails() .getMessage() != null) ? 
							e.getDetails().getMessage() : getString(R.string.msg_erro_operacao_nao_realizada);
				} catch (IOException e) {
					Log.e(TAG, "Erro de comunicação ao executar a operação!", e);
					mensagem = getString(R.string.msg_erro_comunicacao_op_nao_realizada);
				}
				attempt++;
			}
			return usuario;
		}

		@Override
		protected void onPostExecute(Usuario usuario) {
			mAuthTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
			if (usuario != null) {
				Editor editor = preferences.edit();
				editor.putString(PreferencesActivity.PREFS_KEY_EMAIL, email);
				editor.putString(PreferencesActivity.PREFS_KEY_SENHA, password);
				editor.putString(PreferencesActivity.PREFS_KEY_TIPO_CONTA, 
						usuario.getTipoConta());
				editor.commit();
				
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			} else {
				if (mensagem.length() == 0) {
					MessageUtils.alert(getString(R.string.msg_erro_inesperado), LoginActivity.this);
				} else {
					MessageUtils.alert(mensagem, LoginActivity.this);
				}
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
		}
	}
	
	public class RecuperaSenhaTask extends AsyncTask<Void, Void, Usuario> {
		
		String mensagem = null;
		
		@Override
		protected Usuario doInBackground(Void... params) {
			Usuario usuario = null;
			int tries = 3;
			int attempt = 0;
			while (attempt < tries) {
				try {
					usuario = usuarioEndpoint.resetarSenha(email).execute();
					if (usuario != null)
						break;
				} catch(GoogleJsonResponseException e) {
					Log.e(TAG, "Erro ao executar a operação!", e);
					mensagem = (e.getDetails() != null && e.getDetails() .getMessage() != null) ? 
							e.getDetails().getMessage() : getString(R.string.msg_erro_operacao_nao_realizada);
				} catch (IOException e) {
					Log.e(TAG, "Erro de comunicação ao executar a operação!", e);
					mensagem = getString(R.string.msg_erro_comunicacao_op_nao_realizada);
				}
				attempt++;
			}
			return usuario;
		}

		@Override
		protected void onPostExecute(Usuario usuario) {
			recuperaSenhaTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
			if (usuario != null) {
				Editor editor = preferences.edit();
				editor.remove(PreferencesActivity.PREFS_KEY_EMAIL);
				editor.remove(PreferencesActivity.PREFS_KEY_SENHA);
				editor.remove(PreferencesActivity.PREFS_KEY_TIPO_CONTA);
				editor.commit();
				emailEt.requestFocus();
				MessageUtils.alert(getString(R.string.msg_email_nova_senha), LoginActivity.this);
			} else {
				if (mensagem.length() == 0) {
					MessageUtils.alert(getString(R.string.msg_erro_inesperado), LoginActivity.this);
				} else {
					MessageUtils.alert(mensagem, LoginActivity.this);
				}
			}
		}

		@Override
		protected void onCancelled() {
			recuperaSenhaTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
		}
	}
	
	public void doRecuperarSenha() {
		if (recuperaSenhaTask != null) {
			return;
		}
		boolean cancel = false;
		email = emailEt.getText().toString();
		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			emailEt.setError(getString(R.string.error_field_required));
			cancel = true;
		} else if (!email.contains("@")) {
			emailEt.setError(getString(R.string.error_invalid_email));
			cancel = true;
		}

		if (cancel) {
			emailEt.requestFocus();
		} else {
			setSupportProgressBarIndeterminateVisibility(true);
			setControlsEnabled(false);
			recuperaSenhaTask = new RecuperaSenhaTask();
			recuperaSenhaTask.execute((Void) null);
		}
	}

}
