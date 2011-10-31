package br.com.calculofacil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import br.com.calculofacil.util.StringUtils;

public class CalculoRescisaoActivity extends AdMobActivity {

	private Calendar calendar = Calendar.getInstance();

	private List<String> errors = new ArrayList<String>();

	private static final int DIALOG_ERROR = 1;
	
	private static final float INSS = 0.08f; 

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rescisao_clt);

		final EditText dtAdmissao = (EditText) findViewById(R.id.edtDtAdmissao);
		final EditText dtDemissao = (EditText) findViewById(R.id.edtDtDemissao);

		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		dtAdmissao.setText(StringUtils.formatDate(calendar.getTime(),
				StringUtils.DATE_FORMAT));

		calendar.set(Calendar.DAY_OF_MONTH, calendar
				.getMaximum(Calendar.DAY_OF_MONTH));
		dtDemissao.setText(StringUtils.formatDate(calendar.getTime(),
				StringUtils.DATE_FORMAT));

		Button btCalcular = (Button) findViewById(R.id.btCalcular);
		btCalcular.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				errors.clear();
				if (!StringUtils.isValidDate(dtAdmissao.getText().toString(),
						StringUtils.DATE_FORMAT)) {
					errors.add("A data de admissão está inválida.");
				}
				if (!StringUtils.isValidDate(dtDemissao.getText().toString(),
						StringUtils.DATE_FORMAT)) {
					errors.add("A data de demissão está inválida.");
				}
				if (errors.isEmpty()) {
					Date dataAdmissao = StringUtils.formatDate(
							((EditText) findViewById(R.id.edtDtAdmissao))
									.getText().toString(),
							StringUtils.DATE_FORMAT);
					Date dataDemissao = StringUtils.formatDate(
							((EditText) findViewById(R.id.edtDtDemissao))
									.getText().toString(),
							StringUtils.DATE_FORMAT);
					if (dataDemissao.before(dataAdmissao)) {
						errors
								.add("A data de demissão deve ser posterior à data de admissão.");
					}
				}
				EditText ultimoSal = (EditText) findViewById(R.id.edtUltimoSalario);
				if (!StringUtils.isValidDecimal(ultimoSal.getText().toString())) {
					errors.add("O valor de último salário está inválido.");
				}
				if (errors.isEmpty()) {
					calcularRescisao();
					
				} else {
					showDialog(DIALOG_ERROR);
				}
			}
		});
		Button btVoltar = (Button) findViewById(R.id.btVoltar);
		btVoltar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CalculoRescisaoActivity.this.finish();
			}
		});

		setAdView();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ERROR:
			String messages = "";
			for (int i = 0; i < errors.size(); i++) {
				messages += errors.get(i);
				if (i < errors.size() - 1) {
					messages += "\n";
				}
			}
			return new AlertDialog.Builder(CalculoRescisaoActivity.this)
					.setIcon(R.drawable.icon_error).setTitle("Erro")
					.setMessage(messages).setPositiveButton(
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

	/**
	 * 
	 */
	private void calcularRescisao() {
		Date dataAdmissao = StringUtils.formatDate(
				((EditText) findViewById(R.id.edtDtAdmissao)).getText()
						.toString(), StringUtils.DATE_FORMAT);
		Date dataDemissao = StringUtils.formatDate(
				((EditText) findViewById(R.id.edtDtDemissao)).getText()
						.toString(), StringUtils.DATE_FORMAT);
		/*int motivoRescisao = ((Spinner) findViewById(R.id.spMotivoRescisao))
				.getSelectedItemPosition();
		int tipoAvisoPrevio = ((Spinner) findViewById(R.id.spTipoAvisoPrevio))
				.getSelectedItemPosition();*/
		
		double ultimoSalario = Double.parseDouble(((EditText) findViewById(R.id.edtUltimoSalario))
						.getText().toString());

		Calendar cDataAdmissao = Calendar.getInstance();
		cDataAdmissao.setTime(dataAdmissao);
		Calendar cDataDemissao = Calendar.getInstance();
		cDataDemissao.setTime(dataDemissao);		
		
		
		//14/08/2011
		//11/10/2011
		
		// Calculo do Saldo de salário
		int qtdDiasTrabalhados = 0;
		if (dataAdmissao.getYear() == dataDemissao.getYear()
				&& dataAdmissao.getMonth() == dataDemissao.getMonth()) {
			qtdDiasTrabalhados = (cDataDemissao.get(Calendar.DAY_OF_MONTH) - cDataAdmissao.get(Calendar.DAY_OF_MONTH)) + 1;
			if (qtdDiasTrabalhados == 31)
				qtdDiasTrabalhados--;
		} else {
			qtdDiasTrabalhados = cDataDemissao.get(Calendar.DAY_OF_MONTH);
		}
		
		double saldoSalario = ultimoSalario / 30 * qtdDiasTrabalhados;
		
		int mesDataAdmissao = cDataAdmissao.get(Calendar.MONTH) + 1;
		int mesDataDemissao = cDataDemissao.get(Calendar.MONTH) + 1;

		//Décimo Terceiro Proporcional
		//Salário/12*Meses
		int qtdMesesDecimo = 0;
		if (dataAdmissao.getYear() == dataDemissao.getYear()) {
			qtdMesesDecimo = mesDataDemissao - mesDataAdmissao;
			if (qtdMesesDecimo == 0)
				qtdMesesDecimo++;
		} else {
			qtdMesesDecimo = mesDataDemissao;
		}
		double decimoProporcional = ultimoSalario / 12 * qtdMesesDecimo;
		
		//Férias Proporcionais
		//Salário/12*Meses
		int qtdMesesFerias = 0;
		if (dataAdmissao.getYear() == dataDemissao.getYear()) {
			qtdMesesFerias = mesDataDemissao - mesDataAdmissao;
			if (qtdMesesFerias == 0)
				qtdMesesFerias++;
		} else {
			qtdMesesFerias = ((12 - mesDataAdmissao) + 1) + mesDataDemissao;
		}
		double feriasProporcional = ultimoSalario / 12 * qtdMesesFerias;
		//1/3 de Férias Proporcionais
		//feriasProporcional : 3
		double umTercoFeriasProporcional = feriasProporcional / 3;
		
		Intent intent = new Intent();
		intent.setClass(this, ResultadoCalculoRescisaoActivity.class);
		intent.putExtra("saldoSalario", saldoSalario);
		intent.putExtra("decimoProporcional", decimoProporcional);
		intent.putExtra("feriasProporcional", feriasProporcional);
		intent.putExtra("umTercoFeriasProporcional", umTercoFeriasProporcional);
		
		intent.putExtra("inssSalario", saldoSalario * INSS);
		intent.putExtra("inssDecimo", decimoProporcional * INSS);
		
		startActivity(intent);
		

	}
}