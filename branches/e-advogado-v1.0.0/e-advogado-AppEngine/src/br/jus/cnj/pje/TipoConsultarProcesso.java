
package br.jus.cnj.pje;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for tipoConsultarProcesso complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoConsultarProcesso">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idConsultante" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *         &lt;element name="senhaConsultante" type="{http://www.w3.org/2001/XMLSchema}string" form="qualified"/>
 *         &lt;element name="numeroProcesso" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoNumeroUnico" form="qualified"/>
 *         &lt;element name="dataReferencia" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoDataHora" minOccurs="0" form="qualified"/>
 *         &lt;element name="movimentos" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0" form="qualified"/>
 *         &lt;element name="incluirDocumentos" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0" form="qualified"/>
 *         &lt;element name="documento" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoConsultarProcesso", namespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", propOrder = {
    "idConsultante",
    "senhaConsultante",
    "numeroProcesso",
    "dataReferencia",
    "movimentos",
    "incluirDocumentos",
    "documento"
})
public class TipoConsultarProcesso
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String idConsultante;
    @XmlElement(required = true)
    protected String senhaConsultante;
    @XmlElement(required = true)
    protected String numeroProcesso;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    protected Calendar dataReferencia;
    protected Boolean movimentos;
    protected Boolean incluirDocumentos;
    @XmlElement(nillable = true)
    protected List<String> documento;

    /**
     * Gets the value of the idConsultante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdConsultante() {
        return idConsultante;
    }

    /**
     * Sets the value of the idConsultante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdConsultante(String value) {
        this.idConsultante = value;
    }

    /**
     * Gets the value of the senhaConsultante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenhaConsultante() {
        return senhaConsultante;
    }

    /**
     * Sets the value of the senhaConsultante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenhaConsultante(String value) {
        this.senhaConsultante = value;
    }

    /**
     * Gets the value of the numeroProcesso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    /**
     * Sets the value of the numeroProcesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroProcesso(String value) {
        this.numeroProcesso = value;
    }

    /**
     * Gets the value of the dataReferencia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getDataReferencia() {
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
    public void setDataReferencia(Calendar value) {
        this.dataReferencia = value;
    }

    /**
     * Gets the value of the movimentos property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMovimentos() {
        return movimentos;
    }

    /**
     * Sets the value of the movimentos property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMovimentos(Boolean value) {
        this.movimentos = value;
    }

    /**
     * Gets the value of the incluirDocumentos property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncluirDocumentos() {
        return incluirDocumentos;
    }

    /**
     * Sets the value of the incluirDocumentos property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncluirDocumentos(Boolean value) {
        this.incluirDocumentos = value;
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
     * {@link String }
     * 
     * 
     */
    public List<String> getDocumento() {
        if (documento == null) {
            documento = new ArrayList<String>();
        }
        return this.documento;
    }

}
