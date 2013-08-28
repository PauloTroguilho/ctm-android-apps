/**
 * 
 */
package com.ctm.eadvogado.servlets;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ctm.eadvogado.model.Usuario;
import com.ctm.eadvogado.negocio.UsuarioNegocio;
import com.ctm.eadvogado.util.WeldUtils;

/**
 * @author Cleber
 * 
 */
public class EnviarNotificacaoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioNegocio usuarioNegocio;

	/**
	 * 
	 */
	public EnviarNotificacaoServlet() {
		usuarioNegocio = WeldUtils.getBean(UsuarioNegocio.class);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pEmail = req.getParameter("email");
		Usuario usuario = null;
		try {
			usuario = usuarioNegocio.findByEmail(pEmail);
		} catch(NoResultException e) {}
		if (usuario != null) {
			Map<String, String> paramsMap = new HashMap<String, String>();
			Enumeration enumeration = req.getParameterNames();
			while (enumeration.hasMoreElements()) {
				Object object = (Object) enumeration.nextElement();
				String paramName = object.toString();
				paramsMap.put(paramName, req.getParameter(paramName));
			}
			usuarioNegocio.enviarNotificacao(usuario, paramsMap);
		} else {
			log(String.format("Nenhum usuario encontrado com o e-mail: %s", pEmail));
		}
	}

}
