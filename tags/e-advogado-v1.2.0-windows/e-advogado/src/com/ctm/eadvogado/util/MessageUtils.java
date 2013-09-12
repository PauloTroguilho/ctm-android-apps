package com.ctm.eadvogado.util;

import android.app.AlertDialog;
import android.content.Context;

public class MessageUtils {

	/**
	 * @param message
	 * @param context
	 */
	public static void alert(String message, Context context) {
        AlertDialog.Builder bld = new AlertDialog.Builder(context);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        bld.create().show();
    }

}
