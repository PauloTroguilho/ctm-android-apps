package com.ctm.eadvogado.adapters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ctm.eadvogado.ProcessoTabsPagerFragment;
import com.ctm.eadvogado.R;
import com.ctm.eadvogado.endpoints.processoEndpoint.ProcessoEndpoint;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Documento;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Processo;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.TipoDocumento;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.TipoMovimentoNacional;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.TipoMovimentoProcessual;
import com.ctm.eadvogado.util.EndpointUtils;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;

public class MovimentoExpandableAdapter extends BaseExpandableListAdapter {
	
	private static final String TAG = "e-Advogado";
	
	private ProcessoEndpoint processoEndpoint;
	private DownloadDocumentoTask downloadDocumentoTask;

	private Activity context;
	private List<TipoMovimentoProcessual> movimentos;
	private Map<TipoMovimentoProcessual, List<TipoDocumento>> documentos;

	private SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyyMMddHHmmss",
			Locale.getDefault());
	private SimpleDateFormat sdfTo = new SimpleDateFormat("dd/MM/yy HH:mm",
			Locale.getDefault());

	/**
	 * @param context
	 * @param movimentos
	 * @param documentos
	 */
	public MovimentoExpandableAdapter(Activity context, List<TipoMovimentoProcessual> movimentos,
			Map<TipoMovimentoProcessual, List<TipoDocumento>> documentos) {
		super();
		processoEndpoint = EndpointUtils.initProcessoEndpoint();
		this.context = context;
		this.movimentos = movimentos;
		this.documentos = documentos;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return documentos.get(getGroup(groupPosition)).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return ((TipoDocumento)getChild(groupPosition, childPosition)).getIdDocumento().hashCode();
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final TipoDocumento documento = (TipoDocumento) getChild(groupPosition, childPosition);
		LayoutInflater inflater = context.getLayoutInflater();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.documento_child_item, null);
		}
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Processo processo = ProcessoTabsPagerFragment.processoResult.getProcesso();
				doDownloadDocumento(processo.getNpu(), processo.getTribunal()
						.getId(), processo.getTipoJuizo(), documento
						.getIdDocumento());
			}
		});
		
		TextView tvDocumentoId = (TextView) convertView
				.findViewById(R.id.textViewDocumentoId);
		tvDocumentoId.setText(documento.getIdDocumento());
		return convertView;
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return documentos.get(getGroup(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return movimentos.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return movimentos.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TipoMovimentoProcessual movimento = (TipoMovimentoProcessual) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.movimento_group_item,
					null);
		}
		TextView tvDataHora = (TextView) convertView
				.findViewById(R.id.tvTabMov_DataHora);

		String dataHoraStr = movimento.getDataHora().toString();
		try {
			Date dataHora = sdfFrom.parse(dataHoraStr);
			dataHoraStr = sdfTo.format(dataHora);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		tvDataHora.setText(dataHoraStr);
		TextView tvComp = (TextView) convertView.findViewById(R.id.textViewComplementos);
		String complementos = "";
		if (movimento.getMovimentoNacional() != null) {
			TipoMovimentoNacional movNac = movimento.getMovimentoNacional();
			if (movNac.getComplemento() != null
					&& !movNac.getComplemento().isEmpty()) {
				for (int i = 0; i < movNac.getComplemento().size(); i++) {
					complementos += movNac.getComplemento().get(i);
					if (i < movNac.getComplemento().size() -1) {
						complementos+= "; ";
					}
				}
			}
		} else if (movimento.getMovimentoLocal() != null) {
			complementos += movimento.getMovimentoLocal();
		}
		tvComp.setText(complementos);
		
		ImageView ivDocVinc = (ImageView) convertView.findViewById(R.id.imageViewDocVinc);
		ivDocVinc.setVisibility(getChildrenCount(groupPosition) > 0 ? View.VISIBLE : View.GONE);
		
		return convertView;
	}
	
	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	/**
	 * @param npu
	 * @param idTribunal
	 * @param tipoJuizo
	 * @param idDocumento
	 */
	public void doDownloadDocumento(String npu, Long idTribunal, String tipoJuizo, String idDocumento) {
		if (downloadDocumentoTask != null) {
			return;
		}
		downloadDocumentoTask = new DownloadDocumentoTask();
		downloadDocumentoTask.execute(npu, idTribunal.toString(), tipoJuizo, idDocumento);
		((SherlockFragmentActivity) context).setSupportProgressBarIndeterminateVisibility(true);
	}
	
	
	public class DownloadDocumentoTask extends AsyncTask<String, Void, File> {
		
		String mensagem = "";
		String npu;
		Long idTribunal;
		String tipoJuizo;
		String idDocumento;
		
		@Override
		protected File doInBackground(String... params) {
			npu = params[0];
			idTribunal = Long.parseLong(params[1]);
			tipoJuizo = params[2];
			idDocumento = params[3];
			File file = null;
			
			String fileName = npu + "_" + idDocumento;
			final String fileNamePrefix = fileName;
			String[] fileList = context.getExternalCacheDir().list(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String filename) {
					return filename.startsWith(fileNamePrefix);
				}
			});
			if (fileList != null && fileList.length > 0) {
				file = new File(context.getExternalCacheDir(), fileList[0]);
				return file;
			}
			if (isExternalStorageWritable()) {
				Documento documento = null;
				try {
					documento = processoEndpoint.consultarDocumento(npu,
							idTribunal, tipoJuizo, idDocumento)
							.execute();
				} catch(GoogleJsonResponseException e) {
					Log.e(TAG, "Erro ao executar a operação!", e);
					mensagem = (e.getDetails() != null && e.getDetails() .getMessage() != null) ? 
							e.getDetails().getMessage() : context.getString(R.string.msg_erro_operacao_nao_realizada);
				} catch (IOException e) {
					Log.e(TAG, "Erro de comunicação ao executar a operação!", e);
					mensagem = context.getString(R.string.msg_erro_comunicacao_op_nao_realizada);
				} catch(Exception e) {
					Log.e(TAG, "Erro inesperado!", e);
					mensagem = context.getString(R.string.msg_erro_inesperado);
				}
				if (documento != null) {
					if (documento.getMimeType().equals(".pdf")) {
						fileName += ".pdf";
					} else if (documento.getMimeType().equals("text/html")) {
						fileName += ".html";
					} else {
						mensagem = context.getString(R.string.msg_tipo_documento_nao_suportado);
						return null;
					}
					file = new File(context.getExternalCacheDir(), fileName);
					FileOutputStream outputStream;
					try {
						outputStream = new FileOutputStream(file);
						outputStream.write(
							Hex.decodeHex(documento.getConteudo().toCharArray()));
						outputStream.close();
					} catch (Exception e) {
						Log.e(TAG, "Erro ao salvar arquivo", e);
						mensagem = context.getString(R.string.msg_erro_salvando_arquivo);
						return null;
					}
				}
			} else {
				mensagem = context.getString(R.string.msg_erro_salvando_arquivo);
			}
			return file;
		}

		@Override
		protected void onPostExecute(File file) {
			if (file != null) {
				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);

				MimeTypeMap mime = MimeTypeMap.getSingleton();
				String ext = file.getName().substring(
						file.getName().indexOf(".") + 1);
				String type = mime.getMimeTypeFromExtension(ext);

				intent.setDataAndType(Uri.fromFile(file), type);
				context.startActivity(intent);
			} else {
				Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
			}
			downloadDocumentoTask = null;
			((SherlockFragmentActivity) context).setSupportProgressBarIndeterminateVisibility(false);
		}

		@Override
		protected void onCancelled() {
			downloadDocumentoTask = null;
			((SherlockFragmentActivity) context).setSupportProgressBarIndeterminateVisibility(false);
		}
		
	}
	
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}

}
