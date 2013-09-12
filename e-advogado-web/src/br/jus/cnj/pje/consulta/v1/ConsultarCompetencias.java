
package br.jus.cnj.pje.consulta.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consultarCompetencias complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consultarCompetencias">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://ws.pje.cnj.jus.br/}jurisdicao" minOccurs="0"/>
 *         &lt;element name="arg1" type="{http://ws.pje.cnj.jus.br/}classeJudicial" minOccurs="0"/>
 *         &lt;element name="arg2" type="{http://ws.pje.cnj.jus.br/}assuntoJudicial" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarCompetencias", propOrder = {
    "arg0",
    "arg1",
    "arg2"
})
public class ConsultarCompetencias
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected Jurisdicao arg0;
    protected ClasseJudicial arg1;
    @XmlElement(nillable = true)
    protected List<AssuntoJudicial> arg2;

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

    /**
     * Gets the value of the arg1 property.
     * 
     * @return
     *     possible object is
     *     {@link ClasseJudicial }
     *     
     */
    public ClasseJudicial getArg1() {
        return arg1;
    }

    /**
     * Sets the value of the arg1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClasseJudicial }
     *     
     */
    public void setArg1(ClasseJudicial value) {
        this.arg1 = value;
    }

    /**
     * Gets the value of the arg2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the arg2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArg2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AssuntoJudicial }
     * 
     * 
     */
    public List<AssuntoJudicial> getArg2() {
        if (arg2 == null) {
            arg2 = new ArrayList<AssuntoJudicial>();
        }
        return this.arg2;
    }

}
