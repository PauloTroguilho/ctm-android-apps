package br.com.clebertm.procurados;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.clebertm.domain.Procurado;
import br.com.clebertm.domain.Procurados;
import br.com.clebertm.parser.ProcuradosXmlHandler;

public class ProcuradosActivity extends AdMobActivity {
	/** Called when the activity is first created. */

	private GridView gridProcurados;
	
	private Procurados procurados;

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
			procurados = loadXmlProcurados();
			Procurado[] procArray = new Procurado[procurados.getProcurados().size()];
			procArray = procurados.getProcurados().toArray(procArray);
			gridProcurados.setAdapter(new ImageAdapter(this, procurados.getProcurados()));
			gridProcurados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				/* (non-Javadoc)
				 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
				 */
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Procurado procurado = procurados.getProcurados().get(arg2);
					
					Intent intent = new Intent();
					intent.setClass(ProcuradosActivity.this, InfoProcuradoActivity.class);
					intent.putExtra("procurado", procurado);
					
					startActivity(intent);
				}
			});
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
		
		setAdView();
	}

	/**
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	private Procurados loadXmlProcurados() throws SAXException,
			ParserConfigurationException, IOException {
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

		private LayoutInflater mInflater;
		private List<Procurado> procuradosList;

		public ImageAdapter(Context context, List<Procurado> procuradosList) {
			mInflater = LayoutInflater.from(context);
			this.procuradosList = procuradosList;
		}

		@Override
		public int getCount() {
			return this.procuradosList.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = mInflater.inflate(R.layout.grid_item, null);
			Procurado procurado = procuradosList.get(position);

			TextView tv = (TextView) convertView.findViewById(R.id.grid_item_text);
			tv.setText(procurado.getApelidoTratado());
			
			int resId = getResources().getIdentifier("proc_" + procurado.getFotoId(), "drawable", "br.com.clebertm.procurados");
			ImageView iv = (ImageView) convertView.findViewById(R.id.grid_item_image);
			iv.setImageResource(resId);
			
			return convertView;
		}

		@Override
		public Object getItem(int arg0) {
			return procuradosList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}
	}
}