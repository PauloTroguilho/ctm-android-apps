
package br.jus.cnj.pje.consulta.v1;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ConsultaPJeService", targetNamespace = "http://ws.pje.cnj.jus.br/", wsdlLocation = "/META-INF/ConsultaPJe.wsdl")
public class ConsultaPJeService
    extends Service
{

    private final static URL CONSULTAPJESERVICE_WSDL_LOCATION;
    private final static WebServiceException CONSULTAPJESERVICE_EXCEPTION;
    private final static QName CONSULTAPJESERVICE_QNAME = new QName("http://ws.pje.cnj.jus.br/", "ConsultaPJeService");

    static {
        CONSULTAPJESERVICE_WSDL_LOCATION = br.jus.cnj.pje.consulta.v1.ConsultaPJeService.class.getResource("/META-INF/ConsultaPJe.wsdl");
        WebServiceException e = null;
        if (CONSULTAPJESERVICE_WSDL_LOCATION == null) {
            e = new WebServiceException("Cannot find '/META-INF/ConsultaPJe.wsdl' wsdl. Place the resource correctly in the classpath.");
        }
        CONSULTAPJESERVICE_EXCEPTION = e;
    }

    public ConsultaPJeService() {
        super(__getWsdlLocation(), CONSULTAPJESERVICE_QNAME);
    }

    public ConsultaPJeService(WebServiceFeature... features) {
        super(__getWsdlLocation(), CONSULTAPJESERVICE_QNAME, features);
    }

    public ConsultaPJeService(URL wsdlLocation) {
        super(wsdlLocation, CONSULTAPJESERVICE_QNAME);
    }

    public ConsultaPJeService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CONSULTAPJESERVICE_QNAME, features);
    }

    public ConsultaPJeService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ConsultaPJeService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ConsultaPJe
     */
    @WebEndpoint(name = "ConsultaPJePort")
    public ConsultaPJe getConsultaPJePort() {
        return super.getPort(new QName("http://ws.pje.cnj.jus.br/", "ConsultaPJePort"), ConsultaPJe.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ConsultaPJe
     */
    @WebEndpoint(name = "ConsultaPJePort")
    public ConsultaPJe getConsultaPJePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.pje.cnj.jus.br/", "ConsultaPJePort"), ConsultaPJe.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CONSULTAPJESERVICE_EXCEPTION!= null) {
            throw CONSULTAPJESERVICE_EXCEPTION;
        }
        return CONSULTAPJESERVICE_WSDL_LOCATION;
    }

}