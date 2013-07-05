package com.ctm.eadvogado.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class StockPreferenceFragment extends PreferenceFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int res = getActivity().getResources().getIdentifier(
				getArguments().getString("resource"), "xml",
				getActivity().getPackageName());

		addPreferencesFromResource(res);
	}
	
}