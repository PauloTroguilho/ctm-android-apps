package br.com.clebertm.procurados.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import br.com.clebertm.procurados.util.Consts;
import br.com.clebertm.procurados.util.IOUtils;

public enum BitmapManager {

	INSTANCE;

	private final Map<String, SoftReference<Bitmap>> cache;
	private final ExecutorService pool;
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	private Bitmap placeholder;
	private Context context;

	BitmapManager() {
		cache = new HashMap<String, SoftReference<Bitmap>>();
		pool = Executors.newFixedThreadPool(5);
	}

	public void setPlaceholder(Bitmap bmp) {
		placeholder = bmp;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Bitmap getBitmapFromCache(String fotoId) {
		if (cache.containsKey(fotoId)) {
			return cache.get(fotoId).get();
		}

		return null;
	}
	
	/**
	 * @param fotoId
	 * @return
	 */
	public String getFotoUrl(String fotoId) {
		return Consts.SERVER_URL_TO_FOTOSDIR + "proc_" + fotoId + ".jpeg";
	}

	public void queueJob(final String fotoId, final ImageView imageView,
			final int width, final int height) {
		/* Create handler in UI thread. */
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				String tag = imageViews.get(imageView);
				if (tag != null && tag.equals(fotoId)) {
					if (msg.obj != null) {
						imageView.setImageBitmap((Bitmap) msg.obj);
					} else {
						imageView.setImageBitmap(placeholder);
						Log.d(null, "fail " + fotoId);
					}
				}
			}
		};

		pool.submit(new Runnable() {
			@Override
			public void run() {
				final Bitmap bmp = downloadBitmap(fotoId, width, height);
				Message message = Message.obtain();
				message.obj = bmp;
				Log.d(null, "Item downloaded: " + fotoId);

				handler.sendMessage(message);
			}
		});
	}

	public void loadBitmap(final String fotoId, final ImageView imageView,
			final int width, final int height) {
		imageViews.put(imageView, fotoId);
		Bitmap bitmap = getBitmapFromCache(fotoId);

		// check in UI thread, so no concurrency issues
		if (bitmap != null) {
			Log.d(null, "Item loaded from cache: " + fotoId);
			imageView.setImageBitmap(bitmap);
		} else {
			imageView.setImageBitmap(placeholder);
			queueJob(fotoId, imageView, width, height);
		}
	}
	
	/**
	 * @return
	 */
	private File getCacheFotosDir() {
		return context.getDir(Consts.CACHE_FOTOS_DIR, Context.MODE_PRIVATE);
	}
	
	/**
	 * @param fotoId
	 * @param force
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private File downloadFoto(String fotoId, boolean force) throws MalformedURLException, IOException {
		File fotosDir = getCacheFotosDir();
		File fotoDownload = new File(fotosDir, fotoId + ".jpeg");
		boolean exists = fotoDownload.exists(); 
		if (!exists || force) {
			if (!exists) {
				fotoDownload.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(fotoDownload);
			InputStream in = new URL(getFotoUrl(fotoId)).openStream();
			IOUtils.copyStream(in, out);
			Log.i(getClass().getSimpleName(), "Download da foto "+fotoId+" realizado com sucesso.");
		} else {
			Log.i(getClass().getSimpleName(), "Foto "+fotoId+" ja existente na cache.");
		}
		return fotoDownload;
	}

	private Bitmap downloadBitmap(String fotoId, int width, int height) {
		try {
			File fotoDownloaded = downloadFoto(fotoId, false);
			FileInputStream in = new FileInputStream(fotoDownloaded);
			Bitmap bitmap = BitmapFactory.decodeStream(in);
			IOUtils.closeQuietly(in);
			if (bitmap == null) {
				downloadFoto(fotoId, true);
				in = new FileInputStream(fotoDownloaded);
				bitmap = BitmapFactory.decodeStream(in);
				IOUtils.closeQuietly(in);
			}
			bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
			cache.put(fotoId, new SoftReference<Bitmap>(bitmap));
			return bitmap;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}