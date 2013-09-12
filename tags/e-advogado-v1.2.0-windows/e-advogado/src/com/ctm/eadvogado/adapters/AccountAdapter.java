/**
 * 
 */
package com.ctm.eadvogado.adapters;

import java.util.List;

import android.accounts.Account;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ctm.eadvogado.R;

/**
 * @author Cleber
 * 
 */
public class AccountAdapter extends ArrayAdapter<Account> {

	// Your sent context
	private LayoutInflater inflator;
	// Your custom values for the spinner (User)
	private List<Account> values;

	/**
	 * @param context
	 * @param textViewResourceId
	 * @param values
	 */
	public AccountAdapter(Context context, int textViewResourceId,
			List<Account> values) {
		super(context, textViewResourceId, values);
		this.values = values;
		inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return values.size();
	}

	public Account getItem(int position) {
		return values.get(position);
	}

	public long getItemId(int position) {
		return getItem(position).toString().hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = inflator.inflate(R.layout.account_list_item, null);
		TextView tvAccountName = (TextView) view.findViewById(R.id.textViewAccountName);
		Account account = getItem(position);
		tvAccountName.setText(account.name);
		return view;
	}
}
