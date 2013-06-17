package com.ctm.eadvogado.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ctm.eadvogado.R;
import com.ctm.eadvogado.processoendpoint.model.TipoPoloProcessual;

public class PoloAdapter extends ArrayAdapter<TipoPoloProcessual> {

	// Your sent context
	private LayoutInflater inflator;
	// Your custom values for the spinner (User)
	private List<TipoPoloProcessual> values;

	/**
	 * @param context
	 * @param textViewResourceId
	 * @param values
	 */
	public PoloAdapter(Context context, int textViewResourceId,
			List<TipoPoloProcessual> values) {
		super(context, textViewResourceId, values);
		this.values = values;
		inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return values.size();
	}

	public TipoPoloProcessual getItem(int position) {
		return values.get(position);
	}

	public long getItemId(int position) {
		return getItem(position).toString().hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = inflator.inflate(R.layout.polo_list_item, null);
		TextView tvNomeParte = (TextView) view.findViewById(R.id.tvTabPolos_NomeParte);
		TextView tvParteAdvogado = (TextView) view.findViewById(R.id.tvTabPolos_ParteAdvogado);

		TipoPoloProcessual polo = getItem(position);
		tvNomeParte.setText(polo.getParte().get(0).getPessoa().getNome());
		tvParteAdvogado.setText(polo.getParte().get(0).getAdvogado().get(0).getNome());
		return view;
	}

}