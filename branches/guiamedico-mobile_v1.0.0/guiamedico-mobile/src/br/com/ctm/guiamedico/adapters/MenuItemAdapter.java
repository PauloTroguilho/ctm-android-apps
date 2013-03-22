package br.com.ctm.guiamedico.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.ctm.guiamedico.R;

/**
 * @author Cleber
 *
 */
public class MenuItemAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater inflator;

	// Keep all Images in array
	public Integer[] iconsIds = { R.drawable.ic_pesquisar,
			R.drawable.ic_medico, R.drawable.ic_hospital,
			R.drawable.ic_laboratorio, R.drawable.ic_noticias,
			R.drawable.ic_cliente };
	
	public Integer[] labelsIds = { R.string.menu_pesquisar,
			R.string.menu_medicos, R.string.menu_hospitais,
			R.string.menu_laboratorios, R.string.menu_noticias,
			R.string.menu_cliente };

	// Constructor
	public MenuItemAdapter(Context c) {
		context = c;
		inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return iconsIds.length;
	}

	@Override
	public Object getItem(int position) {
		return iconsIds[position];
	}

	@Override
	public long getItemId(int position) {
		return iconsIds[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflator.inflate(R.layout.activity_menu_item, null);
			TextView txtVwMenuLabel = (TextView) convertView.findViewById(R.id.txtVwMenuItem);
			txtVwMenuLabel.setText(labelsIds[position]);
			Drawable drawableTop = convertView.getResources().getDrawable(iconsIds[position]);
			txtVwMenuLabel.setCompoundDrawablesWithIntrinsicBounds(null, drawableTop, null, null);
		}
		return convertView;
	}

}