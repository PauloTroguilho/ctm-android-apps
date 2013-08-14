/**
 * 
 */
package com.ctm.eadvogado.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ctm.eadvogado.model.TipoJuizo;
import com.ctm.eadvogado.negocio.ProcessoNegocio;
import com.ctm.eadvogado.util.WeldUtils;

/**
 * @author Cleber
 * 
 */
public class NotificarMovimentacaoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ProcessoNegocio processoNegocio;

	/**
	 * 
	 */
	public NotificarMovimentacaoServlet() {
		processoNegocio = WeldUtils.getBean(ProcessoNegocio.class);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pNpu = req.getParameter("npu");
		String pIdTribunal = req.getParameter("idTribunal");
		String pTipoJuizo = req.getParameter("tipoJuizo");
		try {
			processoNegocio.notificarMovimentacaoProcessual(pNpu, Long.parseLong(pIdTribunal), TipoJuizo.valueOf(pTipoJuizo));
		} catch (Exception e) {
			log(String.format("Erro ao enviar notificação do processo %s.", pNpu), e);
		}
	}

}
