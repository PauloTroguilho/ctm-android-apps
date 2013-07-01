package com.ctm.eadvogado.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import br.jus.cnj.pje.v1.IntercomunicacaoService;
import br.jus.cnj.pje.v1.ServicoIntercomunicacao21;
import br.jus.cnj.pje.v1.TipoProcessoJudicial;

public class PJeServiceUtil {
	
	private static final Logger log = Logger.getLogger(PJeServiceUtil.class.getName());

	/**
	 * @param service
	 * @param npu
	 * @return
	 */
	public static TipoProcessoJudicial consultarProcessoJudicial(
			ServicoIntercomunicacao21 servico, String numeroProcesso) {
		Holder<TipoProcessoJudicial> processo = new Holder<TipoProcessoJudicial>();
		String idConsultante = "123";
		String senhaConsultante = "123";
		String dataReferencia = null;
		Boolean movimentos = Boolean.TRUE;
		Boolean incluirDocumentos = Boolean.FALSE;
		List<String> documento = new ArrayList<String>();
		Holder<Boolean> sucesso = new Holder<Boolean>();
		Holder<String> mensagem = new Holder<String>();
		servico.consultarProcesso(idConsultante, senhaConsultante,
				numeroProcesso, dataReferencia, movimentos, incluirDocumentos,
				documento, sucesso, mensagem, processo);
		return processo.value;
	}

	/**
	 * @param wsdlURL
	 * @param numeroProcesso
	 * @return
	 * @throws MalformedURLException
	 */
	public static TipoProcessoJudicial consultarProcessoJudicial(
			String wsdlURL, String numeroProcesso)
			throws MalformedURLException {
		TipoProcessoJudicial processoJudicial = null;
		String endpoint = null;
		if (wsdlURL.toLowerCase().endsWith("?wsdl")) {
			endpoint = wsdlURL.substring(0, 
					wsdlURL.toLowerCase().indexOf("?wsdl"));
		}
		try {
			IntercomunicacaoService service = new IntercomunicacaoService();
			ServicoIntercomunicacao21 port = service
					.getPort(ServicoIntercomunicacao21.class);
			BindingProvider bp = (BindingProvider) port;
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
			bp.getRequestContext().put("com.sun.xml.ws.request.timeout", 60000 * 3);
			bp.getRequestContext().put("com.sun.xml.ws.connect.timeout", 60000 * 3);
			bp.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", 60000 * 3);
			bp.getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", 60000 * 3);
			processoJudicial = consultarProcessoJudicial(port, numeroProcesso);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			log.log(Level.WARNING, "Erro ao consultar processo", e);
			try {
				URL url = new URL(wsdlURL);
				IntercomunicacaoService service = new IntercomunicacaoService(
						url, new QName("http://www.cnj.jus.br/servico-intercomunicacao-2.1/", "IntercomunicacaoService"));
				ServicoIntercomunicacao21 port = service
						.getPort(ServicoIntercomunicacao21.class);
				BindingProvider bp = (BindingProvider) port;
				bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
				bp.getRequestContext().put("com.sun.xml.ws.request.timeout", 60000 * 3);
				bp.getRequestContext().put("com.sun.xml.ws.connect.timeout", 60000 * 3);
				bp.getRequestContext().put("com.sun.xml.internal.ws.request.timeout", 60000 * 3);
				bp.getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", 60000 * 3);
				processoJudicial = consultarProcessoJudicial(port, numeroProcesso);
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
				log.log(Level.WARNING, "Erro ao consultar processo", e1);
				throw new RuntimeException(e1);
			}
		}
		return processoJudicial;
	}

	public static void main(String[] args) {
		String urlEndpoint = "https://www.tjpe.jus.br/pje/intercomunicacao?WSDL";
		urlEndpoint = urlEndpoint.substring(0, urlEndpoint.toLowerCase()
				.indexOf("?wsdl"));
		System.out.println(urlEndpoint);
	}

}
