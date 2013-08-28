package com.ctm.eadvogado.tasks;

import android.app.Activity;
import android.os.AsyncTask;

public abstract class BaseTask<Params,Progess,Result> extends AsyncTask<Params,Progess,Result> {

	protected static final String TAG = "e-Advogado";
	
	protected Activity parentActivity;
	protected TaskListener listener;
	
	protected Result result;
	
	/**
	 * @param parentActivity
	 * @param listener
	 */
	public BaseTask(Activity parentActivity, TaskListener listener) {
		this.parentActivity = parentActivity;
		this.listener = listener;
	}

	@Override
	protected void onPostExecute(Result result) {
		this.result = result;
		listener.onCanceled();
	}
	
	@Override
	protected void onCancelled(Result result) {
		this.result = result;
		listener.onCanceled();
	}


}