/**
 * 
 */
package br.com.clebertm.procurados;

import android.app.Activity;
import android.os.Bundle;
import br.com.clebertm.procurados.util.Consts;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 * 
 */
public class AdMobActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * 
	 */
	protected void setAdView() {
		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest request = new AdRequest();
		request.addTestDevice(AdRequest.TEST_EMULATOR);
		request.addTestDevice(Consts.MY_DEVICEID);
		adView.loadAd(request);
	}

}
