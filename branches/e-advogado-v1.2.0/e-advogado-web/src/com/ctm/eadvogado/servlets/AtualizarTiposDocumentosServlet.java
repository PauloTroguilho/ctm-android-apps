/**
 * 
 */
package com.ctm.eadvogado.servlets;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ctm.eadvogado.model.Tribunal;
import com.ctm.eadvogado.negocio.TipoDocumentoNegocio;
import com.ctm.eadvogado.negocio.TribunalNegocio;
import com.ctm.eadvogado.util.WeldUtils;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * @author Cleber
 * 
 */
public class AtualizarTiposDocumentosServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int INTERVALO_ATUALIZACAO = 60000 * 60 * 72;

	@Inject
	private TribunalNegocio tribunalNegocio;
	@Inject
	private TipoDocumentoNegocio tipoDocumentoNegocio;
	

	/**
	 * 
	 */
	public AtualizarTiposDocumentosServlet() {
		tribunalNegocio = WeldUtils.getBean(TribunalNegocio.class);
		tipoDocumentoNegocio = WeldUtils.getBean(TipoDocumentoNegocio.class);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log("Atualização de tipos de documentos iniciada.");
		List<Tribunal> tribunais = tribunalNegocio.findAll(null, null);
		for (Tribunal tribunal : tribunais) {
			if (tribunal.getDataAtualizacao() == null
					|| (System.currentTimeMillis() - tribunal
							.getDataAtualizacao().getTime()) >= INTERVALO_ATUALIZACAO) {
				Queue queue = QueueFactory.getDefaultQueue();
				queue.add(TaskOptions.Builder.withUrl("/consultarTiposDocumentos")
						.method(Method.POST)
						.param("idTribunal", tribunal.getKey().getId() + ""));
				log(String.format(
						"Queue de consulta tipoDocmento criada para o tribunal %s ",
						tribunal.getSigla()));
			}
		}
	}

}
