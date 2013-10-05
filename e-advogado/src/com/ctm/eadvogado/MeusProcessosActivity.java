package com.ctm.eadvogado;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ctm.eadvogado.adapters.ProcessoAdapter;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Processo;
import com.ctm.eadvogado.tasks.CarregarMeusProcessosTask;

public class MeusProcessosActivity extends SlidingActivity {
	
	private CarregarMeusProcessosTask carregarMeusProcessosTask;
	
	private View listarFormView;
	private View statusView;
	private TextView statusMessageView;

	private ListView processosListView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
				Processo processo = (Processo) 
						processosListView.getItemAtPosition(arg2);
				doCarregarProcesso(processo);
			}
		});
		doCarregarMeusProcessos(false);
	}
	
	/**
	 * 
	 */
	public void doCarregarMeusProcessos(Boolean doSync) {
		if (carregarMeusProcessosTask != null) {
			return;
		}
		// Show a progress spinner, and kick off a background task to
		// perform the user login attempt.
		statusMessageView.setText(R.string.msg_carregando_dados);
		showProgress(true, statusView, listarFormView);
		carregarMeusProcessosTask = new CarregarMeusProcessosTask(this) {
			@Override
			protected void onPostExecute(List<Processo> result) {
				if (result != null) {
					ProcessoAdapter processoAdapter = new ProcessoAdapter(
							MeusProcessosActivity.this,
							R.layout.processo_list_item, result);
					processosListView.setAdapter(processoAdapter);
				} else {
					if (getErrorMessage().length() == 0) {
						alert(R.string.msg_erro_inesperado);
					} else {
						alert(getErrorMessage());
					}
				}
				carregarMeusProcessosTask = null;
				showProgress(false, statusView, listarFormView);
			}
			@Override
			protected void onCancelled(List<Processo> result) {
				carregarMeusProcessosTask = null;
				showProgress(false, statusView, listarFormView);
			}
		};
		carregarMeusProcessosTask.execute(
				PreferencesActivity.getEmail(MeusProcessosActivity.this), 
				PreferencesActivity.getSenha(MeusProcessosActivity.this), 
				doSync.toString());
	}
	
	/**
	 * @param processo
	 */
	public void doCarregarProcesso(Processo processo) {
		if (processo != null) {
			ProcessoRootFragment.PROCESSO = processo;
			Intent intent = new Intent(MeusProcessosActivity.this, ProcessoRootFragment.class);
			intent.putExtra(ProcessoRootFragment.BUNDLE_NPU, processo.getNpu());
			intent.putExtra(ProcessoRootFragment.BUNDLE_TRIBUNAL, processo.getTribunal().getId());
			intent.putExtra(ProcessoRootFragment.BUNDLE_TIPO_JUIZO, processo.getTipoJuizo());
			startActivity(intent);
		}
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

}
