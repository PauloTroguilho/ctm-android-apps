package br.com.clebertm.procurados.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.helpers.DefaultValidationEventHandler;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.impl.PropertyManager;
import com.sun.org.apache.xerces.internal.impl.XMLStreamReaderImpl;

/**
 * @author Cleber Moura
 * 
 */
public class MarshallerUtil {

	/**
	 * Cenverte o objeto informado para uma String que representa o XML do
	 * objeto.
	 * 
	 * @param <T>
	 *            Tipo generico que informa a classe
	 * @param object
	 *            O objeto a ser convertido. A classe deste objeto deve conter
	 *            as anotações de JAXB.
	 * @return String que representa o XML do objeto.
	 * @throws JAXBException
	 */
	public static <T> String marshal(T object) throws JAXBException {
		return marshal(object, null);
	}
	
	/**
	 * Cenverte o objeto informado para uma String que representa o XML do
	 * objeto.
	 * 
	 * @param <T>
	 *            Tipo generico que informa a classe
	 * @param object
	 *            O objeto a ser convertido. A classe deste objeto deve conter
	 *            as anotações de JAXB.
	 * @param schemaLocation
	 * 			{@link URL} do schema.
	 * @return
	 * @throws JAXBException
	 */
	@SuppressWarnings("unchecked")
	public static <T> String marshal(T object, URL schemaLocation) throws JAXBException {
		Class<T> objClass = (Class<T>) object.getClass();
		JAXBContext context = JAXBContext.newInstance(objClass);
		Marshaller marshaller = context.createMarshaller();
		StringWriter sWriter = new StringWriter();
		XmlSchema xmlSchema = objClass.getPackage().getAnnotation(
				XmlSchema.class);
		if (xmlSchema != null && xmlSchema.namespace() != null) {
			XmlType xmlType = objClass.getAnnotation(XmlType.class);
			if (xmlType != null && xmlType.name() != null) {
				QName qName = new QName(xmlSchema.namespace(), xmlType.name());
				JAXBElement<T> elem = new JAXBElement<T>(qName, objClass,
						object);
				if (schemaLocation != null) {
					SchemaFactory schemaFactory = SchemaFactory.newInstance(
							XMLConstants.W3C_XML_SCHEMA_NS_URI);
					try {
						Schema schema = schemaFactory.newSchema(schemaLocation);
						marshaller.setSchema(schema);
					} catch (SAXException e) {
						e.printStackTrace();
					}
				}
				marshaller.marshal(elem, sWriter);
				return sWriter.toString();
			} else {
				throw new JAXBException(
						"The xmlType could not be identified in class annotation");
			}
		} else {
			throw new JAXBException(
					"The namespace could not be identified from package-info class");
		}
	}

	/**
	 * Cenverte a string fornecida para um objeto do tipo da classe informada.
	 * Utiliza o {@link DefaultValidationEventHandler} para validação do XML com o schema.
	 * 
	 * @param <T>
	 *            Tipo generico que informa a classe
	 * @param objString
	 *            String que representa o objeto em XML
	 * @param clazz
	 *            Classe do objeto.
	 * @return Objeto.
	 * @throws JAXBException
	 * @throws XMLStreamException
	 */
	public static <T> T unmarshal(String objString, Class<T> clazz)
			throws JAXBException, XMLStreamException {
		return unmarshal(objString, clazz, new DefaultValidationEventHandler());
	}

	/**
	 * Cenverte a string fornecida para um objeto do tipo da classe informada.
	 * @param <T>
	 * @param objString
	 * @param clazz
	 * @param validationEventHandler
	 * 		Utilizado para customizar as mensagens de erro na validação do XML com o Schema.
	 * @return
	 * @throws JAXBException
	 * @throws XMLStreamException
	 */
	public static <T> T unmarshal(String objString, Class<T> clazz, 
			ValidationEventHandler validationEventHandler) throws JAXBException, XMLStreamException {
		return unmarshal(objString, clazz, validationEventHandler, null);
	}
	
	/**
	 * @param <T>
	 * @param objString
	 * @param clazz
	 * @param validationEventHandler
	 * @param schemaLocation
	 * @return
	 * @throws JAXBException
	 * @throws XMLStreamException
	 */
	public static <T> T unmarshal(String objString, Class<T> clazz, 
			ValidationEventHandler validationEventHandler, URL schemaLocation) throws JAXBException, XMLStreamException {
		JAXBContext jc = JAXBContext.newInstance(clazz.getPackage().getName());
		XMLStreamReader streamReader = new XMLStreamReaderImpl(
				new StringReader(objString), new PropertyManager(
						PropertyManager.CONTEXT_READER));
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setEventHandler(validationEventHandler);
		if (schemaLocation != null) {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(
					XMLConstants.W3C_XML_SCHEMA_NS_URI);
			try {
				Schema schema = schemaFactory.newSchema(schemaLocation);
				unmarshaller.setSchema(schema);
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}
		JAXBElement<T> element = (JAXBElement<T>) unmarshaller.unmarshal(
				streamReader, clazz);
		return element.getValue();
	}
}
