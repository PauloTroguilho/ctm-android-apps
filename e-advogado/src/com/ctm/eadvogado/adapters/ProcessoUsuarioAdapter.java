package com.ctm.eadvogado.adapters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ctm.eadvogado.R;
import com.ctm.eadvogado.db.EAdvogadoDbHelper;
import com.ctm.eadvogado.dto.TipoJuizo;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.ProcessoUsuario;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal;

public class ProcessoUsuarioAdapter extends ArrayAdapter<ProcessoUsuario> {

	private EAdvogadoDbHelper dbHelper;
	// Your sent context
	private LayoutInflater inflator;
	// Your custom values for the spinner (User)
	private List<ProcessoUsuario> values;
	
	private Map<Long, Tribunal> tribunaisMap = new HashMap<Long, Tribunal>();

	/**
	 * @param context
	 * @param textViewResourceId
	 * @param values
	 */
	public ProcessoUsuarioAdapter(Context context, int textViewResourceId,
			List<ProcessoUsuario> values) {
		super(context, textViewResourceId, values);
		dbHelper = new EAdvogadoDbHelper(context);
		this.values = values;
		inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return values.size();
	}

	public ProcessoUsuario getItem(int position) {
		return values.get(position);
	}

	public long getItemId(int position) {
		ProcessoUsuario item = getItem(position);
		return String.format("%s-%s-%s", item.getNpu(), item.getTipoJuizo(), item.getIdTribunal()).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = inflator.inflate(R.layout.processo_list_item, null);
		TextView tvNPU = (TextView) view.findViewById(R.id.textViewProcessoNPU);
		TextView tvTribunal = (TextView) view
				.findViewById(R.id.textViewTribunalNome);
		TextView tvTipoJuizo = (TextView) view
				.findViewById(R.id.textViewTipoJuizo);

		ProcessoUsuario item = getItem(position);
		String npu = item.getNpu();
		tvNPU.setText(String.format("%s-%s.%s.%s.%s.%s", npu.substring(0, 7),
				npu.substring(7, 9), npu.substring(9, 13), npu.substring(13, 14),
				npu.substring(14, 16), npu.substring(16)));
		Tribunal tribunal = tribunaisMap.get(item.getIdTribunal());
		if (tribunal == null) {
			tribunal = dbHelper.selectTribunalPorId(item.getIdTribunal());
			tribunaisMap.put(item.getIdTribunal(), tribunal);
		}
		tvTribunal.setText(tribunal.getSigla());
		if (item.getTipoJuizo().equals(TipoJuizo.PRIMEIRO_GRAU.name())) {
			tvTipoJuizo.setText(R.string.processo_tipoJuizo_1g);
		} else {
			tvTipoJuizo.setText(R.string.processo_tipoJuizo_2g);
		}
		return view;
	}

}