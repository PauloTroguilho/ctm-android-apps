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

import com.ctm.eadvogado.model.TipoDocumento;
import com.ctm.eadvogado.negocio.TipoDocumentoNegocio;
import com.ctm.eadvogado.util.WeldUtils;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * @author Cleber
 * 
 */
public class ResetTiposDocumentosServlet extends HttpServlet {

	private static final Integer MAX_BATCH_RESULTS = 100;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TipoDocumentoNegocio tipoDocumentoNegocio;
	

	/**
	 * 
	 */
	public ResetTiposDocumentosServlet() {
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
		List<TipoDocumento> all = tipoDocumentoNegocio.findAll(null, null, 0, MAX_BATCH_RESULTS);
		if (!all.isEmpty()) {
			log(String.format("Removendo %s resgistros de TipoDocumento", all.size()));
			for (TipoDocumento tipoDocumento : all) {
				try {
					tipoDocumentoNegocio.remove(tipoDocumento);
				}catch(Exception e) {
					log(String.format(
							"Erro ao remover tipoDocumento codigo/descricao/tribunal: %s/%s/%s",
							tipoDocumento.getCodigo(),
							tipoDocumento.getDescricao(),
							tipoDocumento.getTribunal()), e);
				}
			}
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withUrl("/resetTiposDocumentos")
					.method(Method.POST));
			log(String.format("Queue de resetTiposDocumentos criada para %s, %s",
					all.size(), MAX_BATCH_RESULTS));
		}
	}

}
