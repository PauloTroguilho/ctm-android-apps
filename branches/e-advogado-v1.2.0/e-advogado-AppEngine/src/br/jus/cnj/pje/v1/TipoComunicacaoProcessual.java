
package br.jus.cnj.pje.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for tipoComunicacaoProcessual complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoComunicacaoProcessual">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="destinatario" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoParte"/>
 *         &lt;element name="processo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="teor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="documento" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoDocumento" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="parametro" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;any processContents='lax' namespace='##other'/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.cnj.jus.br/intercomunicacao-2.1}identificadorComunicacao" />
 *       &lt;attribute name="tipoComunicacao" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoComunicacao" />
 *       &lt;attribute name="tipoPrazo" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoPrazo" />
 *       &lt;attribute name="dataReferencia" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoDataHora" />
 *       &lt;attribute name="prazo" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="nivelSigilo" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoComunicacaoProcessual", propOrder = {
    "destinatario",
    "processo",
    "teor",
    "documento",
    "parametro",
    "any"
})
public class TipoComunicacaoProcessual
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected TipoParte destinatario;
    @XmlElement(required = true)
    protected String processo;
    protected String teor;
    @XmlElement(nillable = true)
    protected List<TipoDocumento> documento;
    @XmlElement(nillable = true)
    protected List<String> parametro;
    @XmlAnyElement(lax = true)
    protected Object any;
    @XmlAttribute(required = true)
    protected String id;
    @XmlAttribute
    protected String tipoComunicacao;
    @XmlAttribute
    protected TipoPrazo tipoPrazo;
    @XmlAttribute
    protected String dataReferencia;
    @XmlAttribute
    protected Integer prazo;
    @XmlAttribute
    protected Integer nivelSigilo;

    /**
     * Gets the value of the destinatario property.
     * 
     * @return
     *     possible object is
     *     {@link TipoParte }
     *     
     */
    public TipoParte getDestinatario() {
        return destinatario;
    }

    /**
     * Sets the value of the destinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoParte }
     *     
     */
    public void setDestinatario(TipoParte value) {
        this.destinatario = value;
    }

    /**
     * Gets the value of the processo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcesso() {
        return processo;
    }

    /**
     * Sets the value of the processo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcesso(String value) {
        this.processo = value;
    }

    /**
     * Gets the value of the teor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeor() {
        return teor;
    }

    /**
     * Sets the value of the teor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeor(String value) {
        this.teor = value;
    }

    /**
     * Gets the value of the documento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipoDocumento }
     * 
     * 
     */
    public List<TipoDocumento> getDocumento() {
        if (documento == null) {
            documento = new ArrayList<TipoDocumento>();
        }
        return this.documento;
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
     * {@link String }
     * 
     * 
     */
    public List<String> getParametro() {
        if (parametro == null) {
            parametro = new ArrayList<String>();
        }
        return this.parametro;
    }

    /**
     * Gets the value of the any property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     {@link Element }
     *     
     */
    public Object getAny() {
        return any;
    }

    /**
     * Sets the value of the any property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     {@link Element }
     *     
     */
    public void setAny(Object value) {
        this.any = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the tipoComunicacao property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoComunicacao() {
        return tipoComunicacao;
    }

    /**
     * Sets the value of the tipoComunicacao property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoComunicacao(String value) {
        this.tipoComunicacao = value;
    }

    /**
     * Gets the value of the tipoPrazo property.
     * 
     * @return
     *     possible object is
     *     {@link TipoPrazo }
     *     
     */
    public TipoPrazo getTipoPrazo() {
        return tipoPrazo;
    }

    /**
     * Sets the value of the tipoPrazo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoPrazo }
     *     
     */
    public void setTipoPrazo(TipoPrazo value) {
        this.tipoPrazo = value;
    }

    /**
     * Gets the value of the dataReferencia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataReferencia() {
        return dataReferencia;
    }

    /**
     * Sets the value of the dataReferencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataReferencia(String value) {
        this.dataReferencia = value;
    }

    /**
     * Gets the value of the prazo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPrazo() {
        return prazo;
    }

    /**
     * Sets the value of the prazo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPrazo(Integer value) {
        this.prazo = value;
    }

    /**
     * Gets the value of the nivelSigilo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNivelSigilo() {
        return nivelSigilo;
    }

    /**
     * Sets the value of the nivelSigilo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNivelSigilo(Integer value) {
        this.nivelSigilo = value;
    }

}
