
package br.jus.cnj.pje.consulta.v1;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for recuperarInformacoesFluxoResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recuperarInformacoesFluxoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://ws.pje.cnj.jus.br/}fluxo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recuperarInformacoesFluxoResponse", propOrder = {
    "_return"
})
public class RecuperarInformacoesFluxoResponse
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "return")
    protected Fluxo _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link Fluxo }
     *     
     */
    public Fluxo getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link Fluxo }
     *     
     */
    public void setReturn(Fluxo value) {
        this._return = value;
    }

}
