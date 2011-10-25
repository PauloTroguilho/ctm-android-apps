package br.com.clebertm.procurados.view;

import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.clebertm.domain.Procurado;
import br.com.clebertm.procurados.R;

public class ListAdapter extends ArrayAdapter<Procurado> {

	private List<Procurado> procurados;
	private Context context;
	private final LayoutInflater inflator;

	private static class ViewHolder {
		public ImageView fotoView;
		public TextView apelidoTextView;
	}

	public ListAdapter(Context context, int textViewResourceId, List<Procurado> articles) {
		super(context, textViewResourceId, articles);
		this.procurados = articles;
		this.context = context;
		inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		BitmapManager.INSTANCE.setPlaceholder(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.loader));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = inflator.inflate(R.layout.grid_item, null);

			TextView apelidoTextView = (TextView) convertView.findViewById(R.id.grid_item_text);
			ImageView fotoView = (ImageView) convertView.findViewById(R.id.grid_item_image);
			holder = new ViewHolder();
			holder.apelidoTextView = apelidoTextView;
			holder.fotoView = fotoView;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Procurado p = procurados.get(position);
		holder.apelidoTextView.setText(p.getApelidoTratado());

		holder.fotoView.setTag(p.getFotoUrl());
		
		BitmapManager.INSTANCE.loadBitmap(p.getFotoUrl(), holder.fotoView, 100,
				120);

		return convertView;
	}
}