
package br.jus.cnj.pje.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoEntregarManifestacaoProcessualResposta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoEntregarManifestacaoProcessualResposta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sucesso" type="{http://www.w3.org/2001/XMLSchema}boolean" form="qualified"/>
 *         &lt;element name="mensagem" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *         &lt;element name="protocoloRecebimento" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *         &lt;element name="dataOperacao" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoDataHora" form="qualified"/>
 *         &lt;element name="recibo" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0" form="qualified"/>
 *         &lt;element name="parametro" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoParametro" maxOccurs="unbounded" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoEntregarManifestacaoProcessualResposta", namespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", propOrder = {
    "sucesso",
    "mensagem",
    "protocoloRecebimento",
    "dataOperacao",
    "recibo",
    "parametro"
})
public class TipoEntregarManifestacaoProcessualResposta
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected boolean sucesso;
    @XmlElement(required = true)
    protected String mensagem;
    @XmlElement(required = true)
    protected String protocoloRecebimento;
    @XmlElement(required = true)
    protected String dataOperacao;
    protected byte[] recibo;
    @XmlElement(nillable = true)
    protected List<TipoParametro> parametro;

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
     * Gets the value of the protocoloRecebimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocoloRecebimento() {
        return protocoloRecebimento;
    }

    /**
     * Sets the value of the protocoloRecebimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocoloRecebimento(String value) {
        this.protocoloRecebimento = value;
    }

    /**
     * Gets the value of the dataOperacao property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataOperacao() {
        return dataOperacao;
    }

    /**
     * Sets the value of the dataOperacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataOperacao(String value) {
        this.dataOperacao = value;
    }

    /**
     * Gets the value of the recibo property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getRecibo() {
        return recibo;
    }

    /**
     * Sets the value of the recibo property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setRecibo(byte[] value) {
        this.recibo = ((byte[]) value);
    }

    /**
     * Gets the value of the parametro property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parametro property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParametro().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipoParametro }
     * 
     * 
     */
    public List<TipoParametro> getParametro() {
        if (parametro == null) {
            parametro = new ArrayList<TipoParametro>();
        }
        return this.parametro;
    }

}
