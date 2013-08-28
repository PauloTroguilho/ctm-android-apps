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

import com.ctm.eadvogado.model.Processo;
import com.ctm.eadvogado.negocio.ProcessoNegocio;
import com.ctm.eadvogado.util.WeldUtils;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * @author Cleber
 * 
 */
public class AtualizarProcessosServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ProcessoNegocio processoNegocio;

	/**
	 * 
	 */
	public AtualizarProcessosServlet() {
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
		List<Processo> processos = processoNegocio.findAllAssociados();
		for (Processo processo : processos) {
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withUrl("/consultarProcessoAsync")
					.method(Method.POST)
					.param("npu",processo.getNpu())
					.param("idTribunal", processo.getTribunal().getId() + "")
					.param("tipoJuizo", processo.getTipoJuizo().name()));
			log(String.format(
					"Queue de consulta criada para o processo %s, %s, %s",
					processo.getNpu(), processo.getTribunal().getId(),
					processo.getTipoJuizo()));
		}
	}

}
