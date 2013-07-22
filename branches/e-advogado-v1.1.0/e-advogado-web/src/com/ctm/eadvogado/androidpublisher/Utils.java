package com.ctm.eadvogado.androidpublisher;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AppEngineCredentialStore;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.Preconditions;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Utility class for JDO persistence, OAuth flow helpers, and others.
 * 
 * @author Yaniv Inbar
 */
public class Utils {

	/** Global instance of the HTTP transport. */
	static final HttpTransport HTTP_TRANSPORT = new UrlFetchTransport();

	/** Global instance of the JSON factory. */
	static final JsonFactory JSON_FACTORY = new JacksonFactory();

	private static GoogleClientSecrets clientSecrets = null;

	public static GoogleClientSecrets getClientCredential() throws IOException {
		if (clientSecrets == null) {
			clientSecrets = GoogleClientSecrets.load(
					JSON_FACTORY,
					new InputStreamReader(Utils.class
							.getResourceAsStream("/client_secrets_localhost.json")));
			Preconditions
					.checkArgument(
							!clientSecrets.getDetails().getClientId()
									.startsWith("Enter ")
									&& !clientSecrets.getDetails()
											.getClientSecret()
											.startsWith("Enter "),
							"Download client_secrets.json file from https://code.google.com/apis/console/?api=androidpublisher "
									+ "into src/client_secrets.json");
		}
		return clientSecrets;
	}

	public static String getRedirectUri(HttpServletRequest req) {
		GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath("/oauth2callback");
		return url.build();
	}

	public static GoogleAuthorizationCodeFlow newFlow() throws IOException {
		return new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT,
				JSON_FACTORY,
				getClientCredential(),
				Collections
						.singleton(AndroidPublisherScopes.AUTH_SCOPE))
				.setCredentialStore(new AppEngineCredentialStore())
				.setAccessType("offline").build();
	}

	public static AndroidPublisher loadAndroidPublisherClient() throws IOException {
		String userId = UserServiceFactory.getUserService()
                .getCurrentUser().getUserId();
		Credential credential = newFlow().loadCredential(userId);
		return new AndroidPublisher.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName("e-advogado-web").build();
	}

	/**
	 * Returns an {@link IOException} (but not a subclass) in order to work
	 * around restrictive GWT serialization policy.
	 */
	public static IOException wrappedIOException(IOException e) {
		if (e.getClass() == IOException.class) {
			return e;
		}
		return new IOException(e.getMessage());
	}

	private Utils() {
	}
}