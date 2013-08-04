package com.ctm.eadvogado;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ctm.eadvogado.adapters.TipoJuizoAdapter;
import com.ctm.eadvogado.adapters.TribunalAdapter;
import com.ctm.eadvogado.db.EAdvogadoDbHelper;
import com.ctm.eadvogado.dto.TipoJuizo;
import com.ctm.eadvogado.endpoints.processoEndpoint.ProcessoEndpoint;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Processo;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.TribunalEndpoint;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal;
import com.ctm.eadvogado.util.EndpointUtils;
import com.ctm.eadvogado.util.Mask;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class ConsultarProcessoActivity extends SlidingActivity {

	private EAdvogadoDbHelper dbHelper;
	private ProcessoEndpoint processoEndpoint;
	private TribunalEndpoint tribunalEndpoint;

	private View mConsultarFormView;
	private View mConsultarStatusView;
	private TextView mConsultarStatusMessageView;
	private Button mConsultarButtonView;
	private Spinner mSpinnerTribunais;
	private Spinner mSpinnerTipoJuizo;
	private EditText mEditTextNPU;
	
	private static boolean isTribunaisCarregados = false;

	CarregarDadosConsultaTask carregarTask = null;
	ConsultarProcessoTask consultarProcessoTask = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbHelper = new EAdvogadoDbHelper(this);
		
		processoEndpoint = EndpointUtils.initProcessoEndpoint();
		tribunalEndpoint = EndpointUtils.initTribunalEndpoint();
		
		setTitle(R.string.activity_consultar_processo_title);
		setContentView(R.layout.activity_consultar_processo);
		initAdmobBanner(R.id.adView);

		mSpinnerTribunais = (Spinner) findViewById(R.id.spinnerTribunal);
		mSpinnerTipoJuizo = (Spinner) findViewById(R.id.spinnerTipoJuizo);
		mSpinnerTipoJuizo.setAdapter(new TipoJuizoAdapter(this,
				R.layout.tipojuizo_list_item, Arrays.asList(TipoJuizo
						.values())));
		mConsultarFormView = findViewById(R.id.consultar_processo_form);
		mConsultarStatusView = findViewById(R.id.consultar_processo_status);
		mConsultarStatusMessageView = (TextView) findViewById(R.id.consultar_processo_status_message);
		mEditTextNPU = (EditText) findViewById(R.id.editTextNPU);
		//0130055-55.2013.5.13.0015
		mEditTextNPU.addTextChangedListener(Mask.insert("#######-##.####.#.##.####", mEditTextNPU));
		//mEditTextNPU.getText().append("01300555520135130015");
		mConsultarButtonView = (Button) findViewById(R.id.buttonConsultarProcesso);
		mConsultarButtonView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				consultarProcessos();
			}
		});
		cerregarDados();
	}

	/**
	 * Consulta os Processos
	 */
	public void consultarProcessos() {
		if (consultarProcessoTask != null) {
			alert("Por favor, aguarde! Sua consulta está sendo processada.");
			return;
		}
		if (mSpinnerTribunais.getAdapter().isEmpty()) {
			alert("Não foi possível carregar a lista de tribunais! Por favor, tente novamente.");
			return;
		}
		// Show a progress spinner, and kick off a background task to
		// perform the user login attempt.
		mConsultarStatusMessageView.setText(R.string.msg_consultando_processos);
		showProgress(true, mConsultarStatusView, mConsultarFormView);
		consultarProcessoTask = new ConsultarProcessoTask();
		
		Tribunal tribunal = (Tribunal) mSpinnerTribunais.getAdapter().getItem(
				mSpinnerTribunais.getSelectedItemPosition());
		TipoJuizo tipoJuizo = (TipoJuizo) mSpinnerTipoJuizo.getSelectedItem();
		String npu = mEditTextNPU.getText().toString();
		
		consultarProcessoTask.execute(tribunal.getKey().getId().toString(), tipoJuizo.name(), npu);
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void cerregarDados() {
		if (carregarTask != null) {
			return;
		}
		// Show a progress spinner, and kick off a background task to
		// perform the user login attempt.
		mConsultarStatusMessageView.setText(R.string.msg_carregando_dados);
		showProgress(true, mConsultarStatusView, mConsultarFormView);
		carregarTask = new CarregarDadosConsultaTask();
		carregarTask.execute((Void) null);
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class CarregarDadosConsultaTask extends
			AsyncTask<Void, Void, List<Tribunal>> {

		boolean erroComunicacao = false;

		@Override
		protected List<Tribunal> doInBackground(Void... params) {
			List<Tribunal> tribunais = new ArrayList<Tribunal>();
			if (!isTribunaisCarregados) {
				int maxTries = 3;
				int attempt = 0;
				while (attempt < maxTries) {
					try {
						tribunais = tribunalEndpoint.listAll()
								.set("sortField", "sigla")
								.set("sortOrder", "ASC")
								.execute().getItems();
						dbHelper.inserirTribunais(tribunais);
						isTribunaisCarregados = true;
					} catch (IOException e) {
						Log.e(TAG, "Falha ao carregar tribunais pelo servico", e);
					}
					if (!tribunais.isEmpty()) {
						break;
					}
					attempt++;
				}
			} else {
				try {
					tribunais = dbHelper.selectTribunais();
				} catch(Exception e) {
					Log.e(TAG, "Falha ao carregar tribunais do BD", e);
				}
			}
			return tribunais;
		}

		@Override
		protected void onPostExecute(final List<Tribunal> tribunais) {
			carregarTask = null;
			showProgress(false, mConsultarStatusView, mConsultarFormView);

			if (tribunais != null && !tribunais.isEmpty()) {
				TribunalAdapter tribunalAdapter = new TribunalAdapter(
						ConsultarProcessoActivity.this,
						R.layout.tribunal_list_item, tribunais);
				mSpinnerTribunais.setAdapter(tribunalAdapter);
			} else {
				Toast.makeText(ConsultarProcessoActivity.this,
						R.string.msg_nao_foi_possivel_carregar_dados,
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected void onCancelled() {
			carregarTask = null;
			showProgress(false, mConsultarStatusView, mConsultarFormView);
		}
	}
	
	/**
	 * @author Cleber
	 *
	 */
	public class ConsultarProcessoTask extends AsyncTask<String, Void, Processo> {
		
		String mensagem = "";

		@Override
		protected Processo doInBackground(String... params) {
			Tribunal tribunal = (Tribunal) mSpinnerTribunais.getAdapter().getItem(
					mSpinnerTribunais.getSelectedItemPosition());
			Long idTribunal = params[0] != null ? Long.parseLong(params[0]) : null;
			String tipoJuizo = params[1];
			String npu = params[2];
			Processo processo = null;
			int tries = 3;
			int attempt = 0;
			while (attempt < tries) {
				try {
					processo = processoEndpoint.consultarProcesso(
							npu.replaceAll("[.-]", ""), idTribunal, tipoJuizo,
							false).execute();
					processo.put("tribunal.nome", tribunal.getNome());
					processo.put("tribunal.sigla", tribunal.getSigla());
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
			
			return processo;
		}

		@Override
		protected void onPostExecute(Processo processo) {
			consultarProcessoTask = null;
			showProgress(false, mConsultarStatusView, mConsultarFormView);

			if (processo != null) {
				ProcessoTabsPagerFragment.processoResult = processo;
				Intent intent = new Intent();
				intent.setClass(ConsultarProcessoActivity.this,
						ProcessoTabsPagerFragment.class);
				startActivity(intent);
			} else {
				if (mensagem.length() == 0) {
					Toast.makeText(ConsultarProcessoActivity.this,
							R.string.msg_erro_inesperado,
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(ConsultarProcessoActivity.this,
							mensagem, Toast.LENGTH_LONG).show();
				}
				
			}
		}

		@Override
		protected void onCancelled() {
			consultarProcessoTask = null;
			showProgress(false, mConsultarStatusView, mConsultarFormView);
		}
	}
	

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (intent.getBooleanExtra("gcmIntentServiceMessage", false)) {
			
			
		}
	}
}
