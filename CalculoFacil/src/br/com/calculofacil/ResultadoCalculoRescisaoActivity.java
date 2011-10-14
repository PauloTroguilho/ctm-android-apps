package br.com.calculofacil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import br.com.calculofacil.util.StringUtils;

public class ResultadoCalculoRescisaoActivity extends AdMobActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rescisao_clt_resultado);
		
		TextView tvSaldoSalario = (TextView)findViewById(R.id.tvSaldoSalario);
		TextView tvDecimoProp = (TextView)findViewById(R.id.tvDecimoProp);
		TextView tvFeriasProp = (TextView)findViewById(R.id.tvFeriasProp);
		TextView tvUmTercoFerias = (TextView)findViewById(R.id.tvUmTercoFerias);
		TextView tvTotalProventos = (TextView)findViewById(R.id.tvTotalProventos);
		
		double saldoSalario = getIntent().getDoubleExtra("saldoSalario", 0);
		double decimoProporcional = getIntent().getDoubleExtra("decimoProporcional", 0);
		double feriasProporcional = getIntent().getDoubleExtra("feriasProporcional", 0);
		double umTercoFeriasProporcional = getIntent().getDoubleExtra("umTercoFeriasProporcional", 0);
		
		double totalProventos = saldoSalario + decimoProporcional + feriasProporcional + umTercoFeriasProporcional;
		
		tvSaldoSalario.setText(StringUtils.formatNumber(saldoSalario));
		tvDecimoProp.setText(StringUtils.formatNumber(decimoProporcional));
		tvFeriasProp.setText(StringUtils.formatNumber(feriasProporcional));
		tvUmTercoFerias.setText(StringUtils.formatNumber(umTercoFeriasProporcional));
		tvTotalProventos.setText(StringUtils.formatNumber(totalProventos));
		
		Button btVoltar = (Button) findViewById(R.id.btVoltar);
		btVoltar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ResultadoCalculoRescisaoActivity.this.finish();
			}
		});

		setAdView();
	}

}