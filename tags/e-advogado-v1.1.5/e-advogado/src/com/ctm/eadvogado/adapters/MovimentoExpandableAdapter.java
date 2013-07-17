package com.ctm.eadvogado.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ctm.eadvogado.R;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.TipoMovimentoNacional;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.TipoMovimentoProcessual;

public class MovimentoExpandableAdapter extends BaseExpandableListAdapter {

	private Activity context;
	//private Map<TipoMovimentoProcessual, List<String>> documentos;
	private List<TipoMovimentoProcessual> movimentos;

	private SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyyMMddHHmmss",
			Locale.getDefault());
	private SimpleDateFormat sdfTo = new SimpleDateFormat("dd/MM/yy HH:mm",
			Locale.getDefault());

	/**
	 * @param context
	 * @param movimentos
	 * @param documentos
	 */
	public MovimentoExpandableAdapter(Activity context,
			List<TipoMovimentoProcessual> movimentos) {
		super();
		this.context = context;
		this.movimentos = movimentos;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		List<String> idDocumentoVinculado = movimentos.get(groupPosition).getIdDocumentoVinculado();
		return idDocumentoVinculado != null ? idDocumentoVinculado.get(childPosition) : null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final String documentoId = (String) getChild(groupPosition,
				childPosition);
		LayoutInflater inflater = context.getLayoutInflater();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.documento_child_item, null);
		}
		TextView tvDocumentoId = (TextView) convertView
				.findViewById(R.id.textViewDocumentoId);
		tvDocumentoId.setText(documentoId);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		List<String> idDocumentoVinculado = movimentos.get(groupPosition).getIdDocumentoVinculado();
		return idDocumentoVinculado != null ? idDocumentoVinculado.size() : 0;
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
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("e-Advogado", "click no group view");
				onGroupExpanded(groupPosition);
			}
		});
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
		ListView lvComplementos = (ListView) convertView
				.findViewById(R.id.listViewComplementos);
		List<String> complementos = new ArrayList<String>();
		if (movimento.getMovimentoNacional() != null) {
			TipoMovimentoNacional movNac = movimento.getMovimentoNacional();
			if (movNac.getComplemento() != null
					&& !movNac.getComplemento().isEmpty()) {
				String codNac = movNac.getCodigoNacional() != null ? movNac
						.getCodigoNacional().toString() : "";
				for (String compl : movNac.getComplemento()) {
					complementos.add(codNac + " : " + compl);
				}
			}
		}
		if (movimento.getMovimentoLocal() != null) {
			complementos.add(movimento.getMovimentoLocal());
		}
		lvComplementos.setAdapter(new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, complementos));
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
