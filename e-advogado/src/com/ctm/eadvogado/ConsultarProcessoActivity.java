package com.ctm.eadvogado;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ctm.eadvogado.adapters.TipoJuizoAdapter;
import com.ctm.eadvogado.adapters.TribunalAdapter;
import com.ctm.eadvogado.db.EAdvogadoContract;
import com.ctm.eadvogado.db.EAdvogadoDbHelper;
import com.ctm.eadvogado.dto.TipoJuizo;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Processo;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal;
import com.ctm.eadvogado.tasks.CarregarTribunaisTask;
import com.ctm.eadvogado.tasks.ConsultarProcessoTask;
import com.ctm.eadvogado.util.Mask;

public class ConsultarProcessoActivity extends SlidingActivity {

	private EAdvogadoDbHelper dbHelper;

	private View mConsultarFormView;
	private View mConsultarStatusView;
	private TextView mConsultarStatusMessageView;
	private Button mConsultarButtonView;
	private Spinner mSpinnerTribunais;
	private Spinner mSpinnerTipoJuizo;
	private EditText mEditTextNPU;
	
	private CarregarTribunaisTask carregarTribunaisTask = null;
	private ConsultarProcessoTask consultarProcessoTask = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbHelper = new EAdvogadoDbHelper(this);
		
		setTitle(R.string.activity_consultar_processo_title);
		setContentView(R.layout.activity_consultar_processo);
		initAdmobBanner(R.id.adView);

		mSpinnerTribunais = (Spinner) findViewById(R.id.spinnerTribunal);
		mSpinnerTipoJuizo = (Spinner) findViewById(R.id.spinnerTipoJuizo);
		mSpinnerTipoJuizo.setAdapter(new TipoJuizoAdapter(this,
				R.layout.tipojuizo_list_item, Arrays.asList(TipoJuizo
						.values())));
		mConsultarFormView = findViewById(R.id.consultar_processo_form);
		mConsultarStatusView = findViewById(R.id.consultar_processo_status);
		mConsultarStatusMessageView = (TextView) findViewById(R.id.consultar_processo_status_message);
		mEditTextNPU = (EditText) findViewById(R.id.editTextNPU);
		//0130055-55.2013.5.13.0015
		mEditTextNPU.addTextChangedListener(Mask.insert("#######-##.####.#.##.####", mEditTextNPU));
		//mEditTextNPU.getText().append("01300555520135130015");
		mConsultarButtonView = (Button) findViewById(R.id.buttonConsultarProcesso);
		mConsultarButtonView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				doConsultarProcessos();
			}
		});
		doCerregarTribunais();
	}

	/**
	 * Consulta os Processos
	 */
	public void doConsultarProcessos() {
		if (consultarProcessoTask != null) {
			alert("Por favor, aguarde! Sua consulta está sendo processada.");
			return;
		}
		if (mSpinnerTribunais.getAdapter().isEmpty()) {
			alert("Não foi possível carregar a lista de tribunais! Por favor, tente novamente.");
			return;
		}
		if (mEditTextNPU.getText().toString().trim().length() != 25){
			alert("O NPU informado está inválido ou incompleto! Por favor, tente novamente.");
			return;
		}
		Tribunal tribunal = (Tribunal) mSpinnerTribunais.getAdapter().getItem(
				mSpinnerTribunais.getSelectedItemPosition());
		TipoJuizo tipoJuizo = (TipoJuizo) mSpinnerTipoJuizo.getSelectedItem();
		String npu = mEditTextNPU.getText().toString();
		
		if (tipoJuizo.equals(TipoJuizo.PRIMEIRO_GRAU)
				&& (tribunal.getPje1gEndpoint() == null || tribunal
						.getPje1gEndpoint().trim().length() == 0)) {
			alert("Este tribunal não disponibiliza a consulta processual para o 1º Grau!");
			return;
		} else if (tipoJuizo.equals(TipoJuizo.SEGUNDO_GRAU)
				&& (tribunal.getPje2gEndpoint() == null || tribunal
						.getPje2gEndpoint().trim().length() == 0)) {
			alert("Este tribunal não disponibiliza a consulta processual para o 2º Grau!");
			return;
		}
		// Show a progress spinner, and kick off a background task to
		// perform the user login attempt.
		mConsultarStatusMessageView.setText(R.string.msg_consultando_processos);
		showProgress(true, mConsultarStatusView, mConsultarFormView);
		consultarProcessoTask = new ConsultarProcessoTask(this) {
			@Override
			protected void onPostExecute(Processo result) {
				if (result != null) {
					Tribunal tribunal = dbHelper.selectTribunalPorId(result.getTribunal().getId());
					Integer qtdConsultas = (Integer) tribunal.get(EAdvogadoContract.TribunalTable.COLUMN_NAME_QTD_CONSULTAS);
					tribunal.set(EAdvogadoContract.TribunalTable.COLUMN_NAME_QTD_CONSULTAS, qtdConsultas + 1);
					dbHelper.updateTribunal(tribunal);
					if (tribunal != null) {
						result.put("tribunal.sigla", tribunal.getSigla());
						result.put("tribunal.nome", tribunal.getNome());
					}
					ProcessoTabsPagerFragment.processoResult = result;
					Intent intent = new Intent();
					intent.setClass(ConsultarProcessoActivity.this,
							ProcessoTabsPagerFragment.class);
					startActivity(intent);
				} else {
					if (getErrorMessage().length() == 0) {
						alert(R.string.msg_erro_inesperado);
					} else {
						alert(getErrorMessage());
					}
				}
				consultarProcessoTask = null;
				showProgress(false, mConsultarStatusView, mConsultarFormView);
			}
			@Override
			protected void onCancelled(Processo result) {
				consultarProcessoTask = null;
				showProgress(false, mConsultarStatusView, mConsultarFormView);
			}
		};
		consultarProcessoTask.execute(npu, tribunal.getKey().getId().toString(),
				tipoJuizo.name(), Boolean.FALSE.toString());
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void doCerregarTribunais() {
		if (carregarTribunaisTask != null) {
			return;
		}
		// Show a progress spinner, and kick off a background task to
		// perform the user login attempt.
		mConsultarStatusMessageView.setText(R.string.msg_carregando_dados);
		showProgress(true, mConsultarStatusView, mConsultarFormView);
		carregarTribunaisTask = new CarregarTribunaisTask(this){
			@Override
			protected void onPostExecute(List<Tribunal> result) {
				if (result != null && !result.isEmpty()) {
					TribunalAdapter tribunalAdapter = new TribunalAdapter(
							ConsultarProcessoActivity.this,
							R.layout.tribunal_list_item, result);
					mSpinnerTribunais.setAdapter(tribunalAdapter);
				} else {
					alert(R.string.msg_nao_foi_possivel_carregar_dados);
				}
				carregarTribunaisTask = null;
				showProgress(false, mConsultarStatusView, mConsultarFormView);
			}
			@Override
			protected void onCancelled(List<Tribunal> result) {
				carregarTribunaisTask = null;
				showProgress(false, mConsultarStatusView, mConsultarFormView);
			}
		};
		carregarTribunaisTask.execute((Void) null);
	}

}
