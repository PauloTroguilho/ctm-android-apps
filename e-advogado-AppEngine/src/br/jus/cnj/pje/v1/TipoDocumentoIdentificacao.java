
package br.jus.cnj.pje.v1;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipoDocumentoIdentificacao complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tipoDocumentoIdentificacao">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="codigoDocumento" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="emissorDocumento" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tipoDocumento" use="required" type="{http://www.cnj.jus.br/intercomunicacao-2.1}modalidadeDocumentoIdentificador" />
 *       &lt;attribute name="nome" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoDocumentoIdentificacao")
public class TipoDocumentoIdentificacao
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlAttribute(required = true)
    protected String codigoDocumento;
    @XmlAttribute(required = true)
    protected String emissorDocumento;
    @XmlAttribute(required = true)
    protected ModalidadeDocumentoIdentificador tipoDocumento;
    @XmlAttribute
    protected String nome;

    /**
     * Gets the value of the codigoDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    /**
     * Sets the value of the codigoDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoDocumento(String value) {
        this.codigoDocumento = value;
    }

    /**
     * Gets the value of the emissorDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmissorDocumento() {
        return emissorDocumento;
    }

    /**
     * Sets the value of the emissorDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmissorDocumento(String value) {
        this.emissorDocumento = value;
    }

    /**
     * Gets the value of the tipoDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link ModalidadeDocumentoIdentificador }
     *     
     */
    public ModalidadeDocumentoIdentificador getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Sets the value of the tipoDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link ModalidadeDocumentoIdentificador }
     *     
     */
    public void setTipoDocumento(ModalidadeDocumentoIdentificador value) {
        this.tipoDocumento = value;
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

}
