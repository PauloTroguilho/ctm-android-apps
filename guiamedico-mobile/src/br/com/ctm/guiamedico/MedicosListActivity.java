package br.com.ctm.guiamedico;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.ctm.guiamedico.adapters.EspecialidadesAdapter;
import br.com.ctm.guiamedico.adapters.MedicoAdapter;
import br.com.ctm.guiamedico.model.Especialidade;
import br.com.ctm.guiamedico.model.Medico;
import br.com.ctm.guiamedico.service.EspecialidadeService;
import br.com.ctm.guiamedico.service.MedicoService;

public class MedicosListActivity extends Activity {

	private MedicoAdapter medicoListAdapter;
	private EspecialidadesAdapter especialidadesAdapter;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medicos_list);

		EditText nomeMedico = (EditText) findViewById(R.id.edtTxPesquisaMedico);
		nomeMedico.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				medicoListAdapter.getFilter().filter(s);
			}

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
		});

		// Show the Up button in the action bar.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		new RequestItemsServiceTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_medicos_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * populate list in background while showing progress dialog.
	 */
	private class RequestItemsServiceTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog dialog = new ProgressDialog(
				MedicosListActivity.this);

		private List<Medico> medicosList;
		private List<Especialidade> especialidadeList;

		@Override
		protected void onPreExecute() {
			dialog.setMessage(getText(R.string.msg_aguarde));
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... unused) {
			// The ItemService would contain the method showed
			// in the previous paragraph
			MedicoService mSer = new MedicoService();
			try {
				medicosList = mSer.findAll();
			} catch (IOException e) {
				medicosList = new ArrayList<Medico>();
			}

			EspecialidadeService eSrv = new EspecialidadeService();
			try {
				especialidadeList = eSrv.findAll();
			} catch (IOException e) {
				especialidadeList = new ArrayList<Especialidade>();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void unused) {
			if (medicosList.isEmpty()) {
				Toast.makeText(MedicosListActivity.this,
						R.string.msg_nao_foi_possivel_carregar_dados,
						Toast.LENGTH_LONG).show();
			}
			// setListAdapter must not be called at doInBackground()
			// since it would be executed in separate Thread
			ListView listViewMedicos = (ListView) findViewById(R.id.listViewMedicos);
			medicoListAdapter = new MedicoAdapter(MedicosListActivity.this,
					R.layout.activity_medicos_list_item, medicosList);
			listViewMedicos.setAdapter(medicoListAdapter);

			Spinner spinEspec = (Spinner) findViewById(R.id.spinEspecialidades);
			especialidadesAdapter = new EspecialidadesAdapter(
					MedicosListActivity.this,
					R.layout.spinner_especialidades_item, especialidadeList);
			spinEspec.setAdapter(especialidadesAdapter);

			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}

}
