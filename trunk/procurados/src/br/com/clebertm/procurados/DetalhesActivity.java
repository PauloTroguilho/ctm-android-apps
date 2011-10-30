package br.com.clebertm.procurados;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.clebertm.domain.Procurado;
import br.com.clebertm.procurados.util.Consts;
import br.com.clebertm.procurados.util.FileCacheUtils;
import br.com.clebertm.procurados.util.IOUtils;

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
		
		File fotosDir = FileCacheUtils.getFotosCacheDir(this);
		File fotoFile = new File(fotosDir, p.getFotoId() + Consts.FOTO_EXTENSAO);
		FileInputStream in = null;
		try {
			in = new FileInputStream(fotoFile);
		} catch (FileNotFoundException e) {
		}
		if (in != null) {
			Bitmap bitmap = BitmapFactory.decodeStream(in);
			bitmap = Bitmap.createScaledBitmap(bitmap, 100, 135, true);
			IOUtils.closeQuietly(in);
			ivFoto.setImageBitmap(bitmap);
		}

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