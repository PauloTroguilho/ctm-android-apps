//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.04 at 11:29:57 AM GMT-03:00 
//


package br.com.clebertm.procurados.types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.com.clebertm.procurados.types package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Procurados_QNAME = new QName("http://procurados.clebertm.com.br/v01", "procurados");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.com.clebertm.procurados.types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Procurados }
     * 
     */
    public Procurados createProcurados() {
        return new Procurados();
    }

    /**
     * Create an instance of {@link Procurado }
     * 
     */
    public Procurado createProcurado() {
        return new Procurado();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Procurados }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://procurados.clebertm.com.br/v01", name = "procurados")
    public JAXBElement<Procurados> createProcurados(Procurados value) {
        return new JAXBElement<Procurados>(_Procurados_QNAME, Procurados.class, null, value);
    }

}
