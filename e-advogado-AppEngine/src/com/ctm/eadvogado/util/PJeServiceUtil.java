package com.ctm.eadvogado.util;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import br.jus.cnj.pje.v1.IntercomunicacaoService;
import br.jus.cnj.pje.v1.ServicoIntercomunicacao21;
import br.jus.cnj.pje.v1.TipoProcessoJudicial;

public class PJeServiceUtil {
	
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
		servico.consultarProcesso(idConsultante, senhaConsultante, numeroProcesso, dataReferencia, movimentos, incluirDocumentos, documento, sucesso, mensagem, processo);
		return processo.value;
	}
	
	/**
	 * @param urlEndpoint
	 * @param numeroProcesso
	 * @return
	 * @throws MalformedURLException 
	 */
	public static TipoProcessoJudicial consultarProcessoJudicial(
			String urlEndpoint, String numeroProcesso) throws MalformedURLException {
		TipoProcessoJudicial processoJudicial = null;
		IntercomunicacaoService service = new IntercomunicacaoService();
		if (service != null) {
			if (urlEndpoint.toLowerCase().endsWith("?wsdl")) {
				urlEndpoint = urlEndpoint.substring(0, urlEndpoint.toLowerCase().indexOf("?wsdl"));
			}
			ServicoIntercomunicacao21 port = service.getIntercomunicacaoImplPort();
			BindingProvider bp = (BindingProvider) port;
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, urlEndpoint);
			processoJudicial = consultarProcessoJudicial(port, numeroProcesso);
		}
		return processoJudicial;
	}
	
	public static void main(String[] args) {
		String urlEndpoint = "http://www.teste.com?wsdl";
		urlEndpoint = urlEndpoint.substring(0, urlEndpoint.toLowerCase().indexOf("?wsdl"));
		System.out.println(urlEndpoint);
	}

}
