package com.ctm.eadvogado;

import java.io.IOException;
import java.util.ArrayList;
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
import com.ctm.eadvogado.adapters.ProcessoUsuarioAdapter;
import com.ctm.eadvogado.db.EAdvogadoDbHelper;
import com.ctm.eadvogado.endpoints.processoEndpoint.ProcessoEndpoint;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Key;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Processo;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.ProcessoUsuario;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.TribunalEndpoint;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal;
import com.ctm.eadvogado.util.EndpointUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class MeusProcessosActivity extends SlidingActivity {
	
	private ProcessoEndpoint processoEndpoint;
	private TribunalEndpoint tribunalEndpoint;
	private EAdvogadoDbHelper dbHelper;
	
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
		processoEndpoint = EndpointUtils.initProcessoEndpoint();
		tribunalEndpoint = EndpointUtils.initTribunalEndpoint();
		
		setContentView(R.layout.activity_meus_processos);
		initAdmobBanner(R.id.adView);
		
		listarFormView = findViewById(R.id.listar_processos_form);
		statusView = findViewById(R.id.listar_status);
		statusMessageView = (TextView) findViewById(R.id.status_message);
		processosListView = (ListView) findViewById(R.id.listViewMeusProcessos);
		processosListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ProcessoUsuario processoUsuario = (ProcessoUsuario) 
						processosListView.getItemAtPosition(arg2);
				carregarMeuProcesso(processoUsuario);
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
	 * @param processoUsuario
	 */
	public void carregarMeuProcesso(ProcessoUsuario processoUsuario) {
		if (consultarProcessoTask != null) {
			return;
		}
		// Show a progress spinner, and kick off a background task to
		// perform the user login attempt.
		statusMessageView.setText(R.string.msg_carregando_dados);
		showProgress(true, statusView, listarFormView);
		consultarProcessoTask = new ConsultarMeuProcessoTask();
		consultarProcessoTask.execute(processoUsuario);
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
	public class ConsultarMeusProcessosTask extends AsyncTask<Void, Void, List<ProcessoUsuario>> {
		
		String mensagem = "";
		
		@Override
		protected List<ProcessoUsuario> doInBackground(Void... params) {
			List<ProcessoUsuario> processos = null;
			int tries = 3;
			int attempt = 0;
			while (attempt < tries && processos == null) {
				try {
					processos = processoEndpoint.consultarProcessosDoUsuario(
							getEmail(), getSenha()).execute().getItems();
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
			return processos;
		}

		@Override
		protected void onPostExecute(List<ProcessoUsuario> processos) {
			consultarMeusProcessosTask = null;
			showProgress(false, statusView, listarFormView);
			if (processos != null && !processos.isEmpty()) {
				
				for (ProcessoUsuario processoUsuario : processos) {
					Processo p = new Processo();
					p.setNpu(processoUsuario.getNpu());
					p.setTipoJuizo(processoUsuario.getTipoJuizo());
					p.setTribunal(new Key());
					p.getTribunal().setId(processoUsuario.getIdTribunal());
					try {
						dbHelper.insertProcessoSeNaoExiste(p);
					} catch (Exception e) {
						Log.e(TAG, "Falha ao inserir processo do usuario.");
					}
				}
				
				ProcessoUsuarioAdapter processoAdapter = new ProcessoUsuarioAdapter(
						MeusProcessosActivity.this,
						R.layout.processo_list_item, processos);
				processosListView.setAdapter(processoAdapter);
			} else {
				if (mensagem.length() == 0) {
					Toast.makeText(MeusProcessosActivity.this,
							R.string.msg_nenhum_processo_cadastrado,
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(MeusProcessosActivity.this,
							mensagem, Toast.LENGTH_LONG).show();
				}
			}
		}

		@Override
		protected void onCancelled() {
			consultarMeusProcessosTask = null;
			showProgress(false, statusView, listarFormView);
		}
	}
	
	public class ConsultarMeuProcessoTask extends AsyncTask<ProcessoUsuario, Void, Processo> {

		@Override
		protected Processo doInBackground(ProcessoUsuario... params) {
			Processo processo = null;
			try {
				processo = processoEndpoint.consultarProcesso(
						params[0].getNpu().replaceAll("[-.]", ""),
						params[0].getIdTribunal(),
						params[0].getTipoJuizo(), false).execute();
			} catch (Exception e) {
				Log.e(TAG,
						"Falha ao carregar processo pelo servico", e);
			}
			if (processo != null) {
				Tribunal tribunal = dbHelper.selectTribunalPorId(params[0].getIdTribunal());
				if (tribunal == null) {
					List<Tribunal> tribunais = new ArrayList<Tribunal>();
					int tries = 3;
					int attempt = 0;
					while (attempt < tries) {
						try {
							tribunais = tribunalEndpoint.listAll()
									.set("sortField", "sigla")
									.set("sortOrder", "ASC")
									.execute().getItems();
							dbHelper.inserirTribunais(tribunais);
							tribunal = dbHelper.selectTribunalPorId(params[0].getIdTribunal());
							break;
						} catch (IOException e) {
							Log.e("E-Advogado", "Falha ao carregar tribunais pelo servico", e);
						}
					}
				}
				processo.put("tribunal.sigla", tribunal.getSigla());
				processo.put("tribunal.nome", tribunal.getSigla());
			}
			return processo;
		}

		@Override
		protected void onPostExecute(Processo processo) {
			consultarProcessoTask = null;
			showProgress(false, statusView, listarFormView);

			if (processo != null) {
				ProcessoTabsPagerFragment.processoResult = processo;
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
