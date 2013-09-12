
package br.jus.cnj.pje.consulta.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for salaAudiencia complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="salaAudiencia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ignoraFeriado" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orgaoJulgador" type="{http://ws.pje.cnj.jus.br/}orgaoJulgador" minOccurs="0"/>
 *         &lt;element name="salaHorarioList" type="{http://ws.pje.cnj.jus.br/}salaHorario" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tipoAudienciaList" type="{http://ws.pje.cnj.jus.br/}tipoAudiencia" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tipoSala" type="{http://ws.pje.cnj.jus.br/}salaEnum" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "salaAudiencia", propOrder = {
    "id",
    "ignoraFeriado",
    "nome",
    "orgaoJulgador",
    "salaHorarioList",
    "tipoAudienciaList",
    "tipoSala"
})
public class SalaAudiencia
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected Integer id;
    protected Boolean ignoraFeriado;
    protected String nome;
    protected OrgaoJulgador orgaoJulgador;
    @XmlElement(nillable = true)
    protected List<SalaHorario> salaHorarioList;
    @XmlElement(nillable = true)
    protected List<TipoAudiencia> tipoAudienciaList;
    protected SalaEnum tipoSala;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the ignoraFeriado property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIgnoraFeriado() {
        return ignoraFeriado;
    }

    /**
     * Sets the value of the ignoraFeriado property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIgnoraFeriado(Boolean value) {
        this.ignoraFeriado = value;
    }

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
     * Gets the value of the orgaoJulgador property.
     * 
     * @return
     *     possible object is
     *     {@link OrgaoJulgador }
     *     
     */
    public OrgaoJulgador getOrgaoJulgador() {
        return orgaoJulgador;
    }

    /**
     * Sets the value of the orgaoJulgador property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrgaoJulgador }
     *     
     */
    public void setOrgaoJulgador(OrgaoJulgador value) {
        this.orgaoJulgador = value;
    }

    /**
     * Gets the value of the salaHorarioList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the salaHorarioList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSalaHorarioList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SalaHorario }
     * 
     * 
     */
    public List<SalaHorario> getSalaHorarioList() {
        if (salaHorarioList == null) {
            salaHorarioList = new ArrayList<SalaHorario>();
        }
        return this.salaHorarioList;
    }

    /**
     * Gets the value of the tipoAudienciaList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tipoAudienciaList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTipoAudienciaList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipoAudiencia }
     * 
     * 
     */
    public List<TipoAudiencia> getTipoAudienciaList() {
        if (tipoAudienciaList == null) {
            tipoAudienciaList = new ArrayList<TipoAudiencia>();
        }
        return this.tipoAudienciaList;
    }

    /**
     * Gets the value of the tipoSala property.
     * 
     * @return
     *     possible object is
     *     {@link SalaEnum }
     *     
     */
    public SalaEnum getTipoSala() {
        return tipoSala;
    }

    /**
     * Sets the value of the tipoSala property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalaEnum }
     *     
     */
    public void setTipoSala(SalaEnum value) {
        this.tipoSala = value;
    }

}
