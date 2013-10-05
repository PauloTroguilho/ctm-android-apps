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

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.ctm.eadvogado.db.EAdvogadoDbHelper;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Processo;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal;
import com.ctm.eadvogado.fragment.TabProcessoDadosFragment;
import com.ctm.eadvogado.fragment.TabProcessoMovimentoFragment;
import com.ctm.eadvogado.fragment.TabProcessoPolosFragment;
import com.ctm.eadvogado.tasks.AssociarProcessoTask;
import com.ctm.eadvogado.tasks.ConsultarProcessoTask;
import com.ctm.eadvogado.util.Consts;
import com.ctm.eadvogado.util.MessageUtils;

/**
 * Demonstrates combining a TabHost with a ViewPager to implement a tab UI that
 * switches between tabs and also allows the user to perform horizontal flicks
 * to move between the tabs.
 */
public class ProcessoTabsPagerFragment extends SherlockFragmentActivity {

	TabHost mTabHost;
	ViewPager mViewPager;
	TabsAdapter mTabsAdapter;

	private SharedPreferences preferences;
	private EAdvogadoDbHelper dbHelper;
	private AssociarProcessoTask associarProcessoTask;
	private ConsultarProcessoTask atualizarProcessoTask;
	
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
					mTabHost.newTabSpec("movimento").setIndicator(
							getString(R.string.processo_tab_movimento)),
					TabProcessoMovimentoFragment.class, null);
			mTabsAdapter.addTab(
					mTabHost.newTabSpec("dados").setIndicator(
							getString(R.string.processo_tab_dados)),
					TabProcessoDadosFragment.class, null);
			mTabsAdapter.addTab(
					mTabHost.newTabSpec("polos").setIndicator(
							getString(R.string.processo_tab_polos)),
					TabProcessoPolosFragment.class, null);
			
			mTabsAdapter.onTabChanged("dados");
		}
		updateButtons();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		dbHelper = new EAdvogadoDbHelper(this);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.processo_tabs_pager_fragment);
		
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
	public void doSalvarProcesso(final Processo processo) {
		if (associarProcessoTask != null) {
			MessageUtils.alert("Por favor, aguarde! Já estamos processando sua solicitação.", this);
			return;
		}
		setSupportProgressBarIndeterminateVisibility(true);
		setControlsEnabled(false);
		associarProcessoTask = new AssociarProcessoTask(this) {
			boolean isProcessoSalvo = false;
			@Override
			protected void onPostExecute(Boolean result) {
				if (result != null) {
					if (result.booleanValue()) {
						dbHelper.insertOrUpdateProcesso(processo);
						alert(R.string.msg_processo_inserido_sucesso);
						isProcessoSalvo = true;
					} else {
						alert(R.string.msg_erro_inesperado);
					}
				} else {
					if (getErrorMessage().length() == 0) {
						alert(R.string.msg_erro_inesperado);
					} else {
						alert(getErrorMessage());
					}
				}
				associarProcessoTask = null;
				setSupportProgressBarIndeterminateVisibility(false);
				setControlsEnabled(true);
				menuSalvar.setVisible(!isProcessoSalvo);
			}
			
			@Override
			protected void onCancelled(Boolean result) {
				associarProcessoTask = null;
				setSupportProgressBarIndeterminateVisibility(false);
				setControlsEnabled(true);
				menuSalvar.setVisible(!isProcessoSalvo);
			}
			
		};
		associarProcessoTask.execute(getEmail(), processo.getKey().getId().toString());
	}
	
	public void doAtualizarProcesso(String npu, Long idTribunal, String tipoJuizo) {
		if (atualizarProcessoTask != null) {
			MessageUtils.alert("Por favor, aguarde! Já existe uma atualização em andamento.", this);
			return;
		}
		setSupportProgressBarIndeterminateVisibility(true);
		setControlsEnabled(false);
		atualizarProcessoTask = new ConsultarProcessoTask(this) {
			@Override
			protected void onPostExecute(Processo result) {
				if (result != null) {
					Tribunal tribunal = dbHelper.selectTribunalPorId(result.getTribunal().getId());
					if (tribunal != null) {
						result.put("tribunal.sigla", tribunal.getSigla());
						result.put("tribunal.nome", tribunal.getSigla());
					}
					processoResult = result;
					carregarTelaProcesso();
				} else {
					if (getErrorMessage().length() == 0) {
						alert(R.string.msg_erro_inesperado);
					} else {
						alert(getErrorMessage());
					}
				}
				atualizarProcessoTask = null;
				setSupportProgressBarIndeterminateVisibility(false);
				setControlsEnabled(true);
			}
			@Override
			protected void onCancelled(Processo result) {
				atualizarProcessoTask = null;
				setSupportProgressBarIndeterminateVisibility(false);
				setControlsEnabled(true);
			}
		};
		atualizarProcessoTask.execute(npu, idTribunal.toString(), tipoJuizo, Boolean.TRUE.toString());
	}
	
	private void setControlsEnabled(boolean enabled) {
		if (menuSalvar != null) 
			menuSalvar.setVisible(enabled);
		if (menuAtualizar != null)
			menuAtualizar.setVisible(enabled);
		updateButtons();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item == menuSalvar) {
			doSalvarProcesso(processoResult);
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
    				processoResult.getTribunal().getId(), processoResult.getTipoJuizo(), false);
    		if (selectProcesso != null) {
    			menuSalvar.setVisible(false);
    		}
        }
	}
	
	protected String getEmail() {
		return preferences.getString(PreferencesActivity.PREFS_KEY_EMAIL, "");
	}
}
