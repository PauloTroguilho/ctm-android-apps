//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.01 at 06:36:33 PM GMT-03:00 
//


package br.com.clebertm.procurados.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for procurado complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="procurado">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apelido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fotoId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="historico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroProcesso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="juizCompetente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comarca" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataExpedicaoMandado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataNascimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "procurado", propOrder = {
    "nome",
    "apelido",
    "fotoId",
    "historico",
    "numeroProcesso",
    "juizCompetente",
    "comarca",
    "dataExpedicaoMandado",
    "dataNascimento",
    "rg"
})
public class Procurado {

    protected String nome;
    protected String apelido;
    protected String fotoId;
    protected String historico;
    protected String numeroProcesso;
    protected String juizCompetente;
    protected String comarca;
    protected String dataExpedicaoMandado;
    protected String dataNascimento;
    protected String rg;

    /**
     * Gets the value of the nome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets the value of the nome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Gets the value of the apelido property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApelido() {
        return apelido;
    }

    /**
     * Sets the value of the apelido property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApelido(String value) {
        this.apelido = value;
    }

    /**
     * Gets the value of the fotoId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFotoId() {
        return fotoId;
    }

    /**
     * Sets the value of the fotoId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFotoId(String value) {
        this.fotoId = value;
    }

    /**
     * Gets the value of the historico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHistorico() {
        return historico;
    }

    /**
     * Sets the value of the historico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHistorico(String value) {
        this.historico = value;
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
     * Gets the value of the juizCompetente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJuizCompetente() {
        return juizCompetente;
    }

    /**
     * Sets the value of the juizCompetente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJuizCompetente(String value) {
        this.juizCompetente = value;
    }

    /**
     * Gets the value of the comarca property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComarca() {
        return comarca;
    }

    /**
     * Sets the value of the comarca property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComarca(String value) {
        this.comarca = value;
    }

    /**
     * Gets the value of the dataExpedicaoMandado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataExpedicaoMandado() {
        return dataExpedicaoMandado;
    }

    /**
     * Sets the value of the dataExpedicaoMandado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataExpedicaoMandado(String value) {
        this.dataExpedicaoMandado = value;
    }

    /**
     * Gets the value of the dataNascimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataNascimento() {
        return dataNascimento;
    }

    /**
     * Sets the value of the dataNascimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataNascimento(String value) {
        this.dataNascimento = value;
    }

    /**
     * Gets the value of the rg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRg() {
        return rg;
    }

    /**
     * Sets the value of the rg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRg(String value) {
        this.rg = value;
    }

}
