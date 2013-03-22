package br.com.ctm.guiamedico;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import br.com.ctm.guiamedico.adapters.MenuItemAdapter;

public class MenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		GridView gridView = (GridView) findViewById(R.id.grdVwMenu);
		// Instance of ImageAdapter Class
		gridView.setAdapter(new MenuItemAdapter(this));
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// Run next activity
				Intent intent = new Intent();
				switch (position) {
				case 0:

					break;
				case 1:
					intent.setClass(MenuActivity.this, MedicosListActivity.class);
					break;
				}
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_menu, menu);
		return true;
	}

}
