package com.ctm.eadvogado;

import java.io.IOException;
import java.util.Locale;

import org.apache.http.HttpStatus;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ctm.eadvogado.advogadoendpoint.Advogadoendpoint;
import com.ctm.eadvogado.advogadoendpoint.model.Advogado;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	
	public static enum LoginStatus {
		LOGIN_OK, LOGIN_ERROR, INVALID_PASSWORD, ACCOUNT_NOT_FOUND, NETWORK_ERROR;
	}

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
	
	private Advogadoendpoint advogadoEndpoint;
	
	private SharedPreferences preferences;

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;
	
	private boolean byPassLogin = false;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private Button mLoginButtonView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		Advogadoendpoint.Builder endpointBuilder = new Advogadoendpoint.Builder(
		        AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
		        new HttpRequestInitializer() {
		          public void initialize(HttpRequest httpRequest) {
		          }
		        });
		advogadoEndpoint = CloudEndpointUtils.updateBuilder(endpointBuilder).build();
		setContentView(R.layout.activity_login);

		String email = preferences.getString(PreferencesActivity.PREFS_KEY_EMAIL, "");
		
		if (email == null || email.trim().length() == 0) {
			// Set up the login form.
			mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
			mEmailView = (EditText) findViewById(R.id.email);
			mEmailView.setText(mEmail);

			mPasswordView = (EditText) findViewById(R.id.password);
			mPasswordView
					.setOnEditorActionListener(new TextView.OnEditorActionListener() {
						@Override
						public boolean onEditorAction(TextView textView, int id,
								KeyEvent keyEvent) {
							if (id == R.id.login || id == EditorInfo.IME_NULL) {
								attemptLogin();
								return true;
							}
							return false;
						}
					});

			mLoginFormView = findViewById(R.id.login_form);
			mLoginStatusView = findViewById(R.id.login_status);
			mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
			mLoginButtonView = (Button) findViewById(R.id.sign_in_button);
			mLoginButtonView.setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							attemptLogin();
						}
					});
		} else {
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			startActivity(intent);
			finish();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 3) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
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
					Advogado advogado = advogadoEndpoint.autenticarAdvogado(mEmail, mPassword).execute();
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
					advogado.setEmail(mEmail);
					advogado.setSenha(mPassword);
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
			showProgress(false);
			
			switch (status) {
				case LOGIN_OK:
					Editor editor = preferences.edit();
					editor.putString(PreferencesActivity.PREFS_KEY_EMAIL, mEmail);
					//editor.putString(PreferencesActivity.PREFS_KEY_SENHA, mPassword);
					editor.commit();
					
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
					break;
				case INVALID_PASSWORD:
					mPasswordView.setError(getString(R.string.error_incorrect_password));
					mPasswordView.requestFocus();
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
					mLoginButtonView.setError(getString(R.string.error_login_fail));
					mLoginButtonView.requestFocus();
					break;
				case NETWORK_ERROR:
					mLoginButtonView.setError(getString(R.string.error_communication));
					mLoginButtonView.requestFocus();
			}

		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
