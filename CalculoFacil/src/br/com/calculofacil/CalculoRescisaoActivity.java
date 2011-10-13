package br.com.calculofacil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import br.com.calculofacil.util.Consts;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class CalculoRescisaoActivity extends Activity {
	
	private SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
	private Calendar calendar = Calendar.getInstance();
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rescisao_clt);
		
		EditText dtAdmissao = (EditText)findViewById(R.id.edtDtAdmissao);
		EditText dtDemissao = (EditText)findViewById(R.id.edtDtDemissao);
		
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		dtAdmissao.setText(sdf.format(calendar.getTime()));
		
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DAY_OF_MONTH));
		dtDemissao.setText(sdf.format(calendar.getTime()));

		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest request = new AdRequest();
		request.addTestDevice(AdRequest.TEST_EMULATOR);
		request.addTestDevice(Consts.MY_DEVICEID);
		adView.loadAd(request);
	}

}