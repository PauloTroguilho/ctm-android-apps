package br.com.ctm.guiamedico.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import android.os.Build;

public class RESTfulClient {

	private static final int CONNECTION_ATTEMPTS = 3;
	private static final int CONNECTION_TIMEOUT = 1000 * 15;
	private static final int DATARETRIEVAL_TIMEOUT = 1000 * 60;

	/**
	 * @param resourceUrl
	 * @return
	 * @throws IOException 
	 */
	public static String getContents(String resourceUrl) throws IOException {
		disableConnectionReuseIfNecessary();
		
		String contents = null;
		URL urlToRequest = new URL(resourceUrl);
		HttpURLConnection urlConnection = null;
		InputStream inStream = null;
		int tries = 0;
		while (tries < CONNECTION_ATTEMPTS) {
			try {
				urlConnection = (HttpURLConnection) urlToRequest.openConnection();
			} catch (IOException e) {
			}
			tries++;
		}
		if (urlConnection != null) {
			urlConnection.addRequestProperty("Accept", "application/json");
			urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
			urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);
			// handle issues
			int statusCode = urlConnection.getResponseCode();
			if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
				// handle unauthorized (if service requires user login)
			} else if (statusCode != HttpURLConnection.HTTP_OK) {
				// handle any other errors, like 404, 500,..
			}
			// create JSON object from content
			inStream = new BufferedInputStream(
					urlConnection.getInputStream());
			contents = getResponseText(inStream);
		} else {
			throw new IOException();
		}
		return contents;
	}

	/**
	 * required in order to prevent issues in earlier Android version.
	 */
	private static void disableConnectionReuseIfNecessary() {
		// see HttpURLConnection API doc
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
			System.setProperty("http.keepAlive", "false");
		}
	}

	/**
	 * @param inStream
	 * @return
	 */
	private static String getResponseText(InputStream inStream) {
		// very nice trick from
		// http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
		return new Scanner(inStream).useDelimiter("\\A").next();
	}

}
