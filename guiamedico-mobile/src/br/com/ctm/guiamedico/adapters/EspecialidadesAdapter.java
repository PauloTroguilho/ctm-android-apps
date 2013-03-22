package br.com.ctm.guiamedico.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.ctm.guiamedico.R;
import br.com.ctm.guiamedico.model.Especialidade;

public class EspecialidadesAdapter extends ArrayAdapter<Especialidade> {

	// Your sent context
	private Context context;
	private LayoutInflater inflator;
	// Your custom values for the spinner (User)
	private List<Especialidade> values;

	/**
	 * @param context
	 * @param textViewResourceId
	 * @param values
	 */
	public EspecialidadesAdapter(Context context, int textViewResourceId,
			List<Especialidade> values) {
		super(context, textViewResourceId, values);
		this.context = context;
		this.values = values;
		inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return values.size();
	}

	public Especialidade getItem(int position) {
		return values.get(position);
	}

	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = inflator.inflate(R.layout.spinner_especialidades_item, null);
		TextView label = (TextView) view.findViewById(R.id.textViewEspecialidadeNome);
		label.setText(getItem(position).getNome());
		return view;
	}

	// And here is when the "chooser" is popped up
	// Normally is the same view, but you can customize it if you want
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View view = inflator.inflate(R.layout.spinner_especialidades_item, null);
		TextView label = (TextView) view.findViewById(R.id.textViewEspecialidadeNome);
		label.setText(getItem(position).getNome());
		return view;
	}
}