
package br.jus.cnj.pje.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoRepresentanteProcessual complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoRepresentanteProcessual">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="endereco" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoEndereco" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="nome" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="inscricao" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoCadastroOAB" />
 *       &lt;attribute name="numeroDocumentoPrincipal" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="intimacao" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="tipoRepresentante" use="required" type="{http://www.cnj.jus.br/intercomunicacao-2.1}modalidadeRepresentanteProcessual" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoRepresentanteProcessual", propOrder = {
    "endereco"
})
public class TipoRepresentanteProcessual
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(nillable = true)
    protected List<TipoEndereco> endereco;
    @XmlAttribute(required = true)
    protected String nome;
    @XmlAttribute
    protected String inscricao;
    @XmlAttribute
    protected String numeroDocumentoPrincipal;
    @XmlAttribute(required = true)
    protected boolean intimacao;
    @XmlAttribute(required = true)
    protected ModalidadeRepresentanteProcessual tipoRepresentante;

    /**
     * Gets the value of the endereco property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the endereco property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEndereco().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TipoEndereco }
     * 
     * 
     */
    public List<TipoEndereco> getEndereco() {
        if (endereco == null) {
            endereco = new ArrayList<TipoEndereco>();
        }
        return this.endereco;
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
     * Gets the value of the inscricao property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInscricao() {
        return inscricao;
    }

    /**
     * Sets the value of the inscricao property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInscricao(String value) {
        this.inscricao = value;
    }

    /**
     * Gets the value of the numeroDocumentoPrincipal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroDocumentoPrincipal() {
        return numeroDocumentoPrincipal;
    }

    /**
     * Sets the value of the numeroDocumentoPrincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroDocumentoPrincipal(String value) {
        this.numeroDocumentoPrincipal = value;
    }

    /**
     * Gets the value of the intimacao property.
     * 
     */
    public boolean isIntimacao() {
        return intimacao;
    }

    /**
     * Sets the value of the intimacao property.
     * 
     */
    public void setIntimacao(boolean value) {
        this.intimacao = value;
    }

    /**
     * Gets the value of the tipoRepresentante property.
     * 
     * @return
     *     possible object is
     *     {@link ModalidadeRepresentanteProcessual }
     *     
     */
    public ModalidadeRepresentanteProcessual getTipoRepresentante() {
        return tipoRepresentante;
    }

    /**
     * Sets the value of the tipoRepresentante property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModalidadeRepresentanteProcessual }
     *     
     */
    public void setTipoRepresentante(ModalidadeRepresentanteProcessual value) {
        this.tipoRepresentante = value;
    }

}
