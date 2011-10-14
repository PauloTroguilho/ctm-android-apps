package br.com.calculofacil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.calculofacil.util.Consts;
import br.com.calculofacil.util.StringUtils;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class CalculoRescisaoActivity extends Activity {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private Calendar calendar = Calendar.getInstance();
	
	private List<String> errors = new ArrayList<String>();
	
	private static final int DIALOG_ERROR = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rescisao_clt);

		final EditText dtAdmissao = (EditText) findViewById(R.id.edtDtAdmissao);
		final EditText dtDemissao = (EditText) findViewById(R.id.edtDtDemissao);

		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		dtAdmissao.setText(StringUtils.formatDate(calendar.getTime(),
				StringUtils.DATE_FORMAT));

		calendar.set(Calendar.DAY_OF_MONTH, calendar
				.getMaximum(Calendar.DAY_OF_MONTH));
		dtDemissao.setText(StringUtils.formatDate(calendar.getTime(),
				StringUtils.DATE_FORMAT));

		Button btCalcular = (Button) findViewById(R.id.btCalcular);
		btCalcular.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				errors.clear();
				
				if (!StringUtils.isValidDate(dtAdmissao.getText().toString(),
						StringUtils.DATE_FORMAT)) {
					errors.add("A data de admissão está inválida.");
				}
				if (!StringUtils.isValidDate(dtDemissao.getText().toString(),
						StringUtils.DATE_FORMAT)) {
					errors.add("A data de demissão está inválida.");
				}
				EditText ultimoSal = (EditText) findViewById(R.id.edtUltimoSalario);
				if (!StringUtils.isValidDecimal(ultimoSal.getText().toString())) {
					errors.add("O valor de último salário está inválido.");
				}
				if (errors.isEmpty()) {
					
				} else {
					showDialog(DIALOG_ERROR);
				}
			}
		});

		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest request = new AdRequest();
		request.addTestDevice(AdRequest.TEST_EMULATOR);
		request.addTestDevice(Consts.MY_DEVICEID);
		adView.loadAd(request);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DIALOG_ERROR:
				String messages = "";
				for (int i = 0; i < errors.size(); i++) {
					messages += errors.get(i);
					if (i < errors.size() -1) {
						messages += "\n";
					}
				}
				return new AlertDialog.Builder(CalculoRescisaoActivity.this)
                .setIcon(R.drawable.icon_error)
                .setTitle("Erro")
                .setMessage(messages)
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked OK so do some stuff */
                    }
                })
                .create();
		}
		// TODO Auto-generated method stub
		return null;
	}

}