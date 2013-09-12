/**
 * 
 */
package com.ctm.eadvogado.servlets;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Cleber
 * 
 */
public class SendMailServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SendMailServlet() {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String from = req.getParameter("from");
		String to = req.getParameter("to");
		String subject = req.getParameter("subject");
		String textContent = req.getParameter("textContent");
		String htmlContent = req.getParameter("htmlContent");
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Message msg = new MimeMessage(session);
			InternetAddress addressFrom = new InternetAddress(from);
			msg.setFrom(addressFrom);
			msg.setReplyTo(new Address[] { addressFrom });
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(subject);
			// Create the Multipart. Add BodyParts to it.
			Multipart mp = new MimeMultipart();
			if (textContent != null) {
				// Unformatted text version
				MimeBodyPart textPart = new MimeBodyPart();
				textPart.setText(textContent);
				mp.addBodyPart(textPart);
			}
			if (htmlContent != null) {
				// HTML version
				MimeBodyPart htmlPart = new MimeBodyPart();
				htmlPart.setContent(htmlContent, "text/html");
				mp.addBodyPart(htmlPart);
			}
			// Set Multipart as the message's content
			msg.setContent(mp);
			Transport.send(msg);
		} catch (Exception e) {
			log(String.format("Falha ao enviar email para %s", to), e);
		}

	}

}
