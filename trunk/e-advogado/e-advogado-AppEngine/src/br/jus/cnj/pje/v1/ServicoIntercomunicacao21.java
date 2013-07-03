
package br.jus.cnj.pje.v1;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "servico-intercomunicacao-2.1", targetNamespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ServicoIntercomunicacao21 {


    /**
     * 
     * @param hashDocumentos
     * @param mensagem
     * @param senhaConsultante
     * @param sucesso
     * @param hashMovimentacoes
     * @param numeroProcesso
     * @param idConsultante
     * @param hashCabecalho
     */
    @WebMethod(action = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/consultarAlteracao")
    @RequestWrapper(localName = "consultarAlteracao", targetNamespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", className = "br.jus.cnj.pje.v1.TipoConsultarAlteracao")
    @ResponseWrapper(localName = "consultarAlteracaoResposta", targetNamespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", className = "br.jus.cnj.pje.v1.TipoConsultarAlteracaoResposta")
    public void consultarAlteracao(
        @WebParam(name = "idConsultante", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        String idConsultante,
        @WebParam(name = "senhaConsultante", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        String senhaConsultante,
        @WebParam(name = "numeroProcesso", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        String numeroProcesso,
        @WebParam(name = "sucesso", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<Boolean> sucesso,
        @WebParam(name = "mensagem", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<String> mensagem,
        @WebParam(name = "hashCabecalho", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<String> hashCabecalho,
        @WebParam(name = "hashMovimentacoes", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<String> hashMovimentacoes,
        @WebParam(name = "hashDocumentos", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<String> hashDocumentos);

    /**
     * 
     * @param mensagem
     * @param dataReferencia
     * @param senhaConsultante
     * @param sucesso
     * @param aviso
     * @param idRepresentado
     * @param idConsultante
     */
    @WebMethod(action = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/consultarAvisosPendentes")
    @RequestWrapper(localName = "consultarAvisosPendentes", targetNamespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", className = "br.jus.cnj.pje.v1.TipoConsultarAvisosPendentes")
    @ResponseWrapper(localName = "consultarAvisosPendentesResposta", targetNamespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", className = "br.jus.cnj.pje.v1.TipoConsultarAvisosPendentesResposta")
    public void consultarAvisosPendentes(
        @WebParam(name = "idRepresentado", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        String idRepresentado,
        @WebParam(name = "idConsultante", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        String idConsultante,
        @WebParam(name = "senhaConsultante", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        String senhaConsultante,
        @WebParam(name = "dataReferencia", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        String dataReferencia,
        @WebParam(name = "sucesso", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<Boolean> sucesso,
        @WebParam(name = "mensagem", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<String> mensagem,
        @WebParam(name = "aviso", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<List<TipoAvisoComunicacaoPendente>> aviso);

    /**
     * 
     * @param processo
     * @param mensagem
     * @param dataReferencia
     * @param senhaConsultante
     * @param incluirDocumentos
     * @param sucesso
     * @param documento
     * @param numeroProcesso
     * @param idConsultante
     * @param movimentos
     */
    @WebMethod(action = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/consultarProcesso")
    @RequestWrapper(localName = "consultarProcesso", targetNamespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", className = "br.jus.cnj.pje.v1.TipoConsultarProcesso")
    @ResponseWrapper(localName = "consultarProcessoResposta", targetNamespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", className = "br.jus.cnj.pje.v1.TipoConsultarProcessoResposta")
    public void consultarProcesso(
        @WebParam(name = "idConsultante", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        String idConsultante,
        @WebParam(name = "senhaConsultante", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        String senhaConsultante,
        @WebParam(name = "numeroProcesso", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        String numeroProcesso,
        @WebParam(name = "dataReferencia", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        String dataReferencia,
        @WebParam(name = "movimentos", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        Boolean movimentos,
        @WebParam(name = "incluirDocumentos", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        Boolean incluirDocumentos,
        @WebParam(name = "documento", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        List<String> documento,
        @WebParam(name = "sucesso", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<Boolean> sucesso,
        @WebParam(name = "mensagem", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<String> mensagem,
        @WebParam(name = "processo", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<TipoProcessoJudicial> processo);

    /**
     * 
     * @param comunicacao
     * @param mensagem
     * @param sucesso
     */
    @WebMethod(action = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/consultarTeorComunicacao")
    @RequestWrapper(localName = "consultarTeorComunicacao", targetNamespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", className = "br.jus.cnj.pje.v1.TipoConsultarTeorComunicacaoResposta")
    @ResponseWrapper(localName = "consultarTeorComunicacaoResposta", targetNamespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", className = "br.jus.cnj.pje.v1.TipoConsultarTeorComunicacaoResposta")
    public void consultarTeorComunicacao(
        @WebParam(name = "sucesso", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.INOUT)
        Holder<Boolean> sucesso,
        @WebParam(name = "mensagem", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.INOUT)
        Holder<String> mensagem,
        @WebParam(name = "comunicacao", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.INOUT)
        Holder<List<TipoComunicacaoProcessual>> comunicacao);

    /**
     * 
     * @param recibo
     * @param mensagem
     * @param parametro
     * @param dataEnvio
     * @param dadosBasicos
     * @param documento
     * @param sucesso
     * @param idManifestante
     * @param parametros
     * @param senhaManifestante
     * @param protocoloRecebimento
     * @param dataOperacao
     * @param numeroProcesso
     */
    @WebMethod(action = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/entregarManifestacaoProcessual")
    @RequestWrapper(localName = "entregarManifestacaoProcessual", targetNamespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", className = "br.jus.cnj.pje.v1.TipoEntregarManifestacaoProcessual")
    @ResponseWrapper(localName = "entregarManifestacaoProcessualResposta", targetNamespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", className = "br.jus.cnj.pje.v1.TipoEntregarManifestacaoProcessualResposta")
    public void entregarManifestacaoProcessual(
        @WebParam(name = "idManifestante", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        String idManifestante,
        @WebParam(name = "senhaManifestante", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        String senhaManifestante,
        @WebParam(name = "numeroProcesso", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        String numeroProcesso,
        @WebParam(name = "dadosBasicos", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        TipoCabecalhoProcesso dadosBasicos,
        @WebParam(name = "documento", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        List<TipoDocumento> documento,
        @WebParam(name = "dataEnvio", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        String dataEnvio,
        @WebParam(name = "parametros", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1")
        List<TipoParametro> parametros,
        @WebParam(name = "sucesso", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<Boolean> sucesso,
        @WebParam(name = "mensagem", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<String> mensagem,
        @WebParam(name = "protocoloRecebimento", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<String> protocoloRecebimento,
        @WebParam(name = "dataOperacao", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<String> dataOperacao,
        @WebParam(name = "recibo", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<byte[]> recibo,
        @WebParam(name = "parametro", targetNamespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", mode = WebParam.Mode.OUT)
        Holder<List<TipoParametro>> parametro);

}
