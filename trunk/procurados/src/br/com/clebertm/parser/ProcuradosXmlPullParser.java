package br.com.clebertm.parser;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;
import br.com.clebertm.domain.Procurado;
import br.com.clebertm.domain.Procurados;

public class ProcuradosXmlPullParser {
	
	private XmlPullParserFactory factory;
	private XmlPullParser parser;

	public ProcuradosXmlPullParser(InputStream inStream) throws XmlPullParserException {

		factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		parser = factory.newPullParser();

		parser.setInput(inStream, null);
	}
	
	public Procurados parse() throws XmlPullParserException, IOException {
		Procurados procurados = null;
		Procurado procurado = null;
		
		int eventType = parser.getEventType();
		String tagName = null;
		
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_DOCUMENT) {
				Log.d(getClass().getSimpleName(), "StartDocument");
				
			} else if (eventType == XmlPullParser.START_TAG) {
				tagName = parser.getName();
				Log.d(getClass().getSimpleName(), "StartTag: " + tagName);
				
				if (tagName.equalsIgnoreCase("procurados")) {
					procurados = new Procurados();
				} else if (tagName.equalsIgnoreCase("procurado")) {
					procurado = new Procurado();
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				tagName = parser.getName();
				Log.d(getClass().getSimpleName(), "EndTag: " + tagName);
				
				if (tagName.equalsIgnoreCase("procurado")) {
					procurados.getProcurados().add(procurado);
					procurado = null;
				}
				tagName = null;
			} else if (eventType == XmlPullParser.TEXT && tagName != null  
					&& !parser.getText().equals("\n")) {
				Log.d(getClass().getSimpleName(), "Text of tag: " + tagName);
				String text = parser.getText();
				
				if (tagName.equalsIgnoreCase("nome")) {
					procurado.setNome(text);
				} else if (tagName.equalsIgnoreCase("apelido")) {
					procurado.setApelido(text);
				} else if (tagName.equalsIgnoreCase("fotoId")) {
					procurado.setFotoId(text);
				} else if (tagName.equalsIgnoreCase("historico")) {
					procurado.setHistorico(text);
				} else if (tagName.equalsIgnoreCase("detalheUrl")) {
					procurado.setDetalheUrl(text);
				} else if (tagName.equalsIgnoreCase("numeroProcesso")) {
					procurado.setNumeroProcesso(text);
				} else if (tagName.equalsIgnoreCase("juizCompetente")) {
					procurado.setJuizCompetente(text);
				} else if (tagName.equalsIgnoreCase("comarca")) {
					procurado.setComarca(text);
				} else if (tagName.equalsIgnoreCase("dataExpedicaoMandado")) {
					procurado.setDataExpedicaoMandado(text);
				} else if (tagName.equalsIgnoreCase("dataNascimento")) {
					procurado.setDataNascimento(text);
				} else if (tagName.equalsIgnoreCase("rg")) {
					procurado.setRg(text);
				} else if (tagName.equalsIgnoreCase("vulgo")) {
					procurado.setVulgo(text);
				}
			}
			eventType = parser.next();
		}
		Log.d(getClass().getSimpleName(), "EndDocument");
		return procurados;
	}
}
