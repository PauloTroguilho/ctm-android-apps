package com.ctm.eadvogado;

import java.io.IOException;

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
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ctm.eadvogado.endpoints.usuarioEndpoint.UsuarioEndpoint;
import com.ctm.eadvogado.endpoints.usuarioEndpoint.model.Usuario;
import com.ctm.eadvogado.util.EndpointUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class RegistroActivity extends Activity {
	
	private SharedPreferences preferences;
	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private RegistrarUsuarioTask registrarUsuarioTask = null;
	
	private UsuarioEndpoint usuarioEndpoint;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;
	private String mRePassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private EditText mRePasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		usuarioEndpoint = EndpointUtils.initUsuarioEndpoint();
		
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		setContentView(R.layout.activity_registro);
		setupActionBar();

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
							attemptRegistrarUsuario();
							return true;
						}
						return false;
					}
				});
		mRePasswordView = (EditText) findViewById(R.id.repassword);
		mRePasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptRegistrarUsuario();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.registrar_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptRegistrarUsuario();
					}
				});
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// TODO: If Settings has multiple levels, Up should navigate up
			// that hierarchy.
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.registro, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptRegistrarUsuario() {
		if (registrarUsuarioTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mRePassword = mRePasswordView.getText().toString();

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
		
		// Check for a valid repassword.
		if (TextUtils.isEmpty(mRePassword)) {
			mRePasswordView.setError(getString(R.string.error_field_required));
			focusView = mRePasswordView;
			cancel = true;
		} else if (mRePassword.length() < 3) {
			mRePasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mRePasswordView;
			cancel = true;
		}
		
		if (!cancel && !mPassword.equals(mRePassword)) {
			mRePasswordView.setError(getString(R.string.error_invalid_passwords));
			focusView = mRePasswordView;
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
			mLoginStatusMessageView.setText(R.string.registro_progress);
			showProgress(true);
			registrarUsuarioTask = new RegistrarUsuarioTask();
			registrarUsuarioTask.execute((Void) null);
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
	public class RegistrarUsuarioTask extends AsyncTask<Void, Void, Boolean> {
		
		String mensagem = "";
		Usuario usuarioResult = null;
		
		@Override
		protected Boolean doInBackground(Void... params) {
			Usuario usuario = new Usuario();
			usuario.setEmail(mEmail);
			usuario.setSenha(mPassword);
			boolean isSuccess = false;
			int tries = 3;
			int attempt = 0;
			while (attempt < tries && !isSuccess) {
				try {
					usuarioResult = usuarioEndpoint.insert(usuario).execute();
					isSuccess = true;
				} catch(GoogleJsonResponseException e) {
					Log.e("e-Advogado", "Erro ao executar a operação!", e);
					mensagem = (e.getDetails() != null && e.getDetails() .getMessage() != null) ? 
							e.getDetails().getMessage() : getString(R.string.msg_erro_operacao_nao_realizada);
					break;
				} catch (IOException e) {
					Log.e("e-Advogado", "Erro de comunicação ao executar a operação!", e);
					mensagem = getString(R.string.msg_erro_comunicacao_op_nao_realizada);
				}
				attempt++;
			}
			return isSuccess;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			registrarUsuarioTask = null;
			showProgress(false);

			if (success) {
				Editor editor = preferences.edit();
				editor.putString(PreferencesActivity.PREFS_KEY_EMAIL, mEmail);
				editor.putString(PreferencesActivity.PREFS_KEY_SENHA, mPassword);
				editor.putString(PreferencesActivity.PREFS_KEY_TIPO_CONTA, 
						usuarioResult.getTipoConta());
				editor.commit();
				startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
				finish();
			} else {
				mPasswordView
						.setError(mensagem);
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			registrarUsuarioTask = null;
			showProgress(false);
		}
	}
}
