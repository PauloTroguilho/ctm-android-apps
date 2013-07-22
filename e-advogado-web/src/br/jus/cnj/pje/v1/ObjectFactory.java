
package br.jus.cnj.pje.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.jus.cnj.pje.v1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ConsultarAvisosPendentes_QNAME = new QName("http://www.cnj.jus.br/servico-intercomunicacao-2.1/", "consultarAvisosPendentes");
    private final static QName _ConsultarTeorComunicacaoResposta_QNAME = new QName("http://www.cnj.jus.br/servico-intercomunicacao-2.1/", "consultarTeorComunicacaoResposta");
    private final static QName _ConsultarAlteracaoResposta_QNAME = new QName("http://www.cnj.jus.br/servico-intercomunicacao-2.1/", "consultarAlteracaoResposta");
    private final static QName _EntregarManifestacaoProcessualResposta_QNAME = new QName("http://www.cnj.jus.br/servico-intercomunicacao-2.1/", "entregarManifestacaoProcessualResposta");
    private final static QName _Intercomunicacao_QNAME = new QName("http://www.cnj.jus.br/intercomunicacao-2.1", "intercomunicacao");
    private final static QName _ConsultarProcesso_QNAME = new QName("http://www.cnj.jus.br/servico-intercomunicacao-2.1/", "consultarProcesso");
    private final static QName _ConsultarAlteracao_QNAME = new QName("http://www.cnj.jus.br/servico-intercomunicacao-2.1/", "consultarAlteracao");
    private final static QName _ConsultarProcessoResposta_QNAME = new QName("http://www.cnj.jus.br/servico-intercomunicacao-2.1/", "consultarProcessoResposta");
    private final static QName _EntregarManifestacaoProcessual_QNAME = new QName("http://www.cnj.jus.br/servico-intercomunicacao-2.1/", "entregarManifestacaoProcessual");
    private final static QName _ConsultarAvisosPendentesResposta_QNAME = new QName("http://www.cnj.jus.br/servico-intercomunicacao-2.1/", "consultarAvisosPendentesResposta");
    private final static QName _ConsultarTeorComunicacao_QNAME = new QName("http://www.cnj.jus.br/servico-intercomunicacao-2.1/", "consultarTeorComunicacao");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.jus.cnj.pje.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TipoConsultarAvisosPendentes }
     * 
     */
    public TipoConsultarAvisosPendentes createTipoConsultarAvisosPendentes() {
        return new TipoConsultarAvisosPendentes();
    }

    /**
     * Create an instance of {@link TipoRepresentanteProcessual }
     * 
     */
    public TipoRepresentanteProcessual createTipoRepresentanteProcessual() {
        return new TipoRepresentanteProcessual();
    }

    /**
     * Create an instance of {@link TipoAvisoComunicacaoPendente }
     * 
     */
    public TipoAvisoComunicacaoPendente createTipoAvisoComunicacaoPendente() {
        return new TipoAvisoComunicacaoPendente();
    }

    /**
     * Create an instance of {@link TipoPessoa }
     * 
     */
    public TipoPessoa createTipoPessoa() {
        return new TipoPessoa();
    }

    /**
     * Create an instance of {@link TipoParametro }
     * 
     */
    public TipoParametro createTipoParametro() {
        return new TipoParametro();
    }

    /**
     * Create an instance of {@link TipoRelacionamentoPessoal }
     * 
     */
    public TipoRelacionamentoPessoal createTipoRelacionamentoPessoal() {
        return new TipoRelacionamentoPessoal();
    }

    /**
     * Create an instance of {@link TipoConsultarAlteracaoResposta }
     * 
     */
    public TipoConsultarAlteracaoResposta createTipoConsultarAlteracaoResposta() {
        return new TipoConsultarAlteracaoResposta();
    }

    /**
     * Create an instance of {@link TipoComunicacaoProcessual }
     * 
     */
    public TipoComunicacaoProcessual createTipoComunicacaoProcessual() {
        return new TipoComunicacaoProcessual();
    }

    /**
     * Create an instance of {@link TipoConsultarProcesso }
     * 
     */
    public TipoConsultarProcesso createTipoConsultarProcesso() {
        return new TipoConsultarProcesso();
    }

    /**
     * Create an instance of {@link TipoConsultarAvisosPendentesResposta }
     * 
     */
    public TipoConsultarAvisosPendentesResposta createTipoConsultarAvisosPendentesResposta() {
        return new TipoConsultarAvisosPendentesResposta();
    }

    /**
     * Create an instance of {@link TipoEntregarManifestacaoProcessual }
     * 
     */
    public TipoEntregarManifestacaoProcessual createTipoEntregarManifestacaoProcessual() {
        return new TipoEntregarManifestacaoProcessual();
    }

    /**
     * Create an instance of {@link TipoConsultarTeorComunicacaoResposta }
     * 
     */
    public TipoConsultarTeorComunicacaoResposta createTipoConsultarTeorComunicacaoResposta() {
        return new TipoConsultarTeorComunicacaoResposta();
    }

    /**
     * Create an instance of {@link TipoDocumento }
     * 
     */
    public TipoDocumento createTipoDocumento() {
        return new TipoDocumento();
    }

    /**
     * Create an instance of {@link TipoProcessoJudicial }
     * 
     */
    public TipoProcessoJudicial createTipoProcessoJudicial() {
        return new TipoProcessoJudicial();
    }

    /**
     * Create an instance of {@link TipoMovimentoNacional }
     * 
     */
    public TipoMovimentoNacional createTipoMovimentoNacional() {
        return new TipoMovimentoNacional();
    }

    /**
     * Create an instance of {@link TipoEndereco }
     * 
     */
    public TipoEndereco createTipoEndereco() {
        return new TipoEndereco();
    }

    /**
     * Create an instance of {@link TipoAssuntoLocal }
     * 
     */
    public TipoAssuntoLocal createTipoAssuntoLocal() {
        return new TipoAssuntoLocal();
    }

    /**
     * Create an instance of {@link TipoPoloProcessual }
     * 
     */
    public TipoPoloProcessual createTipoPoloProcessual() {
        return new TipoPoloProcessual();
    }

    /**
     * Create an instance of {@link TipoEntregarManifestacaoProcessualResposta }
     * 
     */
    public TipoEntregarManifestacaoProcessualResposta createTipoEntregarManifestacaoProcessualResposta() {
        return new TipoEntregarManifestacaoProcessualResposta();
    }

    /**
     * Create an instance of {@link TipoAssinatura }
     * 
     */
    public TipoAssinatura createTipoAssinatura() {
        return new TipoAssinatura();
    }

    /**
     * Create an instance of {@link TipoIntercomunicacao }
     * 
     */
    public TipoIntercomunicacao createTipoIntercomunicacao() {
        return new TipoIntercomunicacao();
    }

    /**
     * Create an instance of {@link TipoCabecalhoProcesso }
     * 
     */
    public TipoCabecalhoProcesso createTipoCabecalhoProcesso() {
        return new TipoCabecalhoProcesso();
    }

    /**
     * Create an instance of {@link TipoConsultarProcessoResposta }
     * 
     */
    public TipoConsultarProcessoResposta createTipoConsultarProcessoResposta() {
        return new TipoConsultarProcessoResposta();
    }

    /**
     * Create an instance of {@link TipoParte }
     * 
     */
    public TipoParte createTipoParte() {
        return new TipoParte();
    }

    /**
     * Create an instance of {@link TipoAssuntoProcessual }
     * 
     */
    public TipoAssuntoProcessual createTipoAssuntoProcessual() {
        return new TipoAssuntoProcessual();
    }

    /**
     * Create an instance of {@link TipoConsultarTeorComunicacao }
     * 
     */
    public TipoConsultarTeorComunicacao createTipoConsultarTeorComunicacao() {
        return new TipoConsultarTeorComunicacao();
    }

    /**
     * Create an instance of {@link TipoMovimentoLocal }
     * 
     */
    public TipoMovimentoLocal createTipoMovimentoLocal() {
        return new TipoMovimentoLocal();
    }

    /**
     * Create an instance of {@link TipoMovimentoProcessual }
     * 
     */
    public TipoMovimentoProcessual createTipoMovimentoProcessual() {
        return new TipoMovimentoProcessual();
    }

    /**
     * Create an instance of {@link TipoConsultarAlteracao }
     * 
     */
    public TipoConsultarAlteracao createTipoConsultarAlteracao() {
        return new TipoConsultarAlteracao();
    }

    /**
     * Create an instance of {@link TipoVinculacaoProcessual }
     * 
     */
    public TipoVinculacaoProcessual createTipoVinculacaoProcessual() {
        return new TipoVinculacaoProcessual();
    }

    /**
     * Create an instance of {@link TipoDocumentoIdentificacao }
     * 
     */
    public TipoDocumentoIdentificacao createTipoDocumentoIdentificacao() {
        return new TipoDocumentoIdentificacao();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoConsultarAvisosPendentes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", name = "consultarAvisosPendentes")
    public JAXBElement<TipoConsultarAvisosPendentes> createConsultarAvisosPendentes(TipoConsultarAvisosPendentes value) {
        return new JAXBElement<TipoConsultarAvisosPendentes>(_ConsultarAvisosPendentes_QNAME, TipoConsultarAvisosPendentes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoConsultarTeorComunicacaoResposta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", name = "consultarTeorComunicacaoResposta")
    public JAXBElement<TipoConsultarTeorComunicacaoResposta> createConsultarTeorComunicacaoResposta(TipoConsultarTeorComunicacaoResposta value) {
        return new JAXBElement<TipoConsultarTeorComunicacaoResposta>(_ConsultarTeorComunicacaoResposta_QNAME, TipoConsultarTeorComunicacaoResposta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoConsultarAlteracaoResposta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", name = "consultarAlteracaoResposta")
    public JAXBElement<TipoConsultarAlteracaoResposta> createConsultarAlteracaoResposta(TipoConsultarAlteracaoResposta value) {
        return new JAXBElement<TipoConsultarAlteracaoResposta>(_ConsultarAlteracaoResposta_QNAME, TipoConsultarAlteracaoResposta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoEntregarManifestacaoProcessualResposta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", name = "entregarManifestacaoProcessualResposta")
    public JAXBElement<TipoEntregarManifestacaoProcessualResposta> createEntregarManifestacaoProcessualResposta(TipoEntregarManifestacaoProcessualResposta value) {
        return new JAXBElement<TipoEntregarManifestacaoProcessualResposta>(_EntregarManifestacaoProcessualResposta_QNAME, TipoEntregarManifestacaoProcessualResposta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoIntercomunicacao }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cnj.jus.br/intercomunicacao-2.1", name = "intercomunicacao")
    public JAXBElement<TipoIntercomunicacao> createIntercomunicacao(TipoIntercomunicacao value) {
        return new JAXBElement<TipoIntercomunicacao>(_Intercomunicacao_QNAME, TipoIntercomunicacao.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoConsultarProcesso }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", name = "consultarProcesso")
    public JAXBElement<TipoConsultarProcesso> createConsultarProcesso(TipoConsultarProcesso value) {
        return new JAXBElement<TipoConsultarProcesso>(_ConsultarProcesso_QNAME, TipoConsultarProcesso.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoConsultarAlteracao }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", name = "consultarAlteracao")
    public JAXBElement<TipoConsultarAlteracao> createConsultarAlteracao(TipoConsultarAlteracao value) {
        return new JAXBElement<TipoConsultarAlteracao>(_ConsultarAlteracao_QNAME, TipoConsultarAlteracao.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoConsultarProcessoResposta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", name = "consultarProcessoResposta")
    public JAXBElement<TipoConsultarProcessoResposta> createConsultarProcessoResposta(TipoConsultarProcessoResposta value) {
        return new JAXBElement<TipoConsultarProcessoResposta>(_ConsultarProcessoResposta_QNAME, TipoConsultarProcessoResposta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoEntregarManifestacaoProcessual }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", name = "entregarManifestacaoProcessual")
    public JAXBElement<TipoEntregarManifestacaoProcessual> createEntregarManifestacaoProcessual(TipoEntregarManifestacaoProcessual value) {
        return new JAXBElement<TipoEntregarManifestacaoProcessual>(_EntregarManifestacaoProcessual_QNAME, TipoEntregarManifestacaoProcessual.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoConsultarAvisosPendentesResposta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", name = "consultarAvisosPendentesResposta")
    public JAXBElement<TipoConsultarAvisosPendentesResposta> createConsultarAvisosPendentesResposta(TipoConsultarAvisosPendentesResposta value) {
        return new JAXBElement<TipoConsultarAvisosPendentesResposta>(_ConsultarAvisosPendentesResposta_QNAME, TipoConsultarAvisosPendentesResposta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipoConsultarTeorComunicacaoResposta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cnj.jus.br/servico-intercomunicacao-2.1/", name = "consultarTeorComunicacao")
    public JAXBElement<TipoConsultarTeorComunicacaoResposta> createConsultarTeorComunicacao(TipoConsultarTeorComunicacaoResposta value) {
        return new JAXBElement<TipoConsultarTeorComunicacaoResposta>(_ConsultarTeorComunicacao_QNAME, TipoConsultarTeorComunicacaoResposta.class, null, value);
    }

}
