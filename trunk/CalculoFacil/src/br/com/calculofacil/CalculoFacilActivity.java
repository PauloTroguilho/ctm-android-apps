package br.com.calculofacil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CalculoFacilActivity extends AdMobActivity {
	
	private static final int DIALOG_AJUDA = 2;

	private ListView listView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		listView = (ListView) findViewById(R.id.listView1);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Run next activity
				Intent intent = new Intent();
				switch(position) {
					case 0:
						intent.setClass(CalculoFacilActivity.this, TrabalhistasActivity.class);
					break;
					case 1:
						intent.setClass(CalculoFacilActivity.this, CalculoBoletoActivity.class);
					break;
				}
				startActivity(intent);
			}
		});

		setAdView();
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DIALOG_AJUDA:
				return new AlertDialog.Builder(CalculoFacilActivity.this)
						.setIcon(R.drawable.icon_help).setTitle(getString(R.string.menu_ajuda))
						.setMessage(getString(R.string.dialog_ajuda)).setPositiveButton(
								R.string.alert_dialog_ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										/* User clicked OK so do some stuff */
									}
								}).create();
		}
		return null;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.ajuda:
				showDialog(DIALOG_AJUDA);
				return true;
			case R.id.sair:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}