package br.com.clebertm.procurados;

import br.com.clebertm.domain.Procurado;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetalhesActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalhes);
		
		Procurado p = (Procurado)getIntent().getSerializableExtra("procurado");
		
		TextView tvNome = (TextView) findViewById(R.id.tvNome);
		TextView tvApelido = (TextView) findViewById(R.id.tvApelido);
		TextView tvComarca = (TextView) findViewById(R.id.tvComarca);
		TextView tvDtMandado = (TextView) findViewById(R.id.tvDtMandado);
		TextView tvDtNasc = (TextView) findViewById(R.id.tvDtNasc);
		TextView tvHistorico = (TextView) findViewById(R.id.tvHistorico);
		TextView tvJuizComp = (TextView) findViewById(R.id.tvJuizComp);
		TextView tvNumProc = (TextView) findViewById(R.id.tvNumProc);
		
		ImageView ivFoto = (ImageView)findViewById(R.id.ivFoto);
		
		
		int resId = getResources().getIdentifier("proc_" + p.getFotoId(), "drawable", "br.com.clebertm.procurados");
		ivFoto.setImageResource(resId);

		tvNome.setText(p.getNome());
		tvApelido.setText(p.getApelidoTratado());
		tvComarca.setText(p.getComarca());
		tvDtMandado.setText(p.getDataExpedicaoMandado());
		tvDtNasc.setText(p.getDataNascimento());
		tvHistorico.setText(p.getHistorico());
		tvJuizComp.setText(p.getJuizCompetente());
		tvNumProc.setText(p.getNumeroProcesso());
	}
}