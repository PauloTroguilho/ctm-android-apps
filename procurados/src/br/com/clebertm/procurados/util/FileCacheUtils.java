package br.com.clebertm.procurados.util;

import java.io.File;

import android.content.Context;
import android.os.Environment;

public class FileCacheUtils {

	/**
	 * @param context
	 * @return
	 */
	public static File getCacheDir(Context context) {
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but
			// all we need
			// to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		File cacheDir = null;
		if (mExternalStorageAvailable && mExternalStorageWriteable) {
			File externalStorage = Environment.getExternalStorageDirectory();
			cacheDir = new File(externalStorage, "/Android/data/"+ context.getPackageName() +"/cache");
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}
		} else {
			cacheDir = context.getCacheDir();
		}
		return cacheDir;
	}
	
	/**
	 * @param context
	 * @return
	 */
	public static File getFotosCacheDir(Context context) {
		File fotoCacheDir = new File(getCacheDir(context), Consts.CACHE_FOTOS_DIR);
		if (!fotoCacheDir.exists()) {
			fotoCacheDir.mkdirs();
		}
		return fotoCacheDir;
	}
}
