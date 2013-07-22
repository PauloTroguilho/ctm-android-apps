
package br.jus.cnj.pje.v1;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for tipoAssinatura complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoAssinatura">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="assinatura" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="dataAssinatura" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="cadeiaCertificado" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="algoritmoHash" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoAssinatura", propOrder = {
    "value"
})
public class TipoAssinatura
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlValue
    protected String value;
    @XmlAttribute
    protected String assinatura;
    @XmlAttribute
    protected String dataAssinatura;
    @XmlAttribute
    protected String cadeiaCertificado;
    @XmlAttribute
    protected String algoritmoHash;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the assinatura property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssinatura() {
        return assinatura;
    }

    /**
     * Sets the value of the assinatura property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssinatura(String value) {
        this.assinatura = value;
    }

    /**
     * Gets the value of the dataAssinatura property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataAssinatura() {
        return dataAssinatura;
    }

    /**
     * Sets the value of the dataAssinatura property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataAssinatura(String value) {
        this.dataAssinatura = value;
    }

    /**
     * Gets the value of the cadeiaCertificado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCadeiaCertificado() {
        return cadeiaCertificado;
    }

    /**
     * Sets the value of the cadeiaCertificado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCadeiaCertificado(String value) {
        this.cadeiaCertificado = value;
    }

    /**
     * Gets the value of the algoritmoHash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlgoritmoHash() {
        return algoritmoHash;
    }

    /**
     * Sets the value of the algoritmoHash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlgoritmoHash(String value) {
        this.algoritmoHash = value;
    }

}
