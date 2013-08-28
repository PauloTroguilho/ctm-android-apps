/**
 * 
 */
package com.ctm.eadvogado.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ctm.eadvogado.model.TipoJuizo;
import com.ctm.eadvogado.negocio.ProcessoNegocio;
import com.ctm.eadvogado.util.WeldUtils;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.ServiceUnavailableException;
import com.google.api.server.spi.response.UnauthorizedException;

/**
 * @author Cleber
 * 
 */
public class ConsultarProcessoAsyncServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger
			.getLogger(ConsultarProcessoAsyncServlet.class.getSimpleName());

	@Inject
	private ProcessoNegocio processoNegocio;

	/**
	 * 
	 */
	public ConsultarProcessoAsyncServlet() {
		processoNegocio = WeldUtils.getBean(ProcessoNegocio.class);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pNpu = req.getParameter("npu");
		String pIdTribunal = req.getParameter("idTribunal");
		String pTipoJuizo = req.getParameter("tipoJuizo");
		try {
			processoNegocio.consultarProcesso(pNpu, 
				Long.parseLong(pIdTribunal), TipoJuizo.valueOf(pTipoJuizo), true, false);
			LOGGER.fine(String.format("Processo: %s, %s, %s foi atualizado com sucesso!", 
					pNpu, pIdTribunal, pTipoJuizo));
		} catch (NumberFormatException e) {
			LOGGER.log(Level.SEVERE, "Falha na conversao de parametros", e);
		} catch (NotFoundException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} catch (UnauthorizedException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} catch (ServiceUnavailableException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
