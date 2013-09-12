
package br.jus.cnj.pje.consulta.v1;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consultarClassesJudiciais complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consultarClassesJudiciais">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://ws.pje.cnj.jus.br/}jurisdicao" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarClassesJudiciais", propOrder = {
    "arg0"
})
public class ConsultarClassesJudiciais
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected Jurisdicao arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link Jurisdicao }
     *     
     */
    public Jurisdicao getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Jurisdicao }
     *     
     */
    public void setArg0(Jurisdicao value) {
        this.arg0 = value;
    }

}
