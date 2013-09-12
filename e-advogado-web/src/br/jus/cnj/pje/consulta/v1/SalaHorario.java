
package br.jus.cnj.pje.consulta.v1;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for salaHorario complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="salaHorario">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="diaSemama" type="{http://ws.pje.cnj.jus.br/}diaSemanaEnum" minOccurs="0"/>
 *         &lt;element name="horaFinal" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="horaInicial" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "salaHorario", propOrder = {
    "diaSemama",
    "horaFinal",
    "horaInicial"
})
public class SalaHorario
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected DiaSemanaEnum diaSemama;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar horaFinal;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar horaInicial;

    /**
     * Gets the value of the diaSemama property.
     * 
     * @return
     *     possible object is
     *     {@link DiaSemanaEnum }
     *     
     */
    public DiaSemanaEnum getDiaSemama() {
        return diaSemama;
    }

    /**
     * Sets the value of the diaSemama property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiaSemanaEnum }
     *     
     */
    public void setDiaSemama(DiaSemanaEnum value) {
        this.diaSemama = value;
    }

    /**
     * Gets the value of the horaFinal property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getHoraFinal() {
        return horaFinal;
    }

    /**
     * Sets the value of the horaFinal property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setHoraFinal(XMLGregorianCalendar value) {
        this.horaFinal = value;
    }

    /**
     * Gets the value of the horaInicial property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getHoraInicial() {
        return horaInicial;
    }

    /**
     * Sets the value of the horaInicial property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setHoraInicial(XMLGregorianCalendar value) {
        this.horaInicial = value;
    }

}
