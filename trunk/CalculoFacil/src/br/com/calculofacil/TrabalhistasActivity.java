package br.com.calculofacil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TrabalhistasActivity extends AdMobActivity {
	
	private ListView listView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_trabalhista);
		
		listView = (ListView) findViewById(R.id.listView1);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Run next activity
				Intent intent = new Intent();
				switch(position) {
					case 0:
						intent.setClass(TrabalhistasActivity.this, CalculoRescisaoActivity.class);
					break;
				}
				startActivity(intent);
			}
		});
		
		setAdView();
	}
	
	


}