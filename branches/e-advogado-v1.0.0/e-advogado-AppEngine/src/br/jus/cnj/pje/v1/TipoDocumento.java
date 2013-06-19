
package br.jus.cnj.pje.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3c.dom.Element;


/**
 * <p>Java class for tipoDocumento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoDocumento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="conteudo" type="{http://www.w3.org/2001/XMLSchema}hexBinary" minOccurs="0"/>
 *         &lt;element name="assinatura" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoAssinatura" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="outroParametro" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoParametro" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;any processContents='lax' namespace='##other'/>
 *         &lt;element name="documentoVinculado" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoDocumento" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="idDocumento" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="idDocumentoVinculado" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tipoDocumento" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="dataHora" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoDataHora" />
 *       &lt;attribute name="mimetype" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="nivelSigilo" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="movimento" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="hash" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoDocumento", propOrder = {
    "conteudo",
    "assinatura",
    "outroParametro",
    "any",
    "documentoVinculado"
})
public class TipoDocumento
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] conteudo;
    @XmlElement(nillable = true)
    protected List<TipoAssinatura> assinatura;
    @XmlElement(nillable = true)
    protected List<TipoParametro> outroParametro;
    @XmlAnyElement(lax = true)
    protected Object any;
    @XmlElement(nillable = true)
    protected List<TipoDocumento> documentoVinculado;
    @XmlAttribute
    protected String idDocumento;
    @XmlAttribute
    protected String idDocumentoVinculado;
    @XmlAttribute(required = true)
    protected String tipoDocumento;
    @XmlAttribute
    protected String dataHora;
    @XmlAttribute
    protected String mimetype;
    @XmlAttribute
    protected Integer nivelSigilo;
    @XmlAttribute
    protected Integer movimento;
    @XmlAttribute
    protected String hash;

    /**
     * Gets the value of the conteudo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getConteudo() {
        return conteudo;
    }

    /**
     * Sets the value of the conteudo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConteudo(byte[] value) {
        this.conteudo = ((byte[]) value);
    }

    /**
     * Gets the value of the assinatura property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the assinatura property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssinatura().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipoAssinatura }
     * 
     * 
     */
    public List<TipoAssinatura> getAssinatura() {
        if (assinatura == null) {
            assinatura = new ArrayList<TipoAssinatura>();
        }
        return this.assinatura;
    }

    /**
     * Gets the value of the outroParametro property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the outroParametro property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutroParametro().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipoParametro }
     * 
     * 
     */
    public List<TipoParametro> getOutroParametro() {
        if (outroParametro == null) {
            outroParametro = new ArrayList<TipoParametro>();
        }
        return this.outroParametro;
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
     * Gets the value of the documentoVinculado property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentoVinculado property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentoVinculado().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipoDocumento }
     * 
     * 
     */
    public List<TipoDocumento> getDocumentoVinculado() {
        if (documentoVinculado == null) {
            documentoVinculado = new ArrayList<TipoDocumento>();
        }
        return this.documentoVinculado;
    }

    /**
     * Gets the value of the idDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdDocumento() {
        return idDocumento;
    }

    /**
     * Sets the value of the idDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdDocumento(String value) {
        this.idDocumento = value;
    }

    /**
     * Gets the value of the idDocumentoVinculado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdDocumentoVinculado() {
        return idDocumentoVinculado;
    }

    /**
     * Sets the value of the idDocumentoVinculado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdDocumentoVinculado(String value) {
        this.idDocumentoVinculado = value;
    }

    /**
     * Gets the value of the tipoDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Sets the value of the tipoDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    /**
     * Gets the value of the dataHora property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataHora() {
        return dataHora;
    }

    /**
     * Sets the value of the dataHora property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataHora(String value) {
        this.dataHora = value;
    }

    /**
     * Gets the value of the mimetype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimetype() {
        return mimetype;
    }

    /**
     * Sets the value of the mimetype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimetype(String value) {
        this.mimetype = value;
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

    /**
     * Gets the value of the movimento property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMovimento() {
        return movimento;
    }

    /**
     * Sets the value of the movimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMovimento(Integer value) {
        this.movimento = value;
    }

    /**
     * Gets the value of the hash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHash() {
        return hash;
    }

    /**
     * Sets the value of the hash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHash(String value) {
        this.hash = value;
    }

}
