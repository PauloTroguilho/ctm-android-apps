package br.com.calculofacil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import br.com.calculofacil.util.StringUtils;

public class ResultadoCalculoBoletoActivity extends AdMobActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boleto_resultado);
		
		TextView tvValorBoleto = (TextView)findViewById(R.id.tvValorBoleto);
		TextView tvMulta = (TextView)findViewById(R.id.tvMulta);
		TextView tvJuros = (TextView)findViewById(R.id.tvJuros);
		TextView tvTotalBoleto = (TextView)findViewById(R.id.tvTotalBoleto);
		
		double valorBoleto = getIntent().getDoubleExtra("valorBoleto", 0); 
		double valorTotalMulta = getIntent().getDoubleExtra("valorTotalMulta", 0);
		double valorTotalJuros = getIntent().getDoubleExtra("valorTotalJuros", 0);
		
		tvValorBoleto.setText(StringUtils.formatNumber(valorBoleto));
		tvMulta.setText(StringUtils.formatNumber(valorTotalMulta));
		tvJuros.setText(StringUtils.formatNumber(valorTotalJuros));
		tvTotalBoleto.setText(StringUtils.formatNumber(valorBoleto + valorTotalMulta + valorTotalJuros));
		
		Button btVoltar = (Button) findViewById(R.id.btVoltar);
		btVoltar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ResultadoCalculoBoletoActivity.this.finish();
			}
		});

		setAdView();
	}

}