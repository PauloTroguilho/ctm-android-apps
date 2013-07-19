package com.ctm.eadvogado.androidpublisher;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AndroidPublisherService extends
		AbstractAppEngineAuthorizationCodeServlet {

	static final String APP_NAME = "Android Publisher API Web Client";

	static final String GWT_MODULE_NAME = "androidpublisher";

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.println("<!doctype html><html><head>");
		writer.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
		writer.println("<title>" + APP_NAME + "</title>");
		writer.println("<link type=\"text/css\" rel=\"stylesheet\" href=\""
				+ GWT_MODULE_NAME + ".css\">");
		writer.println("<script type=\"text/javascript\" language=\"javascript\" "
				+ "src=\""
				+ GWT_MODULE_NAME
				+ "/"
				+ GWT_MODULE_NAME
				+ ".nocache.js\"></script>");
		writer.println("</head><body>");
		UserService userService = UserServiceFactory.getUserService();
		writer.println("<div class=\"header\"><b>"
				+ request.getUserPrincipal().getName()
				+ "</b> | "
				+ "<a href=\""
				+ userService.createLogoutURL(request.getRequestURL()
						.toString())
				+ "\">Log out</a> | "
				+ "<a href=\"http://code.google.com/p/google-api-java-client/source/browse"
				+ "/calendar-appengine-sample?repo=samples\">See source code for "
				+ "this sample</a></div>");
		writer.println("<div id=\"main\"/>");
		writer.println("</body></html>");
	}

	@Override
	protected String getRedirectUri(HttpServletRequest req)
			throws ServletException, IOException {
		return Utils.getRedirectUri(req);
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws IOException {
		return Utils.newFlow();
	}

}