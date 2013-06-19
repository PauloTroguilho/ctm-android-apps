package com.ctm.eadvogado;

import java.lang.reflect.Type;
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
import com.ctm.eadvogado.dto.ProcessoDTO;
import com.ctm.eadvogado.processoendpoint.Processoendpoint;
import com.ctm.eadvogado.processoendpoint.model.Processo;
import com.ctm.eadvogado.processoendpoint.model.TipoProcessoJudicial;
import com.ctm.eadvogado.tribunalendpoint.Tribunalendpoint;
import com.ctm.eadvogado.tribunalendpoint.model.Tribunal;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ConsultarProcessoActivity extends SlidingActivity {

	private EAdvogadoDbHelper dbHelper;
	private Processoendpoint processoEndpoint;
	private Tribunalendpoint tribunalEndpoint;

	private View mConsultarFormView;
	private View mConsultarStatusView;
	private TextView mConsultarStatusMessageView;
	private Button mConsultarButtonView;
	private Spinner mSpinnerTribunais;
	private Spinner mSpinnerTipoJuizo;
	private EditText mEditTextNPU;

	CarregarDadosConsultaTask carregarTask = null;
	ConsultarProcessoTask consultarProcessoTask = null;
	
	public static ProcessoDTO processoResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbHelper = new EAdvogadoDbHelper(this);
		Processoendpoint.Builder procEndpointBuilder = new Processoendpoint.Builder(
		        AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
		        new HttpRequestInitializer() {
		          public void initialize(HttpRequest httpRequest) {
		          }
		        });
		processoEndpoint = CloudEndpointUtils.updateBuilder(procEndpointBuilder).build();
		Tribunalendpoint.Builder tribEndpointBuilder = new Tribunalendpoint.Builder(
		        AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
		        new HttpRequestInitializer() {
		          public void initialize(HttpRequest httpRequest) {
		          }
		        });
		tribunalEndpoint = CloudEndpointUtils.updateBuilder(tribEndpointBuilder).build();
		
		setTitle(R.string.activity_consultar_processo_title);
		setContentView(R.layout.activity_consultar_processo);

		mSpinnerTribunais = (Spinner) findViewById(R.id.spinnerTribunal);
		mSpinnerTipoJuizo = (Spinner) findViewById(R.id.spinnerTipoJuizo);
		mSpinnerTipoJuizo.setAdapter(new TipoJuizoAdapter(this,
				android.R.layout.simple_spinner_item, Arrays.asList(TipoJuizo
						.values())));
		mConsultarFormView = findViewById(R.id.consultar_processo_form);
		mConsultarStatusView = findViewById(R.id.consultar_processo_status);
		mConsultarStatusMessageView = (TextView) findViewById(R.id.consultar_processo_status_message);
		mEditTextNPU = (EditText) findViewById(R.id.editTextNPU);
		mEditTextNPU.getText().append("01300555520135130015");
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
			return;
		}
		// Show a progress spinner, and kick off a background task to
		// perform the user login attempt.
		mConsultarStatusMessageView.setText(R.string.msg_consultando_processos);
		showProgress(true, mConsultarStatusView, mConsultarFormView);
		consultarProcessoTask = new ConsultarProcessoTask();
		consultarProcessoTask.execute((Void) null);
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
			try {
				tribunais = dbHelper.selectTribunais();
			} catch (Exception e) {
				Log.e("E-Advogado", "Falha ao carregar tribunais do banco", e);
			}
			if (tribunais.isEmpty()) {
				try {
					tribunais = tribunalEndpoint.listTribunal().execute().getItems();
					dbHelper.inserirTribunais(tribunais);
				} catch (Exception e) {
					Log.e("E-Advogado",
							"Falha ao carregar tribunais pelo servico", e);
				}
			}
			return tribunais;
		}

		@Override
		protected void onPostExecute(final List<Tribunal> tribunais) {
			carregarTask = null;
			showProgress(false, mConsultarStatusView, mConsultarFormView);

			if (!tribunais.isEmpty()) {
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
	
	public class ConsultarProcessoTask extends AsyncTask<Void, Void, ProcessoDTO> {

		boolean erroComunicacao = false;

		@Override
		protected ProcessoDTO doInBackground(Void... params) {
			processoResult = null;
			ProcessoDTO processoDTO = new ProcessoDTO();
			Processo processo = null;
			
			Tribunal tribunal = (Tribunal) mSpinnerTribunais.getAdapter()
					.getItem(mSpinnerTribunais.getSelectedItemPosition());
			processoDTO.setTribunal(tribunal);
			TipoJuizo tipoJuizo = (TipoJuizo) mSpinnerTipoJuizo.getSelectedItem();
			try {
				processo = dbHelper.selectProcesso(mEditTextNPU.getText()
						.toString(), tribunal.getId().getId(), tipoJuizo.name());
				if (processo != null) {
					String processoXml = dbHelper.selectProcessoXml(
							processo.getId().getId());
					Type t = new TypeToken<TipoProcessoJudicial>() {}.getType();
					Gson gson = new Gson();
					processo.setProcessoJudicial((TipoProcessoJudicial)gson.fromJson(processoXml, t));
				}
			} catch (Exception e) {
				processo = null;
				Log.e("E-Advogado", "Falha ao consultar processo no banco", e);
			}
			if (processo == null) {
				try {
					processo = processoEndpoint.consultarProcesso(
							mEditTextNPU.getText().toString(), 
							tipoJuizo.name(), tribunal.getId().getId()).execute();
				} catch (Exception e) {
					Log.e("E-Advogado",
							"Falha ao carregar processo pelo servico", e);
				}
				if (processo != null) {
					try {
						dbHelper.inserirProcesso(processo);
					} catch (Exception e) {
						Log.e("E-Advogado", "Falha ao inserir o processo no banco", e);
					}
				}
			}
			processoDTO.setProcesso(processo);
			return processoDTO;
		}

		@Override
		protected void onPostExecute(ProcessoDTO processoDTO) {
			consultarProcessoTask = null;
			showProgress(false, mConsultarStatusView, mConsultarFormView);

			if (processoDTO.getProcesso() != null) {
				Intent intent = new Intent();
				intent.setClass(ConsultarProcessoActivity.this,
						ProcessoTabsPagerFragment.class);
				processoResult = processoDTO;
				startActivity(intent);
			} else {
				Toast.makeText(ConsultarProcessoActivity.this,
						R.string.msg_nao_foi_possivel_carregar_dados,
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected void onCancelled() {
			consultarProcessoTask = null;
			showProgress(false, mConsultarStatusView, mConsultarFormView);
		}
	}
	

}
