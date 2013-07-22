package com.ctm.eadvogado.androidpublisher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class MainUtils {

	static String GOOGLE_CLIENT_ID = "e-advogado-web@appspot.gserviceaccount.com";
	static String GOOGLE_CLIENT_SECRET = "SIHp-NZWpqidh39aM97wTuVi";
	static String GOOGLE_REDIRECT_URI = "http://localhost:8888/oauth2callback";

	public MainUtils() {
		// TODO Auto-generated constructor stub
	}

	public static String getRefreshToken(String code) throws IOException {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(
				"https://accounts.google.com/o/oauth2/token");
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
			nameValuePairs.add(new BasicNameValuePair("grant_type",
					"authorization_code"));
			nameValuePairs.add(new BasicNameValuePair("client_id",
					GOOGLE_CLIENT_ID));
			nameValuePairs.add(new BasicNameValuePair("client_secret",
					GOOGLE_CLIENT_SECRET));
			nameValuePairs.add(new BasicNameValuePair("code", code));
			nameValuePairs.add(new BasicNameValuePair("redirect_uri",
					GOOGLE_REDIRECT_URI));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = client.execute(post);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			StringBuffer buffer = new StringBuffer();
			for (String line = reader.readLine(); line != null; line = reader
					.readLine()) {
				buffer.append(line);
			}

			JSONObject json = new JSONObject(buffer.toString());
			String refreshToken = json.getString("refresh_token");
			return refreshToken;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getAccessToken(String refreshToken)
			throws JSONException {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(
				"https://accounts.google.com/o/oauth2/token");
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
			nameValuePairs.add(new BasicNameValuePair("grant_type",
					"refresh_token"));
			nameValuePairs.add(new BasicNameValuePair("client_id",
					GOOGLE_CLIENT_ID));
			nameValuePairs.add(new BasicNameValuePair("client_secret",
					GOOGLE_CLIENT_SECRET));
			nameValuePairs.add(new BasicNameValuePair("refresh_token",
					refreshToken));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			org.apache.http.HttpResponse response = client.execute(post);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			StringBuffer buffer = new StringBuffer();
			for (String line = reader.readLine(); line != null; line = reader
					.readLine()) {
				buffer.append(line);
			}

			JSONObject json = new JSONObject(buffer.toString());
			String accessToken = json.getString("access_token");

			return accessToken;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) throws IOException, JSONException {

		String refreshToken = getRefreshToken("4/GSHGVO-NyOzveFAuwI5pcHbieomV.AnBzmPERePMQshQV0ieZDArka7P0fwI");
		String accessToken = getAccessToken(refreshToken);
		System.out.println(accessToken);
	}
}
