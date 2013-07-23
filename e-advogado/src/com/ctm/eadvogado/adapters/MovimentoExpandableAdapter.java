package com.ctm.eadvogado.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctm.eadvogado.R;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.TipoDocumento;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.TipoMovimentoNacional;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.TipoMovimentoProcessual;

public class MovimentoExpandableAdapter extends BaseExpandableListAdapter {

	private Activity context;
	//private Map<TipoMovimentoProcessual, List<String>> documentos;
	//private List<TipoMovimentoProcessual> movimentos;
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

}
