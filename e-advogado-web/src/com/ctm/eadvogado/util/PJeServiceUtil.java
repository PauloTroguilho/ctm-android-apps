package com.ctm.eadvogado.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceException;

import br.jus.cnj.pje.consulta.v1.ConsultaPJe;
import br.jus.cnj.pje.consulta.v1.ConsultaPJeService;
import br.jus.cnj.pje.consulta.v1.TipoDocumentoProcessual;
import br.jus.cnj.pje.v1.IntercomunicacaoService;
import br.jus.cnj.pje.v1.ServicoIntercomunicacao21;
import br.jus.cnj.pje.v1.TipoDocumento;
import br.jus.cnj.pje.v1.TipoProcessoJudicial;

public class PJeServiceUtil {
	
	private static final Logger log = Logger.getLogger(PJeServiceUtil.class.getName());
	
	private static final String ID_CONSULTANTE = "123";
	private static final String SENHA_CONSULTANTE = "123";
	
	/**
	 * Consulta um processo no endereço wsdl informado.
	 * 
	 * @param wsdlURL
	 * @param idConsultante
	 * @param senhaConsultante
	 * @param numeroProcesso
	 * @param dataReferencia
	 * @param incluirMovimentos
	 * @param incluirDocumentos
	 * @param documento
	 * @return
	 * @throws WebServiceException
	 */
	public static TipoProcessoJudicial consultarProcessoJudicial(String wsdlURL, String idConsultante,
			String senhaConsultante, String numeroProcesso,
			Date dataReferencia, Boolean incluirMovimentos,
			Boolean incluirDocumentos, List<String> documento) throws WebServiceException {
		Holder<Boolean> sucesso = new Holder<Boolean>();
		Holder<String> mensagem = new Holder<String>();
		Holder<TipoProcessoJudicial> processo = new Holder<TipoProcessoJudicial>();
		String dataRefStr = null;
		if (dataReferencia != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dataReferencia);
			dataRefStr = TipoDataHoraConverter.marshal(cal);
		}
		ServicoIntercomunicacao21 servico = getPortFromEndpoint(wsdlURL);
		try {
			servico.consultarProcesso(idConsultante, senhaConsultante,
					numeroProcesso, dataRefStr, incluirMovimentos,
					incluirDocumentos, documento, sucesso, mensagem, processo);
		} catch(WebServiceException e) {
			log.log(Level.WARNING, 
				"Falha no serviço ao consultar processo no endpoint: " + wsdlURL, e);
			throw e;
		} catch(Exception e) {
			log.log(Level.WARNING, 
				"Erro ao consultar processo com endereço do endpoint: " + wsdlURL, e);
			servico = getPortFromURL(wsdlURL);
			try {
				servico.consultarProcesso(idConsultante, senhaConsultante,
						numeroProcesso, dataRefStr, incluirMovimentos,
						incluirDocumentos, documento, sucesso, mensagem, processo);
			} catch(WebServiceException e1) {
				log.log(Level.WARNING, 
						"Falha no serviço ao consultar processo com wsdlURL: " + wsdlURL, e1);
					throw e1;
			} catch(Exception e1) {
				log.log(Level.WARNING, "Erro ao consultar processo com wsdlURL: " + wsdlURL, e);
				throw new RuntimeException("Falha ao consultar processo.", e1);
			}
		}
		return processo.value;
	}
	
	/**
	 * Consulta um processo no endereço wsdl informado.
	 * 
	 * @param wsdlURL
	 * @param numeroProcesso
	 * @param dataReferencia
	 * @param incluirMovimentos
	 * @param incluirDocumentos
	 * @param documento
	 * @return
	 * @throws WebServiceException
	 */
	public static TipoProcessoJudicial consultarProcessoJudicial(String wsdlURL,
			String numeroProcesso, Date dataReferencia,
			Boolean incluirMovimentos, Boolean incluirDocumentos,
			List<String> documento)  throws WebServiceException {
		return consultarProcessoJudicial(wsdlURL, ID_CONSULTANTE,
				SENHA_CONSULTANTE, numeroProcesso, dataReferencia,
				incluirMovimentos, incluirDocumentos, documento);
	}
	
	/**
	 * Consulta um processo no endereço wsdl informado.
	 * 
	 * @param wsdlURL
	 * @param idConsultante
	 * @param senhaConsultante
	 * @param numeroProcesso
	 * @return
	 * @throws WebServiceException
	 */
	public static TipoProcessoJudicial consultarProcessoJudicial(String wsdlURL,
			String idConsultante,
			String senhaConsultante,
			String numeroProcesso)  throws WebServiceException {
		return consultarProcessoJudicial(wsdlURL, idConsultante, senhaConsultante, numeroProcesso, null, Boolean.TRUE, Boolean.FALSE, null);
	}
	
	
	
	/**
	 * Consulta um processo no endereço wsdl informado.
	 * 
	 * @param wsdlURL
	 * @param numeroProcesso
	 * @return
	 */
	public static TipoProcessoJudicial consultarProcessoJudicial(String wsdlURL,
			String numeroProcesso) {
		return consultarProcessoJudicial(wsdlURL, ID_CONSULTANTE, SENHA_CONSULTANTE, numeroProcesso);
	}
	
	/**
	 * Consultar uma lista de documentos do processo.
	 * 
	 * @param wsdlURL
	 * @param idConsultante
	 * @param senhaConsultante
	 * @param numeroProcesso
	 * @param documentoIds
	 * @return
	 * @throws WebServiceException
	 */
	public static List<TipoDocumento> consultarDocumentos(String wsdlURL, String idConsultante,
			String senhaConsultante, String numeroProcesso, List<String> documentoIds)  throws WebServiceException {
		TipoProcessoJudicial processoJudicial = consultarProcessoJudicial(
				wsdlURL, idConsultante, senhaConsultante, numeroProcesso, null, Boolean.FALSE, Boolean.TRUE, documentoIds);
		List<TipoDocumento> documentosList = new ArrayList<TipoDocumento>();
		if (processoJudicial != null) {
			if (!processoJudicial.getDocumento().isEmpty()) {
				for (TipoDocumento tipoDocumento : processoJudicial.getDocumento()) {
					if (documentoIds.contains(tipoDocumento.getIdDocumento())) {
						documentosList.add(tipoDocumento);
					}
				}
			}
		}
		return documentosList;
	}
	
	/**
	 * Consultar uma lista de documentos do processo.
	 * 
	 * @param wsdlURL
	 * @param numeroProcesso
	 * @param documentoIds
	 * @return
	 */
	public static List<TipoDocumento> consultarDocumentos(String wsdlURL, String numeroProcesso, List<String> documentoIds) {
		return consultarDocumentos(wsdlURL, ID_CONSULTANTE, SENHA_CONSULTANTE, numeroProcesso, documentoIds);
	}
	
	/**
	 * Consultar um documento do processo.
	 * 
	 * @param wsdlURL
	 * @param idConsultante
	 * @param senhaConsultante
	 * @param numeroProcesso
	 * @param documentoId
	 * @return
	 */
	public static TipoDocumento consultarDocumento(String wsdlURL, String idConsultante,
			String senhaConsultante, String numeroProcesso, String documentoId) {
		List<TipoDocumento> documentos = consultarDocumentos(wsdlURL, idConsultante, senhaConsultante, numeroProcesso, Arrays.asList(documentoId));
		return (documentos.size() > 0) ? documentos.get(0) : null;
	}
	
	/**
	 * Consultar um documento do processo.
	 * 
	 * @param wsdlURL
	 * @param numeroProcesso
	 * @param documentoId
	 * @return
	 */
	public static TipoDocumento consultarDocumento(String wsdlURL, String numeroProcesso, String documentoId) {
		return consultarDocumento(wsdlURL, ID_CONSULTANTE, SENHA_CONSULTANTE, numeroProcesso, documentoId);
	}
	
	/**
	 * @param wsdlURL
	 * @return
	 */
	private static ServicoIntercomunicacao21 getPortFromEndpoint(String wsdlURL) {
		String endpoint = null;
		if (wsdlURL.toLowerCase().endsWith("?wsdl")) {
			endpoint = wsdlURL.substring(0, 
					wsdlURL.toLowerCase().indexOf("?wsdl"));
		}
		IntercomunicacaoService service = new IntercomunicacaoService();
		ServicoIntercomunicacao21 port = service.getPort(ServicoIntercomunicacao21.class);
		BindingProvider bp = (BindingProvider) port;
		setupBindingProvider(bp, endpoint);
		return port;
	}
	
	/**
	 * @param wsdlURL
	 * @return
	 */
	private static ServicoIntercomunicacao21 getPortFromURL(String wsdlURL) {
		String endpoint = null;
		if (wsdlURL.toLowerCase().endsWith("?wsdl")) {
			endpoint = wsdlURL.substring(0, 
					wsdlURL.toLowerCase().indexOf("?wsdl"));
		}
		URL url = null;
		try {
			url = new URL(wsdlURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		IntercomunicacaoService service = new IntercomunicacaoService(
				url, new QName("http://www.cnj.jus.br/servico-intercomunicacao-2.1/", "IntercomunicacaoService"));
		ServicoIntercomunicacao21 port = service
				.getPort(ServicoIntercomunicacao21.class);
		BindingProvider bp = (BindingProvider) port;
		setupBindingProvider(bp, endpoint);
		return port;
	}
	
	private static ConsultaPJe getPortConsultaFromEndpoint(String wsdlURL) {
		String endpoint = null;
		if (wsdlURL.toLowerCase().endsWith("?wsdl")) {
			endpoint = wsdlURL.substring(0, 
					wsdlURL.toLowerCase().indexOf("?wsdl"));
		}
		ConsultaPJeService service = new ConsultaPJeService();
		ConsultaPJe port = service.getPort(ConsultaPJe.class);
		BindingProvider bp = (BindingProvider) port;
		setupBindingProvider(bp, endpoint);
		return port;
	}
	
	private static ConsultaPJe getPortConsultaFromURL(String wsdlURL) {
		String endpoint = null;
		if (wsdlURL.toLowerCase().endsWith("?wsdl")) {
			endpoint = wsdlURL.substring(0, 
					wsdlURL.toLowerCase().indexOf("?wsdl"));
		}
		URL url = null;
		try {
			url = new URL(wsdlURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ConsultaPJeService service = new ConsultaPJeService(
				url, new QName("http://ws.pje.cnj.jus.br/", "ConsultaPJeService"));
		ConsultaPJe port = service
				.getPort(ConsultaPJe.class);
		BindingProvider bp = (BindingProvider) port;
		setupBindingProvider(bp, endpoint);
		return port;
	}
	
	/**
	 * @param bp
	 * @param endpoint
	 */
	private static void setupBindingProvider(BindingProvider bp, String endpoint) {
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
		bp.getRequestContext().put("com.sun.xml.ws.request.timeout", 60000 * 3);
		bp.getRequestContext().put("com.sun.xml.ws.connect.timeout", 60000 * 3);
		bp.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", 60000 * 3);
		bp.getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", 60000 * 3);
	}
	
	
	
	/**
	 * @param wsdlURL
	 * @param param
	 * @return
	 * @throws WebServiceException
	 */
	public static List<TipoDocumentoProcessual> consultarTiposDocumentoProcessual(String wsdlURL, String param) throws WebServiceException {
		ConsultaPJe servico = getPortConsultaFromEndpoint(wsdlURL);
		List<TipoDocumentoProcessual> result = null;
		try {
			result = servico.consultarTiposDocumentoProcessual(param);
		} catch(WebServiceException e) {
			log.log(Level.WARNING, 
				"Falha no serviço ao consultar tiposDocumentos no endpoint: " + wsdlURL, e);
			throw e;
		} catch(Exception e) {
			log.log(Level.WARNING, 
				"Erro ao consultar tiposDocumentos com endereço do endpoint: " + wsdlURL, e);
			servico = getPortConsultaFromURL(wsdlURL);
			try {
				result = servico.consultarTiposDocumentoProcessual(param);
			} catch(WebServiceException e1) {
				log.log(Level.WARNING, 
						"Falha no serviço ao consultar tiposDocumentos com wsdlURL: " + wsdlURL, e1);
					throw e1;
			} catch(Exception e1) {
				log.log(Level.WARNING, "Erro ao consultar tiposDocumentos com wsdlURL: " + wsdlURL, e);
				throw new RuntimeException("Falha ao consultar processo.", e1);
			}
		}
		return result;
	}


	public static void main(String[] args) {
		List<TipoDocumentoProcessual> tipos = consultarTiposDocumentoProcessual("http://pje.trt5.jus.br/primeirograu/ConsultaPJe?wsdl", null);
		System.out.println(tipos);
	}

}
