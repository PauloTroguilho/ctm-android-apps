package br.com.clebertm.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;
import br.com.clebertm.domain.Procurado;
import br.com.clebertm.domain.Procurados;

public class ProcuradosXmlHandler extends DefaultHandler {

	private Boolean currentElement = false;
	private StringBuffer currentValueBuf;
	
	private Procurado currentProcurado;
	private Procurados procurados;
	
	private int cont = 0;
	
	public Procurados getProcurados() {
		return procurados;
	}

	/**
	 * Called when tag starts ( ex:- <name>AndroidPeople</name> -- <name> )
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;
		currentValueBuf = new StringBuffer();

		if (localName.equalsIgnoreCase("procurados")) {
			/** Start */
			procurados = new Procurados();
		} else if (localName.equalsIgnoreCase("procurado")) {
			currentProcurado = new Procurado();
			currentProcurado.setId(++cont);
		} else if (localName.equalsIgnoreCase("detalheUrl")) {
			Log.i("XmlHandler", "tag detalheUrl");
		}

	}

	/**
	 * Called when tag closing ( ex:- <name>AndroidPeople</name> -- </name> )
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;
		String currentValue = currentValueBuf.toString();

		/** set value */
		if (localName.equalsIgnoreCase("nome")) {
			currentProcurado.setNome(currentValue);
			
		} else if (localName.equalsIgnoreCase("apelido")) {
			currentProcurado.setApelido(currentValue);
			
		} else if (localName.equalsIgnoreCase("fotoId")) {
			currentProcurado.setFotoId(currentValue);
			
		} else if (localName.equalsIgnoreCase("historico")) {
			currentProcurado.setHistorico(currentValue);
			
		} else if (localName.equalsIgnoreCase("detalheUrl")) {
			currentProcurado.setDetalheUrl(currentValue);
			
		} else if (localName.equalsIgnoreCase("numeroProcesso")) {
			currentProcurado.setNumeroProcesso(currentValue);
			
		} else if (localName.equalsIgnoreCase("juizCompetente")) {
			currentProcurado.setJuizCompetente(currentValue);
			
		} else if (localName.equalsIgnoreCase("comarca")) {
			currentProcurado.setComarca(currentValue);
			
		} else if (localName.equalsIgnoreCase("dataExpedicaoMandado")) {
			currentProcurado.setDataExpedicaoMandado(currentValue);
			
		} else if (localName.equalsIgnoreCase("dataNascimento")) {
			currentProcurado.setDataNascimento(currentValue);
			
		} else if (localName.equalsIgnoreCase("rg")) {
			currentProcurado.setRg(currentValue);
			
		} else if (localName.equalsIgnoreCase("vulgo")) {
			currentProcurado.setVulgo(currentValue);
			
		} else if (localName.equalsIgnoreCase("procurado")) {
			procurados.getProcurados().add(currentProcurado);
			Log.i("XMLParser", "Procurado: " + currentProcurado.getNome());
			currentProcurado = null;
		}  

		currentValueBuf = null;
	}

	/**
	 * Called to get tag characters ( ex:- <name>AndroidPeople</name> -- to get
	 * AndroidPeople Character )
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValueBuf.append(ch, start, length);
			currentElement = false;
		}

	}

}
