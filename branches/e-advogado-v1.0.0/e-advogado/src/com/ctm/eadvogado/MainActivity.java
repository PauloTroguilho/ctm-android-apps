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
	
	public static MainActivity INSTANCE = null;
	
	private ListView listViewMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		INSTANCE = this;
		listViewMenu = (ListView) findViewById(R.id.listViewMain);
		listViewMenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = null;
				switch (position) {
				case 0:
					// Run next activity
					intent = new Intent();
					intent.setClass(MainActivity.INSTANCE, MeusProcessosActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent();
					intent.setClass(MainActivity.INSTANCE, ConsultarProcessoActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent();
					intent.setClass(MainActivity.INSTANCE, PreferencesActivity.class);
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
				Intent intent = new Intent();
				intent.setClass(this, PreferencesActivity.class);
				startActivity(intent);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
