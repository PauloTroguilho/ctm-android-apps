/**
 * 
 */
package com.ctm.eadvogado;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import javax.inject.Named;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.model.SubscriptionPurchase;

/**
 * @author ctm
 * 
 */
@Named
public class MeuBean {

	// ENTER YOUR PROJECT ID HERE
	private static final String PROJECT_NUMBER = "XXXXXXXXXXXXXXX";

	private static final HttpTransport TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final String SCOPE = "https://www.googleapis.com/auth/androidpublisher";

	/**
	 * 
	 */
	public MeuBean() {
		// TODO Auto-generated constructor stub
	}

	public String getMensagem() {
		GoogleCredential credential = null;
		try {
			//getClass().getResource("0833900ecf20f992e1343ca5f21586f652b0a623-privatekey.p12").toURI().
			credential = new GoogleCredential.Builder()
					.setTransport(TRANSPORT)
					.setJsonFactory(JSON_FACTORY)
					.setServiceAccountId(
							"935757146253-l75msnpp3442h697scceh0p4upm9382g@developer.gserviceaccount.com")
					.setServiceAccountScopes(Arrays.asList(SCOPE))
					.setServiceAccountPrivateKeyFromP12File(
							new File(
									"F:/DesenvolvimentoAndroid/workspaceNovo/e-advogado-web/src/0833900ecf20f992e1343ca5f21586f652b0a623-privatekey.p12"))
					.build();
		} catch (GeneralSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		AndroidPublisher androidPublish = new AndroidPublisher.Builder(
				TRANSPORT, JSON_FACTORY, credential).setApplicationName(
				"e-advogado-web").build();
		try {
			SubscriptionPurchase execute = androidPublish.purchases()
					.get("com.ctm.eadvogado", "conta_premium", "asdasdas")
					.execute();
			System.out.println(execute);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Olá Mundo!";
	}

}
