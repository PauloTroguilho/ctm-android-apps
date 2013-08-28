package com.ctm.eadvogado.tasks;


public interface TaskListener {
	
	public void onCompleted();

	public void onError();

	public void onCanceled();
}