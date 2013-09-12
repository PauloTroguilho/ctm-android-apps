
package br.jus.cnj.pje.consulta.v1;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consultarSalasAudienciaOrgaoJulgador complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consultarSalasAudienciaOrgaoJulgador">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orgaoJulgador" type="{http://ws.pje.cnj.jus.br/}orgaoJulgador" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarSalasAudienciaOrgaoJulgador", propOrder = {
    "orgaoJulgador"
})
public class ConsultarSalasAudienciaOrgaoJulgador
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected OrgaoJulgador orgaoJulgador;

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

}
