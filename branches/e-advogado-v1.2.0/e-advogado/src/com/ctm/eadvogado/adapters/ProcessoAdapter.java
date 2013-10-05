package com.ctm.eadvogado.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ctm.eadvogado.R;
import com.ctm.eadvogado.db.EAdvogadoContract;
import com.ctm.eadvogado.db.EAdvogadoDbHelper;
import com.ctm.eadvogado.dto.TipoJuizo;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Processo;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal;

public class ProcessoAdapter extends ArrayAdapter<Processo> {

	// Your sent context
	private LayoutInflater inflator;
	// Your custom values for the spinner (User)
	private List<Processo> values;
	
	private EAdvogadoDbHelper dbHelper;

	/**
	 * @param context
	 * @param textViewResourceId
	 * @param values
	 */
	public ProcessoAdapter(Context context, int textViewResourceId,
			List<Processo> values) {
		super(context, textViewResourceId, values);
		dbHelper = new EAdvogadoDbHelper(context);
		this.values = values;
		inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return values.size();
	}

	public Processo getItem(int position) {
		return values.get(position);
	}

	public long getItemId(int position) {
		return getItem(position).getKey().getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = inflator.inflate(R.layout.processo_list_item, null);
		TextView tvNPU = (TextView) view.findViewById(R.id.textViewProcessoNPU);
		TextView tvTribunal = (TextView) view
				.findViewById(R.id.textViewTribunalNome);
		TextView tvTipoJuizo = (TextView) view
				.findViewById(R.id.textViewTipoJuizo);

		Processo item = getItem(position);
		String npu = item.getNpu();
		tvNPU.setText(String.format("%s-%s.%s.%s.%s.%s", npu.substring(0, 7),
				npu.substring(7, 9), npu.substring(9, 13), npu.substring(13, 14),
				npu.substring(14, 16), npu.substring(16)));
		Tribunal tribunal = dbHelper.selectTribunalPorId(item.getTribunal().getId());
		if (tribunal != null) {
			tvTribunal.setText(tribunal.getSigla());
		}
		if (item.getTipoJuizo().equals(TipoJuizo.PRIMEIRO_GRAU.name())) {
			tvTipoJuizo.setText(R.string.processo_tipoJuizo_1g);
		} else {
			tvTipoJuizo.setText(R.string.processo_tipoJuizo_2g);
		}
		TextView tvPoloAtivo = (TextView) view.findViewById(R.id.textViewPoloAtivo);
		String poloAtivo = (String) item.get(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_ATIVO);
		tvPoloAtivo.setText(poloAtivo != null ? poloAtivo : "");
		TextView tvPoloPassivo = (TextView) view.findViewById(R.id.textViewPoloPassivo);
		String poloPassivo = (String) item.get(EAdvogadoContract.ProcessoTable.COLUMN_NAME_POLO_PASSIVO);
		tvPoloPassivo.setText(poloPassivo != null ? poloPassivo : "");
		return view;
	}

}