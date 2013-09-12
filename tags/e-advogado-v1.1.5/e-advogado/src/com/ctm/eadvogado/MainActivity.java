package com.ctm.eadvogado;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SlidingActivity {
	
	private ListView listViewMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initAdmobBanner(R.id.adView);
		
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