package com.ctm.eadvogado;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ctm.eadvogado.adapters.ProcessoAdapter;
import com.ctm.eadvogado.db.EAdvogadoDbHelper;
import com.ctm.eadvogado.dto.ProcessoDTO;
import com.ctm.eadvogado.processoendpoint.Processoendpoint;
import com.ctm.eadvogado.processoendpoint.model.Processo;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

public class MeusProcessosActivity extends SlidingActivity {
	
	private EAdvogadoDbHelper dbHelper;
	private Processoendpoint processoEndpoint;
	
	private ConsultarMeusProcessosTask consultarMeusProcessosTask;
	private ConsultarMeuProcessoTask consultarProcessoTask;
	
	private View listarFormView;
	private View statusView;
	private TextView statusMessageView;

	private ListView processosListView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbHelper = new EAdvogadoDbHelper(this);
		Processoendpoint.Builder procEndpointBuilder = new Processoendpoint.Builder(
		        AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
		        new HttpRequestInitializer() {
		          public void initialize(HttpRequest httpRequest) {
		          }
		        });
		processoEndpoint = CloudEndpointUtils.updateBuilder(procEndpointBuilder).build();
		
		setContentView(R.layout.activity_meus_processos);
		
		listarFormView = findViewById(R.id.listar_processos_form);
		statusView = findViewById(R.id.listar_status);
		statusMessageView = (TextView) findViewById(R.id.status_message);
		processosListView = (ListView) findViewById(R.id.listViewMeusProcessos);
		processosListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ProcessoDTO processoDTO = (ProcessoDTO) 
						processosListView.getItemAtPosition(arg2);
				carregarMeuProcesso(processoDTO);
			}
		});
		
		carregarProcessos();
	}
	
	/**
	 * 
	 */
	public void carregarProcessos() {
		if (consultarMeusProcessosTask != null) {
			return;
		}
		// Show a progress spinner, and kick off a background task to
		// perform the user login attempt.
		statusMessageView.setText(R.string.msg_carregando_dados);
		showProgress(true, statusView, listarFormView);
		consultarMeusProcessosTask = new ConsultarMeusProcessosTask();
		consultarMeusProcessosTask.execute((Void) null);
	}
	
	/**
	 * @param processoDTO
	 */
	public void carregarMeuProcesso(ProcessoDTO processoDTO) {
		if (consultarProcessoTask != null) {
			return;
		}
		// Show a progress spinner, and kick off a background task to
		// perform the user login attempt.
		statusMessageView.setText(R.string.msg_carregando_dados);
		showProgress(true, statusView, listarFormView);
		consultarProcessoTask = new ConsultarMeuProcessoTask();
		consultarProcessoTask.execute(processoDTO);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_preferences:
				Intent intent = new Intent();
				intent.setClass(this, PreferencesActivity.class);
				startActivity(intent);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	/**
	 * @author Cleber
	 *
	 */
	public class ConsultarMeusProcessosTask extends AsyncTask<Void, Void, List<ProcessoDTO>> {
		@Override
		protected List<ProcessoDTO> doInBackground(Void... params) {
			List<ProcessoDTO> processos = null;
			try {
				processos = dbHelper.selectProcessos();
			} catch (Exception e) {
				Log.e("E-Advogado",
						"Falha ao carregar processos do BD", e);
			}
			return processos;
		}

		@Override
		protected void onPostExecute(List<ProcessoDTO> processos) {
			consultarMeusProcessosTask = null;
			showProgress(false, statusView, listarFormView);

			if (processos != null) {
				ProcessoAdapter processoAdapter = new ProcessoAdapter(
						MeusProcessosActivity.this,
						R.layout.processo_list_item, processos);
				processosListView.setAdapter(processoAdapter);
			} else {
				Toast.makeText(MeusProcessosActivity.this,
						R.string.msg_nao_foi_possivel_carregar_dados,
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected void onCancelled() {
			consultarMeusProcessosTask = null;
			showProgress(false, statusView, listarFormView);
		}
	}
	
	public class ConsultarMeuProcessoTask extends AsyncTask<ProcessoDTO, Void, ProcessoDTO> {

		@Override
		protected ProcessoDTO doInBackground(ProcessoDTO... params) {
			ProcessoDTO processoDTO = null;
			Processo processo = null;
			try {
				processo = processoEndpoint.consultarProcesso(
						params[0].getProcesso().getNpu().replaceAll("[-.]", ""), 
						params[0].getProcesso().getTipoJuizo(), 
						params[0].getTribunal().getId().getId()).execute();
			} catch (Exception e) {
				Log.e("E-Advogado",
						"Falha ao carregar processo pelo servico", e);
			}
			if (processo != null) {
				processoDTO = new ProcessoDTO();
				processoDTO.setTribunal(params[0].getTribunal());
				processoDTO.setProcesso(processo);
			}
			return processoDTO;
		}

		@Override
		protected void onPostExecute(ProcessoDTO processoDTO) {
			consultarProcessoTask = null;
			showProgress(false, statusView, listarFormView);

			if (processoDTO != null) {
				ProcessoTabsPagerFragment.processoResult = processoDTO;
				Intent intent = new Intent();
				intent.setClass(MeusProcessosActivity.this,
						ProcessoTabsPagerFragment.class);
				startActivity(intent);
			} else {
				Toast.makeText(MeusProcessosActivity.this,
						R.string.msg_nao_foi_possivel_carregar_dados,
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		protected void onCancelled() {
			consultarProcessoTask = null;
			showProgress(false, statusView, listarFormView);
		}
	}

}
