package br.com.clebertm.procurados.view;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import br.com.clebertm.domain.Procurado;
import br.com.clebertm.procurados.R;
import br.com.clebertm.procurados.view.BitmapManager.LoadBitmapCompleteListener;

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
		BitmapManager.INSTANCE.setContext(context);
		BitmapManager.INSTANCE.setPlaceholder(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.loader));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = inflator.inflate(R.layout.grid_load_item, null);

			TextView apelidoTextView = (TextView) convertView.findViewById(R.id.tvApelido);
			ImageView fotoView = (ImageView) convertView.findViewById(R.id.ivFoto);
			
			holder = new ViewHolder();
			holder.apelidoTextView = apelidoTextView;
			holder.fotoView = fotoView;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Procurado p = procurados.get(position);
		holder.apelidoTextView.setText(p.getApelidoTratado());

		holder.fotoView.setTag(p.getFotoId());
		
		final ViewSwitcher vSwitcher = (ViewSwitcher) convertView.findViewById(R.id.gridItemViewSwitcher);
		vSwitcher.setDisplayedChild(0);
		
		LoadBitmapCompleteListener listener = new LoadBitmapCompleteListener() {
			
			@Override
			public void loadCompledted(Bitmap bitmap) {
				vSwitcher.setDisplayedChild(1);
			}
		};
		
		BitmapManager.INSTANCE.loadBitmap(p.getFotoId(), holder.fotoView, listener, 100, 120);
		
		return convertView;
	}
}