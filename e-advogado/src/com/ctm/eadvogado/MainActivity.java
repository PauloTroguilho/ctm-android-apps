package com.ctm.eadvogado;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ctm.eadvogado.util.Consts;

public class MainActivity extends SlidingActivity {
	
	private ListView listViewMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listViewMenu = (ListView) findViewById(R.id.listViewMain);
		listViewMenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = null;
				switch (position) {
				case 0:
					if (!Consts.VERSAO_GRATIS) {
						intent = new Intent();
						intent.setClass(MainActivity.this, MeusProcessosActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(MainActivity.this,
								R.string.msg_op_incluir_processo_disponivel_versao_paga,
								Toast.LENGTH_LONG).show();
					}
					break;
				case 1:
					intent = new Intent();
					intent.setClass(MainActivity.this, ConsultarProcessoActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent();
					intent.setClass(MainActivity.this, PreferencesActivity.class);
					startActivity(intent);
					break;
				}
				
			}
		});
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

}
