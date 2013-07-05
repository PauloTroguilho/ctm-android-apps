
package br.jus.cnj.pje.v1;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoConsultarAlteracaoResposta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoConsultarAlteracaoResposta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sucesso" type="{http://www.w3.org/2001/XMLSchema}boolean" form="qualified"/>
 *         &lt;element name="mensagem" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *         &lt;element name="hashCabecalho" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="hashMovimentacoes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="hashDocumentos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoConsultarAlteracaoResposta", namespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", propOrder = {
    "sucesso",
    "mensagem",
    "hashCabecalho",
    "hashMovimentacoes",
    "hashDocumentos"
})
public class TipoConsultarAlteracaoResposta
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected boolean sucesso;
    @XmlElement(required = true)
    protected String mensagem;
    protected String hashCabecalho;
    protected String hashMovimentacoes;
    protected String hashDocumentos;

    /**
     * Gets the value of the sucesso property.
     * 
     */
    public boolean isSucesso() {
        return sucesso;
    }

    /**
     * Sets the value of the sucesso property.
     * 
     */
    public void setSucesso(boolean value) {
        this.sucesso = value;
    }

    /**
     * Gets the value of the mensagem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Sets the value of the mensagem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensagem(String value) {
        this.mensagem = value;
    }

    /**
     * Gets the value of the hashCabecalho property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHashCabecalho() {
        return hashCabecalho;
    }

    /**
     * Sets the value of the hashCabecalho property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHashCabecalho(String value) {
        this.hashCabecalho = value;
    }

    /**
     * Gets the value of the hashMovimentacoes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHashMovimentacoes() {
        return hashMovimentacoes;
    }

    /**
     * Sets the value of the hashMovimentacoes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHashMovimentacoes(String value) {
        this.hashMovimentacoes = value;
    }

    /**
     * Gets the value of the hashDocumentos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHashDocumentos() {
        return hashDocumentos;
    }

    /**
     * Sets the value of the hashDocumentos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHashDocumentos(String value) {
        this.hashDocumentos = value;
    }

}
