/**
 * 
 */
package com.ctm.eadvogado.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceException;

import br.jus.cnj.pje.consulta.v1.TipoDocumentoProcessual;

import com.ctm.eadvogado.model.TipoDocumento;
import com.ctm.eadvogado.model.TipoJuizo;
import com.ctm.eadvogado.model.Tribunal;
import com.ctm.eadvogado.negocio.TipoDocumentoNegocio;
import com.ctm.eadvogado.negocio.TribunalNegocio;
import com.ctm.eadvogado.util.PJeServiceUtil;
import com.ctm.eadvogado.util.WeldUtils;

/**
 * @author Cleber
 * 
 */
public class ConsultarTiposDocumentosServlet extends HttpServlet {

	private static final String CONSULTA_PJE_WSDL_SUFIX = "/ConsultaPJe?wsdl";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private TribunalNegocio tribunalNegocio;
	@Inject
	private TipoDocumentoNegocio tipoDocumentoNegocio;

	/**
	 * 
	 */
	public ConsultarTiposDocumentosServlet() {
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
		String pIdTribunal = req.getParameter("idTribunal");
		log(String.format("Consultando tiposDocumentos no tribunal: %s", pIdTribunal));
		Tribunal tribunal = null;
		try {
			tribunal = tribunalNegocio.findByID(Long.parseLong(pIdTribunal));
		} catch (PersistenceException e) {}
		
		if (tribunal != null) {
			String pje1gEndpoint = tribunal.getPje1gEndpoint();
			if (pje1gEndpoint != null && !pje1gEndpoint.trim().isEmpty()) {
				inserirTiposDocumentos(pje1gEndpoint, tribunal, TipoJuizo.PRIMEIRO_GRAU);
			}
			String pje2gEndpoint = tribunal.getPje1gEndpoint();
			if (pje2gEndpoint != null && !pje2gEndpoint.trim().isEmpty()) {
				inserirTiposDocumentos(pje2gEndpoint, tribunal, TipoJuizo.SEGUNDO_GRAU);
			}
			log("Atualização de tipos de documentos finalizada para o tribunal: " + tribunal.getSigla());
		} else {
			log(String.format("Nao foi encontrado tribunal com id: %s", pIdTribunal));
		}
	}
	
	public static void main(String[] args) {
		String pje1gEndpoint = "http://pje.trt13.jus.br/primeirograu/intercomunicacao?WSDL";
		if (pje1gEndpoint != null && !pje1gEndpoint.trim().isEmpty()) {
			String consultaEndpoint = pje1gEndpoint.substring(0, pje1gEndpoint.lastIndexOf("/"));
			System.out.println(consultaEndpoint);
		}
	}

	/**
	 * @param pjeEndpoint
	 * @param tribunal
	 * @param tipoJuizo
	 */
	private void inserirTiposDocumentos(String pjeEndpoint, Tribunal tribunal, TipoJuizo tipoJuizo) {
		String consultaEndpoint = pjeEndpoint.substring(0, pjeEndpoint.lastIndexOf("/"));
		consultaEndpoint += CONSULTA_PJE_WSDL_SUFIX;
		List<TipoDocumentoProcessual> tiposDocumentos = new ArrayList<TipoDocumentoProcessual>();
		try {
			tiposDocumentos = PJeServiceUtil.consultarTiposDocumentoProcessual(consultaEndpoint, null);
		} catch(WebServiceException e) {}
		for (TipoDocumentoProcessual tipo : tiposDocumentos) {
			TipoDocumento tipoDocumento = tipoDocumentoNegocio
					.findByTribunalETipoJuizoECodigo(tribunal,
							tipoJuizo, tipo.getCodigo());
			if (tipoDocumento == null) {
				tipoDocumento = new TipoDocumento();
				tipoDocumento.setCodigo(tipo.getCodigo());
				tipoDocumento.setDescricao(tipo.getDescricao());
				tipoDocumento.setTribunal(tribunal.getKey());
				tipoDocumento.setTipoJuizo(tipoJuizo);
				try {
					tipoDocumentoNegocio.insert(tipoDocumento);
				} catch (Exception e) {
					log(String
							.format("Falha ao inserir tipoDocumento. tribunal/tipoJuizo/codigo/descricao = %s/%s/%s/%s",
									tribunal.getSigla(), tipoJuizo, tipo.getCodigo(), tipo.getDescricao()),
							e);
				}
			}
		}
		tribunal.setDataAtualizacao(new Date());
		try {
			tribunalNegocio.update(tribunal);
		} catch (Exception e) {
			log(String
					.format("Falha ao atualizar dataAtualizacao do tribunal: %s",
							tribunal.getSigla()), e);
		}
	}
}
