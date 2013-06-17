package com.ctm.eadvogado.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.ws.Holder;

import br.jus.cnj.pje.IntercomunicacaoService;
import br.jus.cnj.pje.ServicoIntercomunicacao21;
import br.jus.cnj.pje.TipoProcessoJudicial;

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
		Calendar dataReferencia = null;
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
		IntercomunicacaoService service = new IntercomunicacaoService(new URL(urlEndpoint));
		if (service != null) {
			processoJudicial = consultarProcessoJudicial(service.getIntercomunicacaoImplPort(urlEndpoint), numeroProcesso);
		}
		return processoJudicial;
	}

}
