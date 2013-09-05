package com.ctm.eadvogado;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ctm.eadvogado.db.EAdvogadoDbHelper;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal;
import com.ctm.eadvogado.tasks.AtualizarTribunaisTask;
import com.ctm.eadvogado.tasks.RateThisAppTask;
import com.ctm.eadvogado.util.Consts;
import com.ctm.eadvogado.util.MessageUtils;

public class MainActivity extends SlidingActivity {
	
	private ListView listViewMenu;
	
	private EAdvogadoDbHelper dbHelper;
	
	private RateThisAppTask rateThisAppTask;	
	private AtualizarTribunaisTask atualizarTribunaisTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initAdmobBanner(R.id.adView);
		dbHelper = new EAdvogadoDbHelper(this);
		
		listViewMenu = (ListView) findViewById(R.id.listViewMain);
		listViewMenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				switch (position) {
				case 0:
					startActivity(new Intent(MainActivity.this, MeusProcessosActivity.class));
					break;
				case 1:
					startActivity(new Intent(MainActivity.this, ConsultarProcessoActivity.class));
					break;
				case 2:
					startActivity(new Intent(MainActivity.this, MinhaContaActivity.class));
					break;
				case 3:
					startActivity(new Intent(MainActivity.this, PreferencesActivity.class));
					break;
				}
				
			}
		});
		
		if (savedInstanceState == null) {
			try {
				GCMIntentService.register(getApplicationContext());
			} catch (Exception e) {
			    Log.e(TAG,
			        "Exception received when attempting to register for Google Cloud "
					+ "Messaging. Perhaps you need to set your virtual device's "
					+ " target to Google APIs? "
					+ "See https://developers.google.com/eclipse/docs/cloud_endpoints_android"
					+ " for more information.", e);
			}
			
			carregarListaTribunais();
			
			if (!isAppRated()) {
				long intervalUltimaRate = System.currentTimeMillis() - getUltimaRateThisApp();
				if (intervalUltimaRate >= Consts.TRES_DIAS_EM_MILLIS) {
					doRateThisApp();
				}
			}
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
				startActivity(new Intent(this, PreferencesActivity.class));
		        return(true);
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
     * @param sku
     */
    private void doRateThisApp() {
    	if (rateThisAppTask != null){
    		return;
    	}
		rateThisAppTask = new RateThisAppTask(this){
			@Override
			protected void onPostExecute(Boolean result) {
				if (result) {
					preferences.edit()
						.putLong(PreferencesActivity.PREFS_KEY_ULTIMA_RATE_THIS_APP,
							System.currentTimeMillis())
						.commit();
					new AlertDialog.Builder(MainActivity.this)
			        .setIcon(android.R.drawable.ic_dialog_alert)
			        .setMessage(R.string.msg_avaliacao_app)
			        .setPositiveButton(R.string.btn_sim, new DialogInterface.OnClickListener() {
			            @Override
			            public void onClick(DialogInterface dialog, int which) {
			            	Intent i = new Intent(Intent.ACTION_VIEW);
							i.setData(Uri.parse("market://details?id=" + getPackageName()));
							startActivity(i);
							preferences.edit().putBoolean(PreferencesActivity.PREFS_KEY_RATE_THIS_APP, true).commit();    
			            }

			        })
			        .setNegativeButton(R.string.btn_nao, null)
			        .show();
				}
				rateThisAppTask = null;
			}
			@Override
			protected void onCancelled(Boolean result) {
				rateThisAppTask = null;
			}
		};
		rateThisAppTask.execute((Void)null);
    }
    
    /**
     * @param enabled
     */
    private void setControlsEnabled(boolean enabled){
    	listViewMenu.setEnabled(enabled);
    }
    
    
    private void carregarListaTribunais() {
    	List<Tribunal> tribunais = dbHelper.selectTribunais();
		long intervaloAtualizacao = System.currentTimeMillis() - getUltimaAtualizacao();
		if (tribunais.isEmpty() || intervaloAtualizacao > Consts.UM_DIA_EM_MILLIS) {
			doAtualizarTribunais();
		}
    }
    
    private void doAtualizarTribunais() {
    	if (atualizarTribunaisTask != null) {
    		Log.w(TAG, "Ja existe uma task de carregamento de tribunais em execucao");
    		return;
    	}
    	setSupportProgressBarIndeterminateVisibility(true);
    	setControlsEnabled(false);
    	atualizarTribunaisTask = new AtualizarTribunaisTask(this){
    		@Override
    		protected void onPostExecute(List<Tribunal> result) {
    			if (result != null) {
    				if (!result.isEmpty()) {
    					dbHelper.inserirTribunais(result);
						preferences.edit()
							.putLong(PreferencesActivity.PREFS_KEY_ULTIMA_ATUALIZACAO,
								System.currentTimeMillis())
							.commit();
    				} else {
    					if (dbHelper.selectTribunaisCount() == 0) {
    						if (getErrorMessage().length() == 0) {
        						alert(R.string.msg_erro_inesperado);
        					} else {
        						alert(getErrorMessage());
        					}
    					}
    				}
    				atualizarTribunaisTask = null;
    				setSupportProgressBarIndeterminateVisibility(false);
    				setControlsEnabled(true);
    			}
    		}
    		@Override
    		protected void onCancelled(List<Tribunal> result) {
    			atualizarTribunaisTask = null;
				setSupportProgressBarIndeterminateVisibility(false);
				setControlsEnabled(true);
    		}
    	};
    	atualizarTribunaisTask.execute((Void)null);
    }

}
