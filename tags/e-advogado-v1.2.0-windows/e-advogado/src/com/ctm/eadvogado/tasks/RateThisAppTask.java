package com.ctm.eadvogado.tasks;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;

/**
 * Task rate this app.
 */
public class RateThisAppTask extends
		AsyncTask<Void, Void, Boolean> {

	private Context context;
	
	/**
	 * @param context
	 */
	public RateThisAppTask(Context context) {
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("market://search?q=" + context.getPackageName()));
		PackageManager pm = context.getPackageManager();
		List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);
		return list != null && !list.isEmpty();
	}

}