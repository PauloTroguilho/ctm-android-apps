package br.com.clebertm.procurados;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.clebertm.domain.Procurado;
import br.com.clebertm.procurados.util.Consts;
import br.com.clebertm.procurados.util.FileCacheUtils;
import br.com.clebertm.procurados.util.IOUtils;

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
		
		File fotosDir = FileCacheUtils.getFotosCacheDir(this);
		File fotoFile = new File(fotosDir, procurado.getFotoId() + Consts.FOTO_EXTENSAO);
		FileInputStream in = null;
		try {
			in = new FileInputStream(fotoFile);
		} catch (FileNotFoundException e) {
		}
		if (in != null) {
			Bitmap bitmap = BitmapFactory.decodeStream(in);
			IOUtils.closeQuietly(in);
			ivFoto.setImageBitmap(bitmap);
		}
		
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