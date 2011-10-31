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
import android.widget.RadioButton;
import android.widget.Spinner;
import br.com.calculofacil.util.StringUtils;

public class CalculoBoletoActivity extends AdMobActivity {

	private Calendar calendar = Calendar.getInstance();

	private List<String> errors = new ArrayList<String>();

	private static final int DIALOG_ERROR = 1;
	
	private static final float INSS = 0.08f; 

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculo_boleto);

		final EditText dtVenc = (EditText) findViewById(R.id.edtDtVenc);
		final EditText dtPagto = (EditText) findViewById(R.id.edtDtPagto);

		calendar.setTime(new Date());
		
		dtVenc.setText(StringUtils.formatDate(calendar.getTime(),
				StringUtils.DATE_FORMAT));
		dtPagto.setText(StringUtils.formatDate(calendar.getTime(),
				StringUtils.DATE_FORMAT));
		

		Button btCalcular = (Button) findViewById(R.id.btCalcular);
		btCalcular.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				errors.clear();
				if (!StringUtils.isValidDate(dtVenc.getText().toString(),
						StringUtils.DATE_FORMAT)) {
					errors.add("A data de admissão está inválida.");
				}
				if (!StringUtils.isValidDate(dtPagto.getText().toString(),
						StringUtils.DATE_FORMAT)) {
					errors.add("A data de demissão está inválida.");
				}
				if (errors.isEmpty()) {
					Date dataVenc = StringUtils.formatDate(
							((EditText) findViewById(R.id.edtDtVenc))
									.getText().toString(), StringUtils.DATE_FORMAT);
					Date dataPagto = StringUtils.formatDate(
							((EditText) findViewById(R.id.edtDtPagto))
									.getText().toString(), StringUtils.DATE_FORMAT);
					if (dataPagto.before(dataVenc)) {
						errors.add("A data de pagamento deve ser posterior à data de vencimento.");
					}
				}
				
				EditText valorBoleto = (EditText) findViewById(R.id.edtValorBoleto);
				if (!StringUtils.isValidDecimal(valorBoleto.getText().toString())) {
					errors.add("O valor do boleto está inválido.");
				}
				EditText valorMulta = (EditText) findViewById(R.id.edtValorMulta);
				if (!StringUtils.isValidDecimal(valorMulta.getText().toString())) {
					errors.add("O valor da multa está inválido.");
				}
				EditText valorJuros = (EditText) findViewById(R.id.edtValorJuros);
				if (!StringUtils.isValidDecimal(valorJuros.getText().toString())) {
					errors.add("O valor dos juros está inválido.");
				}
				if (errors.isEmpty()) {
					calcularBoleto();
					
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
				CalculoBoletoActivity.this.finish();
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
			return new AlertDialog.Builder(CalculoBoletoActivity.this)
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
	private void calcularBoleto() {
		Date dataVenc = StringUtils.formatDate(
				((EditText) findViewById(R.id.edtDtVenc)).getText()
						.toString(), StringUtils.DATE_FORMAT);
		Date dataPagto = StringUtils.formatDate(
				((EditText) findViewById(R.id.edtDtPagto)).getText()
						.toString(), StringUtils.DATE_FORMAT);
		
		double valorBoleto = Double.parseDouble(((EditText) findViewById(R.id.edtValorBoleto))
						.getText().toString());
		double valorMulta = Double.parseDouble(((EditText) findViewById(R.id.edtValorMulta))
				.getText().toString());
		double valorJuros = Double.parseDouble(((EditText) findViewById(R.id.edtValorJuros))
				.getText().toString());

		double valorTotalMulta = 0.0;
		double valorTotalJuros = 0.0;
		
		Spinner spTipoMulta = (Spinner) findViewById(R.id.spTipoMulta);
		boolean isMultaValorFixo = (spTipoMulta.getSelectedItemPosition() == 1);
		
		if (valorMulta > 0) {
			if (isMultaValorFixo) {
				valorTotalMulta = valorMulta;
			} else {
				valorTotalMulta = valorBoleto * valorMulta / 100;
			}
		}
		
		Spinner spTiposJuros = (Spinner) findViewById(R.id.spTiposJuros);
		boolean isJurosCompostos = (spTipoMulta.getSelectedItemPosition() == 0);
		
		Spinner spIncJuros = (Spinner) findViewById(R.id.spIncidenciaJuros);
		boolean isIncAoDia = (spTipoMulta.getSelectedItemPosition() == 0);
		
		if (isJurosCompostos) {
			
			long intervalo = 0;
			
			if (isIncAoDia) {
		
				long dt = (dataPagto.getTime() - dataVenc.getTime()) + 3600000;        
                intervalo = (dt / 86400000L);
				
			} else {
				
			}
			
		}
		
		
		Intent intent = new Intent();
		intent.setClass(this, ResultadoCalculoRescisaoActivity.class);
		//intent.putExtra("saldoSalario", saldoSalario);
		//intent.putExtra("decimoProporcional", decimoProporcional);
	//	intent.putExtra("feriasProporcional", feriasProporcional);
		//intent.putExtra("umTercoFeriasProporcional", umTercoFeriasProporcional);
		
		//intent.putExtra("inssSalario", saldoSalario * INSS);
		//intent.putExtra("inssDecimo", decimoProporcional * INSS);
		
		startActivity(intent);
		

	}
}