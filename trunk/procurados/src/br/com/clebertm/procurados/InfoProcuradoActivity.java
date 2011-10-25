package br.com.clebertm.procurados;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.clebertm.domain.Procurado;

public class InfoProcuradoActivity extends Activity {
	
	private Procurado procurado;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_procurado);
		
		procurado = (Procurado)getIntent().getSerializableExtra("procurado");
		
		TextView tvNome = (TextView)findViewById(R.id.tvNome);
		TextView tvApelido = (TextView)findViewById(R.id.tvApelido);
		ImageView ivFoto = (ImageView)findViewById(R.id.ivFoto);
		
		tvNome.setText(procurado.getNome());
		tvApelido.setText(procurado.getApelidoTratado());
		
		int resId = getResources().getIdentifier("proc_" + procurado.getFotoId(), "drawable", "br.com.clebertm.procurados");
		ivFoto.setImageResource(resId);
		
		Button btDetalhes = (Button)findViewById(R.id.btDetalhes);
		btDetalhes.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(InfoProcuradoActivity.this, DetalhesActivity.class);
				intent.putExtra("procurado", procurado);
				
				startActivity(intent);
			}
		});
	}
}