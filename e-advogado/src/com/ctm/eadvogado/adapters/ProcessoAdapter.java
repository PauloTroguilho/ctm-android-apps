package com.ctm.eadvogado.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ctm.eadvogado.R;
import com.ctm.eadvogado.dto.ProcessoDTO;
import com.ctm.eadvogado.dto.TipoJuizo;

public class ProcessoAdapter extends ArrayAdapter<ProcessoDTO> {

	// Your sent context
	private LayoutInflater inflator;
	// Your custom values for the spinner (User)
	private List<ProcessoDTO> values;

	/**
	 * @param context
	 * @param textViewResourceId
	 * @param values
	 */
	public ProcessoAdapter(Context context, int textViewResourceId,
			List<ProcessoDTO> values) {
		super(context, textViewResourceId, values);
		this.values = values;
		inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return values.size();
	}

	public ProcessoDTO getItem(int position) {
		return values.get(position);
	}

	public long getItemId(int position) {
		return getItem(position).getProcesso().getKey().getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = inflator.inflate(R.layout.processo_list_item, null);
		TextView tvNPU = (TextView) view.findViewById(R.id.textViewProcessoNPU);
		TextView tvTribunal = (TextView) view
				.findViewById(R.id.textViewTribunalNome);
		TextView tvTipoJuizo = (TextView) view
				.findViewById(R.id.textViewTipoJuizo);

		ProcessoDTO item = getItem(position);
		String npu = item.getProcesso().getNpu();
		tvNPU.setText(String.format("%s-%s.%s.%s.%s.%s", npu.substring(0, 7),
				npu.substring(7, 9), npu.substring(9, 13), npu.substring(13, 14),
				npu.substring(14, 16), npu.substring(16)));
		tvTribunal.setText(item.getTribunal().getSigla());
		if (item.getProcesso().getTipoJuizo().equals(TipoJuizo.PRIMEIRO_GRAU.name())) {
			tvTipoJuizo.setText(R.string.processo_tipoJuizo_1g);
		} else {
			tvTipoJuizo.setText(R.string.processo_tipoJuizo_2g);
		}
		return view;
	}

}