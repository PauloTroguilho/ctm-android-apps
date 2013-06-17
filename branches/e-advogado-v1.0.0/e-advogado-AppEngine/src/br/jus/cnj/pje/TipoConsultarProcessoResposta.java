
package br.jus.cnj.pje;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoConsultarProcessoResposta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoConsultarProcessoResposta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sucesso" type="{http://www.w3.org/2001/XMLSchema}boolean" form="qualified"/>
 *         &lt;element name="mensagem" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *         &lt;element name="processo" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoProcessoJudicial" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoConsultarProcessoResposta", namespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", propOrder = {
    "sucesso",
    "mensagem",
    "processo"
})
public class TipoConsultarProcessoResposta
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected boolean sucesso;
    @XmlElement(required = true)
    protected String mensagem;
    protected TipoProcessoJudicial processo;

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
     * Gets the value of the processo property.
     * 
     * @return
     *     possible object is
     *     {@link TipoProcessoJudicial }
     *     
     */
    public TipoProcessoJudicial getProcesso() {
        return processo;
    }

    /**
     * Sets the value of the processo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoProcessoJudicial }
     *     
     */
    public void setProcesso(TipoProcessoJudicial value) {
        this.processo = value;
    }

}
