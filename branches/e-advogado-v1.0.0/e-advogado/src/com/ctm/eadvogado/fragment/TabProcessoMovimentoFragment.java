package com.ctm.eadvogado.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ctm.eadvogado.ProcessoTabsPagerFragment;
import com.ctm.eadvogado.R;
import com.ctm.eadvogado.adapters.MovimentoAdapter;
import com.ctm.eadvogado.dto.ProcessoDTO;
import com.ctm.eadvogado.processoendpoint.model.TipoMovimentoProcessual;

/**
 * @author Cleber
 *
 */
public class TabProcessoMovimentoFragment extends SherlockFragment {
	
	ProcessoDTO processoDTO;

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		processoDTO = ProcessoTabsPagerFragment.PROCESSO;
	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.processo_tab_movimento, container, false);
		ListView lvMovimentos = (ListView) v.findViewById(R.id.lvTabMov_movimentos);
		List<TipoMovimentoProcessual> movimentos = processoDTO.getProcesso().getProcessoJudicial().getMovimento();
		MovimentoAdapter movAdapter = new MovimentoAdapter(getActivity(), R.layout.movimento_list_item, movimentos);
		lvMovimentos.setAdapter(movAdapter);
		return v;
	}
	
}