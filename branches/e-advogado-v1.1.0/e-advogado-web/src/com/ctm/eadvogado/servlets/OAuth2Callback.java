package com.ctm.eadvogado.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ctm.eadvogado.androidpublisher.Utils;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeCallbackServlet;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * HTTP servlet to process access granted from user.
 * 
 * @author Yaniv Inbar
 */
public class OAuth2Callback extends
		AbstractAppEngineAuthorizationCodeCallbackServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void onSuccess(HttpServletRequest req, HttpServletResponse resp,
			Credential credential) throws ServletException, IOException {
		resp.sendRedirect("/");
	}

	@Override
	protected void onError(HttpServletRequest req, HttpServletResponse resp,
			AuthorizationCodeResponseUrl errorResponse)
			throws ServletException, IOException {
		String nickname = UserServiceFactory.getUserService().getCurrentUser()
				.getNickname();
		resp.getWriter().print(
				"<h3>" + nickname
						+ ", why don't you want to play with me?</h1>");
		resp.setStatus(200);
		resp.addHeader("Content-Type", "text/html");
	}

	@Override
	protected String getRedirectUri(HttpServletRequest req)
			throws ServletException, IOException {
		UserServiceFactory.getUserService().getCurrentUser();
		return Utils.getRedirectUri(req);
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws IOException {
		return Utils.newFlow();
	}
}