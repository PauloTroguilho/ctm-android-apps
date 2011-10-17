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
		
		TextView tvINSSsalario = (TextView)findViewById(R.id.tvINSSsalario);
		TextView tvINSSdecimo = (TextView)findViewById(R.id.tvINSSdecimo);
		TextView tvTotalDescontos = (TextView)findViewById(R.id.tvTotalDescontos);
		
		TextView tvTotalRescisao = (TextView)findViewById(R.id.tvTotalRescisao);
		
		double saldoSalario = getIntent().getDoubleExtra("saldoSalario", 0);
		double decimoProporcional = getIntent().getDoubleExtra("decimoProporcional", 0);
		double feriasProporcional = getIntent().getDoubleExtra("feriasProporcional", 0);
		double umTercoFeriasProporcional = getIntent().getDoubleExtra("umTercoFeriasProporcional", 0);
		
		double totalProventos = saldoSalario + decimoProporcional + feriasProporcional + umTercoFeriasProporcional;
		
		double inssSalario = getIntent().getDoubleExtra("inssSalario", 0);
		double inssDecimo = getIntent().getDoubleExtra("inssDecimo", 0);
		
		double totalDescontos = inssSalario + inssDecimo;
		
		double totalRescisao = totalProventos - totalDescontos;
		
		tvSaldoSalario.setText(StringUtils.formatNumber(saldoSalario));
		tvDecimoProp.setText(StringUtils.formatNumber(decimoProporcional));
		tvFeriasProp.setText(StringUtils.formatNumber(feriasProporcional));
		tvUmTercoFerias.setText(StringUtils.formatNumber(umTercoFeriasProporcional));
		tvTotalProventos.setText(StringUtils.formatNumber(totalProventos));
		
		tvINSSsalario.setText(StringUtils.formatNumber(inssSalario));
		tvINSSdecimo.setText(StringUtils.formatNumber(inssDecimo));
		tvTotalDescontos.setText(StringUtils.formatNumber(totalDescontos));
		
		tvTotalRescisao.setText(StringUtils.formatNumber(totalRescisao));
		
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