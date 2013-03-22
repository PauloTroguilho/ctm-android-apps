/**
 * 
 */
package br.com.ctm.guiamedico.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import br.com.ctm.guiamedico.R;
import br.com.ctm.guiamedico.model.Medico;

/**
 * @author Cleber
 * 
 */
public class MedicoAdapter extends ArrayAdapter<Medico> {

	private LayoutInflater inflator;
	private int layoutResourceId;
	
	private List<Medico> objects = new ArrayList<Medico>();
	private List<Medico> originalObjects;
	private Object mLock = new Object();

	/**
	 * @param context
	 * @param layoutResourceId
	 * @param objects
	 */
	public MedicoAdapter(Context context, int layoutResourceId,
			List<Medico> objects) {
		super(context, layoutResourceId, objects);
		this.layoutResourceId = layoutResourceId;
		this.objects = objects;

		this.inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		MedicoHolder holder = null;

		if (row == null) {
			row = inflator.inflate(layoutResourceId, parent, false);

			holder = new MedicoHolder();
			holder.txtNomeMedico = (TextView) row
					.findViewById(R.id.txtNomeMedico);

			row.setTag(holder);
		} else {
			holder = (MedicoHolder) row.getTag();
		}

		Medico medico = objects.get(position);
		holder.txtNomeMedico.setText(medico.getNome());

		return row;
	}
	
	public int getCount() {
		return objects.size();
	}
	
	public int getPosition(Medico item) {
        return objects.indexOf(item);
    }

	private Filter filter;

	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new Filter() {
				
				@Override
				protected FilterResults performFiltering(CharSequence prefix) {
					FilterResults results = new FilterResults();
					
					if (originalObjects == null) {
		                synchronized (mLock) {
		                    originalObjects = new ArrayList<Medico>(objects);
		                }
		            }

					if (prefix == null || prefix.length() == 0) {
						ArrayList<Medico> list;
						synchronized (mLock) {
							list = new ArrayList<Medico>(originalObjects);
						}
						results.values = list;
						results.count = list.size();
					} else {
						String prefixString = prefix.toString().toLowerCase();

						ArrayList<Medico> values;
						synchronized (mLock) {
							values = new ArrayList<Medico>(originalObjects);
						}

						final int count = values.size();
						final ArrayList<Medico> newValues = new ArrayList<Medico>();

						for (int i = 0; i < count; i++) {
							final Medico value = values.get(i);
							final String valueText = value.getNome()
									.toLowerCase();

							// First match against the whole, non-splitted value
							if (valueText.startsWith(prefixString)) {
								newValues.add(value);
							} else {
								final String[] words = valueText.split(" ");
								final int wordCount = words.length;

								// Start at index 0, in case valueText starts
								// with space(s)
								for (int k = 0; k < wordCount; k++) {
									if (words[k].startsWith(prefixString)) {
										newValues.add(value);
										break;
									}
								}
							}
						}

						results.values = newValues;
						results.count = newValues.size();
					}

					return results;
				}

				@Override
				protected void publishResults(CharSequence constraint,
						FilterResults results) {
					//noinspection unchecked
		            objects = (List<Medico>) results.values;
		            if (results.count > 0) {
		                notifyDataSetChanged();
		            } else {
		                notifyDataSetInvalidated();
		            }
				}
			};
		}
		return filter;
	}

	static class MedicoHolder {
		TextView txtNomeMedico;
	}

}
