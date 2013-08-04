/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ctm.eadvogado;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ctm.eadvogado.db.EAdvogadoDbHelper;
import com.ctm.eadvogado.endpoints.processoEndpoint.ProcessoEndpoint;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Processo;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.TribunalEndpoint;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal;
import com.ctm.eadvogado.fragment.TabProcessoDadosFragment;
import com.ctm.eadvogado.fragment.TabProcessoMovimentoFragment;
import com.ctm.eadvogado.fragment.TabProcessoPolosFragment;
import com.ctm.eadvogado.util.Consts;
import com.ctm.eadvogado.util.EndpointUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

/**
 * Demonstrates combining a TabHost with a ViewPager to implement a tab UI that
 * switches between tabs and also allows the user to perform horizontal flicks
 * to move between the tabs.
 */
public class ProcessoTabsPagerFragment extends SlidingActivity {

	TabHost mTabHost;
	ViewPager mViewPager;
	TabsAdapter mTabsAdapter;

	private ProcessoEndpoint processoEndpoint;
	private TribunalEndpoint tribunalEndpoint;
	
	private EAdvogadoDbHelper dbHelper;
	private SalvarProcessoTask salvarProcessoTask;
	private AtualizarProcessoTask atualizarProcessoTask;
	
	public static Processo processoResult;
	
	protected void carregarTelaProcesso() {
		if (processoResult != null) {
			if (mTabHost == null) {
				mTabHost = (TabHost) findViewById(android.R.id.tabhost);
				mTabHost.setup();
			} else {
				mTabHost.getTabWidget().removeAllViews();
			}
			mViewPager = (ViewPager) findViewById(R.id.pager);

			mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);
			mTabsAdapter.addTab(
					mTabHost.newTabSpec("dados").setIndicator(
							getString(R.string.processo_tab_dados)),
					TabProcessoDadosFragment.class, null);
			mTabsAdapter.addTab(
					mTabHost.newTabSpec("polos").setIndicator(
							getString(R.string.processo_tab_polos)),
					TabProcessoPolosFragment.class, null);
			mTabsAdapter.addTab(
					mTabHost.newTabSpec("movimento").setIndicator(
							getString(R.string.processo_tab_movimento)),
					TabProcessoMovimentoFragment.class, null);
		}
		updateButtons();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbHelper = new EAdvogadoDbHelper(this);
		setContentView(R.layout.processo_tabs_pager_fragment);
		
		processoEndpoint = EndpointUtils.initProcessoEndpoint();
		tribunalEndpoint = EndpointUtils.initTribunalEndpoint();
		
		carregarTelaProcesso();
		
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.getBoolean("gcmIntentServiceMessage", false)) {
			String npu = extras.getString("npu");
			String idTribunal = extras.getString("idTribunal");
			String tipoJuizo = extras.getString("tipoJuizo");
			doAtualizarProcesso(npu, Long.parseLong(idTribunal), tipoJuizo);
		}
		
		if (savedInstanceState != null) {
			if (mTabHost != null)
				mTabHost.setCurrentTabByTag(savedInstanceState
					.getString("currentTab"));
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mTabHost != null) {
			outState.putString("currentTab", mTabHost.getCurrentTabTag());
		}
	}

	/**
	 * This is a helper class that implements the management of tabs and all
	 * details of connecting a ViewPager with associated TabHost. It relies on a
	 * trick. Normally a tab host has a simple API for supplying a View or
	 * Intent that each tab will show. This is not sufficient for switching
	 * between pages. So instead we make the content part of the tab host 0dp
	 * high (it is not shown) and the TabsAdapter supplies its own dummy view to
	 * show as the tab content. It listens to changes in tabs, and takes care of
	 * switch to the correct paged in the ViewPager whenever the selected tab
	 * changes.
	 */
	public static class TabsAdapter extends FragmentPagerAdapter implements
			TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
		private final Context mContext;
		private final TabHost mTabHost;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		static final class TabInfo {
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		static class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabsAdapter(FragmentActivity activity, TabHost tabHost,
				ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mTabHost = tabHost;
			mViewPager = pager;
			mTabHost.setOnTabChangedListener(this);
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
			tabSpec.setContent(new DummyTabFactory(mContext));
			String tag = tabSpec.getTag();

			TabInfo info = new TabInfo(tag, clss, args);
			mTabs.add(info);
			mTabHost.addTab(tabSpec);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(mContext, info.clss.getName(),
					info.args);
		}

		@Override
		public void onTabChanged(String tabId) {
			int position = mTabHost.getCurrentTab();
			mViewPager.setCurrentItem(position);
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			// Unfortunately when TabHost changes the current tab, it kindly
			// also takes care of putting focus on it when not in touch mode.
			// The jerk.
			// This hack tries to prevent this from pulling focus out of our
			// ViewPager.
			TabWidget widget = mTabHost.getTabWidget();
			int oldFocusability = widget.getDescendantFocusability();
			widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			mTabHost.setCurrentTab(position);
			widget.setDescendantFocusability(oldFocusability);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}
	
	/**
	 * Consulta os Processos
	 */
	public void doSalvarProcesso() {
		if (salvarProcessoTask != null) {
			alert("Por favor, aguarde! Já estamos processando sua solicitação.");
			return;
		}
		setSupportProgressBarIndeterminateVisibility(true);
		setControlsEnabled(false);
		salvarProcessoTask = new SalvarProcessoTask();
		salvarProcessoTask.execute((Void) null);
	}
	
	public void doAtualizarProcesso(String npu, Long idTribunal, String tipoJuizo) {
		if (atualizarProcessoTask != null) {
			alert("Por favor, aguarde! Já existe uma atualização em andamento.");
			return;
		}
		setSupportProgressBarIndeterminateVisibility(true);
		setControlsEnabled(false);
		atualizarProcessoTask = new AtualizarProcessoTask();
		atualizarProcessoTask.execute(npu, idTribunal.toString(), tipoJuizo);
	}
	
	private void setControlsEnabled(boolean enabled) {
		if (menuSalvar != null) 
			menuSalvar.setVisible(enabled);
		if (menuAtualizar != null)
			menuAtualizar.setVisible(enabled);
	}
	
	public class SalvarProcessoTask extends AsyncTask<Void, Void, Boolean> {
		
		boolean isProcessoSalvo = false;
		String mensagem = "";

		@Override
		protected Boolean doInBackground(Void... params) {
			Boolean result = Boolean.FALSE;
			try {
				processoEndpoint.associarProcessoAoUsuario(
						preferences.getString(PreferencesActivity.PREFS_KEY_EMAIL, ""), 
						processoResult.getKey().getId()).execute();
				result = Boolean.TRUE;
				dbHelper.insertProcessoSeNaoExiste(processoResult);
			} catch(GoogleJsonResponseException e) {
				Log.e(TAG, "Erro ao executar a operação!", e);
				mensagem = (e.getDetails() != null && e.getDetails() .getMessage() != null) ? 
						e.getDetails().getMessage() : getString(R.string.msg_erro_operacao_nao_realizada);
			} catch (IOException e) {
				Log.e(TAG, "Erro de comunicação ao executar a operação!", e);
				mensagem = getString(R.string.msg_erro_comunicacao_op_nao_realizada);
			} catch(Exception e) {
				Log.e(TAG, "Erro inesperado!", e);
				mensagem = getString(R.string.msg_erro_inesperado);
			}
			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result.booleanValue()) {
				Toast.makeText(ProcessoTabsPagerFragment.this,
						R.string.msg_processo_inserido_sucesso,
						Toast.LENGTH_LONG).show();
				isProcessoSalvo = true;
			} else {
				Toast.makeText(ProcessoTabsPagerFragment.this,
						mensagem, Toast.LENGTH_LONG).show();
			}
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
			menuSalvar.setVisible(!isProcessoSalvo);
			salvarProcessoTask = null;
		}

		@Override
		protected void onCancelled() {
			salvarProcessoTask = null;
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
			menuSalvar.setVisible(!isProcessoSalvo);
		}
		
	}
	
	
	public class AtualizarProcessoTask extends AsyncTask<String, Void, Processo> {
		
		String mensagem = "";
		
		@Override
		protected Processo doInBackground(String... params) {
			setControlsEnabled(false);
			Processo processo = null;
			String npu = params[0];
			Long idTribunal = Long.parseLong(params[1]);
			String tipoJuizo = params[2];
			
			int tries = 3;
			int attempt = 0;
			while (attempt < tries) {
				try {
					processo = processoEndpoint.consultarProcesso(
							npu, idTribunal, tipoJuizo, true)
							.execute();
					Tribunal tribunal = dbHelper.selectTribunalPorId(idTribunal);
					if (tribunal == null) {
						List<Tribunal> tribunais = new ArrayList<Tribunal>();
						int triesTrib = 3;
						int attemptTrib = 0;
						while (attemptTrib < triesTrib) {
							try {
								tribunais = tribunalEndpoint.listAll()
										.set("sortField", "sigla")
										.set("sortOrder", "ASC")
										.execute().getItems();
								dbHelper.inserirTribunais(tribunais);
								tribunal = dbHelper.selectTribunalPorId(idTribunal);
								break;
							} catch (IOException e) {
								Log.e("E-Advogado", "Falha ao carregar tribunais pelo servico", e);
							}
						}
					}
					if (tribunal != null) {
						processo.put("tribunal.sigla", tribunal.getSigla());
						processo.put("tribunal.nome", tribunal.getSigla());
					}
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

		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@Override
		protected void onPostExecute(Processo processo) {
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
			atualizarProcessoTask = null;
			if (processo != null) {
				processoResult = processo;
				carregarTelaProcesso();
	        	//finish();
			} else {
				if (mensagem.length() == 0) {
					Toast.makeText(ProcessoTabsPagerFragment.this,
							R.string.msg_nao_foi_possivel_carregar_dados,
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(ProcessoTabsPagerFragment.this,
							mensagem, Toast.LENGTH_LONG).show();
				}
			}
		}

		@Override
		protected void onCancelled() {
			setSupportProgressBarIndeterminateVisibility(false);
			setControlsEnabled(true);
			atualizarProcessoTask = null;
		}
		
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item == menuSalvar) {
			doSalvarProcesso();
		} else if (item == menuAtualizar) {
			doAtualizarProcesso(processoResult.getNpu(), processoResult
					.getTribunal().getId(), processoResult.getTipoJuizo());
		}
		return super.onOptionsItemSelected(item);
	}
	
	private MenuItem menuSalvar = null;
	private MenuItem menuAtualizar = null;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 //Used to put dark icons on light action bar
        boolean isLight = Consts.THEME == R.style.Theme_Sherlock_Light;

        menuSalvar = menu.add(R.string.btn_ab_salvar);
        menuSalvar.setIcon(isLight ? R.drawable.ic_content_save_inverse : R.drawable.ic_content_save)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        
        updateButtons();

        menuAtualizar = menu.add(R.string.btn_ab_atualizar);
		menuAtualizar.setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
			.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
	}
	
	private void updateButtons() {
		if (processoResult != null && menuSalvar != null) {
        	Processo selectProcesso = dbHelper.selectProcesso(processoResult.getNpu(),
    				processoResult.getTribunal().getId(), processoResult.getTipoJuizo());
    		if (selectProcesso != null) {
    			menuSalvar.setVisible(false);
    		}
        }
	}
}
