package br.com.clebertm.procurados;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.clebertm.domain.Procurado;
import br.com.clebertm.domain.Procurados;
import br.com.clebertm.parser.ProcuradosXmlHandler;

public class ProcuradosActivity extends Activity {
	/** Called when the activity is first created. */
	
	GridView gridProcurados;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/*
		 * Here we setContentView() to main.xml, get the GridView and then fill
		 * it with the ImageAdapter class that extend from BaseAdapter
		 */
		gridProcurados = (GridView) findViewById(R.id.gvProcurados);
		try {
			Procurados procurados = loadXmlProcurados();
			Procurado[] procArray = new Procurado[procurados.getProcurados().size()];
			procArray = procurados.getProcurados().toArray(procArray);
			gridProcurados.setAdapter(new ImageAdapter(this, procArray));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	private Procurados loadXmlProcurados() throws SAXException, ParserConfigurationException, IOException {
		/** Handling XML */
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();
		
		/** Send URL to parse XML Tags */
		URL sourceUrl = getClassLoader().getResource("procurados.xml");

		/** Create handler to handle XML Tags ( extends DefaultHandler ) */
		ProcuradosXmlHandler procuradosHandler = new ProcuradosXmlHandler();
		xr.setContentHandler(procuradosHandler);
		xr.parse(new InputSource(sourceUrl.openStream()));
		
		return procuradosHandler.getProcurados();
		
	}
	
	
	
	

	/**
	 * @author Cleber Moura <cleber.t.moura@gmail.com>
	 *
	 */
	public class ImageAdapter extends BaseAdapter {

		private Context context;
		private Procurado[] procurados;

		public ImageAdapter(Context context, Procurado[] procurados) {
			this.context = context;
			this.procurados = procurados;
		}

		@Override
		public int getCount() {
			return this.procurados.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View theView;
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	        	LayoutInflater li = getLayoutInflater();
	            theView = li.inflate(R.layout.grid_item, parent);

	            Procurado procurado = procurados[position];

				TextView tv = (TextView) theView.findViewById(R.id.grid_item_text);
				String apelido = "";
				if (procurado.getApelido() != null && procurado.getApelido().trim().length() > 0) {
					apelido = procurado.getApelido();
				} else {
					apelido = procurado.getVulgo();
				}
				if (apelido != null && apelido.trim().length() > 0){
					tv.setText(apelido);
				} else {
					tv.setText(procurado.getNome().split(" ")[0]);
				}
				int resId = getResources().getIdentifier("proc_" + procurado.getFotoId(), "drawable", "br.com.clebertm.procurados");
				ImageView iv = (ImageView) theView.findViewById(R.id.grid_item_image);
				iv.setImageResource(resId);
				
	        } else {
	            theView = (View) convertView;
	        }

			return theView;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
}