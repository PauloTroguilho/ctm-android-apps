
package br.jus.cnj.pje.v1;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoVinculacaoProcessual complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoVinculacaoProcessual">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="numeroProcesso" use="required" type="{http://www.cnj.jus.br/intercomunicacao-2.1}tipoNumeroUnico" />
 *       &lt;attribute name="vinculo" use="required" type="{http://www.cnj.jus.br/intercomunicacao-2.1}modalidadeVinculacaoProcesso" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoVinculacaoProcessual")
public class TipoVinculacaoProcessual
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(required = true)
    protected String numeroProcesso;
    @XmlAttribute(required = true)
    protected ModalidadeVinculacaoProcesso vinculo;

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
     * Gets the value of the vinculo property.
     * 
     * @return
     *     possible object is
     *     {@link ModalidadeVinculacaoProcesso }
     *     
     */
    public ModalidadeVinculacaoProcesso getVinculo() {
        return vinculo;
    }

    /**
     * Sets the value of the vinculo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModalidadeVinculacaoProcesso }
     *     
     */
    public void setVinculo(ModalidadeVinculacaoProcesso value) {
        this.vinculo = value;
    }

}
