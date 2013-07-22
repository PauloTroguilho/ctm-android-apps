
package br.jus.cnj.pje.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoEntregarManifestacaoProcessual complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoEntregarManifestacaoProcessual">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idManifestante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="senhaManifestante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="qualified"/>
 *         &lt;element name="numeroProcesso" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoNumeroUnico" minOccurs="0" form="qualified"/>
 *         &lt;element name="dadosBasicos" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoCabecalhoProcesso" minOccurs="0" form="qualified"/>
 *         &lt;element name="documento" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoDocumento" maxOccurs="unbounded" form="qualified"/>
 *         &lt;element name="dataEnvio" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoDataHora" form="qualified"/>
 *         &lt;element name="parametros" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoParametro" maxOccurs="unbounded" minOccurs="0" form="qualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoEntregarManifestacaoProcessual", namespace = "http://www.cnj.jus.br/tipos-servico-intercomunicacao-2.1", propOrder = {
    "idManifestante",
    "senhaManifestante",
    "numeroProcesso",
    "dadosBasicos",
    "documento",
    "dataEnvio",
    "parametros"
})
public class TipoEntregarManifestacaoProcessual
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected String idManifestante;
    protected String senhaManifestante;
    protected String numeroProcesso;
    protected TipoCabecalhoProcesso dadosBasicos;
    @XmlElement(required = true)
    protected List<TipoDocumento> documento;
    @XmlElement(required = true)
    protected String dataEnvio;
    @XmlElement(nillable = true)
    protected List<TipoParametro> parametros;

    /**
     * Gets the value of the idManifestante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdManifestante() {
        return idManifestante;
    }

    /**
     * Sets the value of the idManifestante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdManifestante(String value) {
        this.idManifestante = value;
    }

    /**
     * Gets the value of the senhaManifestante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenhaManifestante() {
        return senhaManifestante;
    }

    /**
     * Sets the value of the senhaManifestante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenhaManifestante(String value) {
        this.senhaManifestante = value;
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
     * Gets the value of the dadosBasicos property.
     * 
     * @return
     *     possible object is
     *     {@link TipoCabecalhoProcesso }
     *     
     */
    public TipoCabecalhoProcesso getDadosBasicos() {
        return dadosBasicos;
    }

    /**
     * Sets the value of the dadosBasicos property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoCabecalhoProcesso }
     *     
     */
    public void setDadosBasicos(TipoCabecalhoProcesso value) {
        this.dadosBasicos = value;
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
     * Gets the value of the dataEnvio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataEnvio() {
        return dataEnvio;
    }

    /**
     * Sets the value of the dataEnvio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataEnvio(String value) {
        this.dataEnvio = value;
    }

    /**
     * Gets the value of the parametros property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parametros property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParametros().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipoParametro }
     * 
     * 
     */
    public List<TipoParametro> getParametros() {
        if (parametros == null) {
            parametros = new ArrayList<TipoParametro>();
        }
        return this.parametros;
    }

}
