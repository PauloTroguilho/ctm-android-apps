package br.com.clebertm.procurados;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import br.com.clebertm.domain.Procurado;
import br.com.clebertm.domain.Procurados;
import br.com.clebertm.parser.ProcuradosXmlHandler;
import br.com.clebertm.procurados.util.Consts;
import br.com.clebertm.procurados.util.FileCacheUtils;
import br.com.clebertm.procurados.util.IOUtils;
import br.com.clebertm.procurados.view.ListAdapter;

public class ProcuradosActivity extends AdMobActivity {
	/** Called when the activity is first created. */

	private static final int DIALOG_ATUALIZACAO = 1;
	private static final int DIALOG_ERRO_CRIACAO_XML = 2;
	private static final int DIALOG_ERRO_CARREGAMENTO_XML = 3;
	private static final int DIALOG_ERRO_ATUALIZACAO_XML = 4;
	private static final int DIALOG_SOBRE = 5;
	
	private static final long DAY_INTERVAL = 1000 * 60 * 60 * 24;

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
		gridProcurados = (GridView) findViewById(R.id.gridProcurados);
		
		carregarListaProcurados();
		
		List<Procurado> procuradosList = new ArrayList<Procurado>();
		if (procurados != null) {
			procuradosList = procurados.getProcurados();
		}
		
		gridProcurados.setAdapter(new ListAdapter(this, R.id.gridProcurados, procuradosList));
		gridProcurados
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					/*
					 * (non-Javadoc)
					 * 
					 * @seeandroid.widget.AdapterView.OnItemClickListener#
					 * onItemClick(android.widget.AdapterView,
					 * android.view.View, int, long)
					 */
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Procurado procurado = procurados.getProcurados()
								.get(arg2);

						Intent intent = new Intent();
						intent.setClass(ProcuradosActivity.this,
								InfoProcuradoActivity.class);
						intent.putExtra("procurado", procurado);

						startActivity(intent);
					}
				});

		gridProcurados.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (fileCacheXmlProcuradosExists()) {
					File xmlCache = getFileCacheXmlProcurados();
					Date data = new Date();
					if (data.getTime() - xmlCache.lastModified() > DAY_INTERVAL) {
						showDialog(DIALOG_ATUALIZACAO);
					}
				}
			}
		}, 1000 * 60);
		
		setAdView();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
			case DIALOG_ATUALIZACAO:
				return new AlertDialog.Builder(ProcuradosActivity.this).setIcon(
						R.drawable.icon_update).setTitle(
						getString(R.string.dialog_atualizacao_titulo)).setMessage(
						getString(R.string.dialog_atualizacao_message))
						.setPositiveButton(R.string.dialog_sim,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										boolean atualizou = false;
										try {
											atualizou = atualizarXmlProcurados();
										} catch (Exception e) { }
										if (atualizou) {
											gridProcurados.setAdapter(new ListAdapter(ProcuradosActivity.this, 
													R.id.gridProcurados, procurados.getProcurados()));
										} else {
											showDialog(DIALOG_ERRO_ATUALIZACAO_XML);
										}
									}
								}).setNegativeButton(R.string.dialog_nao,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										try {
											carregarXmlProcuradosFromCache();
										} catch (Exception e) {
											Log.e(getClass().getSimpleName(), "Falha ao carregar o Xml de procurados");
											showDialog(DIALOG_ERRO_CARREGAMENTO_XML);
										}
									}
								}).create();
				
			case DIALOG_ERRO_CRIACAO_XML:
				return new AlertDialog.Builder(ProcuradosActivity.this).setIcon(
						R.drawable.icon_error).setTitle(
						getString(R.string.dialog_erro_titulo)).setMessage(
						getString(R.string.dialog_erro_msg_criar_xml_dir_local))
						.setPositiveButton(R.string.dialog_ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
									}
								}).create();
				
			case DIALOG_ERRO_CARREGAMENTO_XML:
				return new AlertDialog.Builder(ProcuradosActivity.this).setIcon(
						R.drawable.icon_error).setTitle(
						getString(R.string.dialog_erro_titulo)).setMessage(
						getString(R.string.dialog_erro_msg_carregar_arq_xml))
						.setPositiveButton(R.string.dialog_ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
									}
								}).create();
				
			case DIALOG_ERRO_ATUALIZACAO_XML:
				return new AlertDialog.Builder(ProcuradosActivity.this).setIcon(
						R.drawable.icon_error).setTitle(
						getString(R.string.dialog_erro_titulo)).setMessage(
						getString(R.string.dialog_erro_msg_conectando_ao_servidor))
						.setPositiveButton(R.string.dialog_ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										finish();
									}
								}).create();
				
			case DIALOG_SOBRE:
				return new AlertDialog.Builder(ProcuradosActivity.this)
						.setIcon(R.drawable.icon_help).setTitle(getString(R.string.dialog_sobre_titulo))
						.setMessage(getString(R.string.dialog_sobre_msg))
						.setPositiveButton(R.string.dialog_ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										/* User clicked OK so do some stuff */
									}
								}).create();
		}
		return null;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.menu_item_atualizar:
				boolean atualizou = false;
				try {
					atualizou = atualizarXmlProcurados();
				} catch (Exception e) { }
				if (atualizou) {
					gridProcurados.setAdapter(new ListAdapter(ProcuradosActivity.this, 
							R.id.gridProcurados, procurados.getProcurados()));
				} else {
					showDialog(DIALOG_ERRO_ATUALIZACAO_XML);
				}
				return true;
			case R.id.menu_item_sobre:
				showDialog(DIALOG_SOBRE);
				return true;
			case R.id.menu_item_sair:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * 
	 */
	private boolean atualizarXmlProcurados() {
		boolean atualizou = false;
		
		URL sourceUrl = null;
		InputStream in = null;
		/** Send URL to parse XML Tags */
		try {
			sourceUrl = new URL(Consts.SERVER_URL_TO_XML);
			in = sourceUrl.openStream();
		} catch (MalformedURLException e1) {
			Log.e(getClass().getSimpleName(), "Url do servidor invalida para o xml");
		} catch (IOException e) {
			Log.e(getClass().getSimpleName(), "Falha ao abrir o stream para o xml");
		}
		
		if (in != null) {
			File xmlCache = new File(FileCacheUtils.getCacheDir(this), Consts.PROCURADOS_XML_FILENAME);
			if (!xmlCache.exists()) {
				try {
					xmlCache.createNewFile();
				} catch (IOException e) {
				}
			}
			if (xmlCache.exists()) {
				FileOutputStream out = null;
				try {
					out = new FileOutputStream(xmlCache);
				} catch (FileNotFoundException e) {
					Log.e(getClass().getSimpleName(), "Falha ao abrir o stream de escrita para o xml local");
				}
				if (out != null) {
					try {
						IOUtils.copyStream(in, out);
						atualizou = true;
					} catch (IOException e) {
						Log.e(getClass().getSimpleName(), "Falha ao copiar streams");
					}
				} 
			} 
		}
		return atualizou;
	}
	
	/**
	 * @return
	 */
	private File getFileCacheXmlProcurados() {
		return new File(FileCacheUtils.getCacheDir(this), Consts.PROCURADOS_XML_FILENAME);
	}
	
	/**
	 * @return
	 */
	private boolean fileCacheXmlProcuradosExists() {
		return getFileCacheXmlProcurados().exists();
	}

	/**
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	private void carregarListaProcurados() {
		if (fileCacheXmlProcuradosExists()) {
			boolean carregou = carregarXmlProcuradosFromCache();
			if (!carregou) {
				boolean atualizou = atualizarXmlProcurados();
				if (!atualizou) {
					showDialog(DIALOG_ERRO_ATUALIZACAO_XML);
				}
			}
		} else {
			boolean atualizou = atualizarXmlProcurados();
			if (atualizou) {
				boolean carregou = carregarXmlProcuradosFromCache();
				if (!carregou) {
					showDialog(DIALOG_ERRO_CARREGAMENTO_XML);
				}
			} else {
				showDialog(DIALOG_ERRO_ATUALIZACAO_XML);
			}
		}
	}
	
	/**
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	private boolean carregarXmlProcuradosFromCache() {
		File xmlCache = new File(FileCacheUtils.getCacheDir(this), Consts.PROCURADOS_XML_FILENAME);
		InputStream in = null;
		try {
			in = new FileInputStream(xmlCache);
		} catch (FileNotFoundException e) {
			Log.e(getClass().getSimpleName(), "Falha ao abrir Xml local de procurados");
		}
		boolean result = false;
		if (in != null) {
			result = carregarXmlProcurados(in);
			IOUtils.closeQuietly(in);
		}
		return result;
	}


	/**
	 * @param inputStream
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	private boolean carregarXmlProcurados(InputStream inputStream)  {
		boolean carregou = false;
		/** Handling XML */
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = null;
		XMLReader xr = null;
		try {
			sp = spf.newSAXParser();
			xr = sp.getXMLReader();
			
			/** Create handler to handle XML Tags ( extends DefaultHandler ) */
			ProcuradosXmlHandler procuradosHandler = new ProcuradosXmlHandler();
			xr.setContentHandler(procuradosHandler);
			xr.parse(new InputSource(inputStream));
			
			procurados = procuradosHandler.getProcurados();
			carregou = true;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return carregou;

	}

}