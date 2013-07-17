package com.ctm.eadvogado.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.ctm.eadvogado.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class PreferencesFragment extends PreferenceFragment {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences_conta);
        
    }
    
}