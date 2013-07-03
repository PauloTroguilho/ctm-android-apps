package com.ctm.eadvogado.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ctm.eadvogado.R;
import com.ctm.eadvogado.processoendpoint.model.TipoMovimentoProcessual;

public class MovimentoAdapter extends ArrayAdapter<TipoMovimentoProcessual> {

	// Your sent context
	private LayoutInflater inflator;
	// Your custom values for the spinner (User)
	private List<TipoMovimentoProcessual> values;
	
	private SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
	private SimpleDateFormat sdfTo = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());

	/**
	 * @param context
	 * @param textViewResourceId
	 * @param values
	 */
	public MovimentoAdapter(Context context, int textViewResourceId,
			List<TipoMovimentoProcessual> values) {
		super(context, textViewResourceId, values);
		this.values = values;
		inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return values.size();
	}

	public TipoMovimentoProcessual getItem(int position) {
		return values.get(position);
	}

	public long getItemId(int position) {
		return getItem(position).toString().hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = inflator.inflate(R.layout.movimento_list_item, null);
		TextView tvDataHora = (TextView) view.findViewById(R.id.tvTabMov_DataHora);
		TextView tvComplem = (TextView) view.findViewById(R.id.tvTabMov_Complemento);

		TipoMovimentoProcessual mov = getItem(position);
		String dataHoraStr = mov.getDataHora().toString();
		try {
			Date dataHora = sdfFrom.parse(dataHoraStr);
			dataHoraStr = sdfTo.format(dataHora);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		tvDataHora.setText(dataHoraStr);
		if (mov.getMovimentoNacional() != null &&
				mov.getMovimentoNacional().getComplemento() != null && !mov.getMovimentoNacional().getComplemento().isEmpty()) {
			tvComplem.setText(mov.getMovimentoNacional().getComplemento().get(0));
		} else if (mov.getComplemento() != null && !mov.getComplemento().isEmpty()) {
			tvComplem.setText(mov.getComplemento().get(0));
		} else {
			tvComplem.setText("N/A");
		}
		return view;
	}

}