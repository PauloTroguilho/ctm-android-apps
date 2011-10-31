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
import android.widget.Spinner;
import br.com.calculofacil.util.StringUtils;

public class CalculoBoletoActivity extends AdMobActivity {

	private Calendar calendar = Calendar.getInstance();

	private List<String> errors = new ArrayList<String>();

	private static final int DIALOG_ERROR = 1;
	
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
	 * @param g1
	 * @param g2
	 * @return
	 */
	private int getMeses(Calendar g1, Calendar g2) {  
        int meses = 0;  
        boolean dataIniMaior = false;  
        Calendar gc1, gc2;  
          
        if (g2.after(g1)){  
            gc2 = (Calendar) g2.clone();  
            gc1 = (Calendar) g1.clone();  
        }  
        else{  
            dataIniMaior = true;  
            gc2 = (Calendar) g1.clone();  
            gc1 = (Calendar) g2.clone();  
        }  
          
        gc1.clear(Calendar.MILLISECOND);  
        gc1.clear(Calendar.SECOND);  
        gc1.clear(Calendar.MINUTE);  
        gc1.clear(Calendar.HOUR_OF_DAY);  
        gc1.clear(Calendar.DATE);  
  
        gc2.clear(Calendar.MILLISECOND);  
        gc2.clear(Calendar.SECOND);  
        gc2.clear(Calendar.MINUTE);  
        gc2.clear(Calendar.HOUR_OF_DAY);  
        gc2.clear(Calendar.DATE);  
          
        while(gc1.before(gc2)){  
            gc1.add(Calendar.MONTH, 1);  
            meses = dataIniMaior? --meses: ++meses;  
        }  
          
        return (meses==0 || meses < 0)? meses: meses - 1;  
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
		boolean isJurosCompostos = (spTiposJuros.getSelectedItemPosition() == 0);
		
		Spinner spIncJuros = (Spinner) findViewById(R.id.spIncidenciaJuros);
		boolean isIncAoDia = (spIncJuros.getSelectedItemPosition() == 0);

		long intervalo = 0;

		if (isIncAoDia) {
			long dt = (dataPagto.getTime() - dataVenc.getTime()) + 3600000;        
            intervalo = (dt / (60000 * 60 * 24));
		} else {
			Calendar c1 = Calendar.getInstance();
			c1.setTime(dataVenc);
			Calendar c2 = Calendar.getInstance();
			c2.setTime(dataPagto);
			
			intervalo = getMeses(c1, c2);
		}
		
		if (isJurosCompostos) {
		    double montante = valorBoleto * Math.pow((1 + (valorJuros / 100)), intervalo);  
		    valorTotalJuros = montante - valorBoleto;			
		} else {
		    valorTotalJuros = valorBoleto * ((valorJuros /100) * intervalo);
		}
		
		
		Intent intent = new Intent();
		intent.setClass(this, ResultadoCalculoBoletoActivity.class);
		intent.putExtra("valorBoleto", valorBoleto);
		intent.putExtra("valorTotalMulta", valorTotalMulta);
		intent.putExtra("valorTotalJuros", valorTotalJuros);
		
		startActivity(intent);
		

	}
}