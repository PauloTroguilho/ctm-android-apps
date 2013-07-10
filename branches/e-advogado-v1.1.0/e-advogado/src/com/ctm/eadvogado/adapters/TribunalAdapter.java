package com.ctm.eadvogado.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ctm.eadvogado.R;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal;

public class TribunalAdapter extends ArrayAdapter<Tribunal> {

	// Your sent context
	private LayoutInflater inflator;
	// Your custom values for the spinner (User)
	private List<Tribunal> values;

	/**
	 * @param context
	 * @param textViewResourceId
	 * @param values
	 */
	public TribunalAdapter(Context context, int textViewResourceId,
			List<Tribunal> values) {
		super(context, textViewResourceId, values);
		this.values = values;
		inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return values.size();
	}

	public Tribunal getItem(int position) {
		return values.get(position);
	}

	public long getItemId(int position) {
		return getItem(position).getKey().getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = inflator.inflate(R.layout.tribunal_list_item, null);
		TextView tvSigla = (TextView) view
				.findViewById(R.id.textViewTribunalSigla);
		TextView tvTribunalNome = (TextView) view
				.findViewById(R.id.textViewTribunalNome);

		Tribunal tribunal = getItem(position);
		tvSigla.setText(tribunal.getSigla());
		tvTribunalNome.setText(tribunal.getNome());

		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getView(position, convertView, parent);
	}

}