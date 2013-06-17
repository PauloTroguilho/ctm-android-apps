package com.ctm.eadvogado.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ctm.eadvogado.R;
import com.ctm.eadvogado.TipoJuizo;

public class TipoJuizoAdapter extends ArrayAdapter<TipoJuizo> {

	// Your sent context
	private LayoutInflater inflator;
	// Your custom values for the spinner (User)
	private List<TipoJuizo> values;

	/**
	 * @param context
	 * @param textViewResourceId
	 * @param values
	 */
	public TipoJuizoAdapter(Context context, int textViewResourceId,
			List<TipoJuizo> values) {
		super(context, textViewResourceId, values);
		this.values = values;
		inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return values.size();
	}

	public TipoJuizo getItem(int position) {
		return values.get(position);
	}

	public long getItemId(int position) {
		return getItem(position).ordinal();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) inflator.inflate(android.R.layout.simple_spinner_item, null);
		TipoJuizo tipoJuizo = getItem(position);
		if (tipoJuizo.equals(TipoJuizo.PRIMEIRO_GRAU)) {
			view.setText(R.string.processo_tipoJuizo_1g);
		} else {
			view.setText(R.string.processo_tipoJuizo_2g);
		}
		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getView(position, convertView, parent);
	}

}