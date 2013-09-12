
package br.jus.cnj.pje.consulta.v1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for diaSemanaEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="diaSemanaEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DOMINGO"/>
 *     &lt;enumeration value="SEGUNDA"/>
 *     &lt;enumeration value="TERCA"/>
 *     &lt;enumeration value="QUARTA"/>
 *     &lt;enumeration value="QUINTA"/>
 *     &lt;enumeration value="SEXTA"/>
 *     &lt;enumeration value="SABADO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "diaSemanaEnum")
@XmlEnum
public enum DiaSemanaEnum {

    DOMINGO,
    SEGUNDA,
    TERCA,
    QUARTA,
    QUINTA,
    SEXTA,
    SABADO;

    public String value() {
        return name();
    }

    public static DiaSemanaEnum fromValue(String v) {
        return valueOf(v);
    }

}
