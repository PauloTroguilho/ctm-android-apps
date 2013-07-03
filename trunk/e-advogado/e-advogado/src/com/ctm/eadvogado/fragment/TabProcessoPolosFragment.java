package com.ctm.eadvogado.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ctm.eadvogado.ProcessoTabsPagerFragment;
import com.ctm.eadvogado.R;
import com.ctm.eadvogado.adapters.PoloAdapter;
import com.ctm.eadvogado.dto.ProcessoDTO;
import com.ctm.eadvogado.processoendpoint.model.TipoPoloProcessual;

/**
 * @author Cleber
 * 
 */
public class TabProcessoPolosFragment extends SherlockFragment {

	ProcessoDTO processoDTO;

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		processoDTO = ProcessoTabsPagerFragment.processoResult;
	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.processo_tab_polos, container, false);
		ListView lvPoloAtivo = (ListView) v
				.findViewById(R.id.lvTabMov_movimentos);
		ListView lvPoloPassivo = (ListView) v
				.findViewById(R.id.lvTabPolos_poloPassivo);
		List<TipoPoloProcessual> polosAtivos = new ArrayList<TipoPoloProcessual>();
		List<TipoPoloProcessual> polosPassivos = new ArrayList<TipoPoloProcessual>();
		fillPolosLists(polosAtivos, polosPassivos);
		PoloAdapter poloAtivoAdapter = new PoloAdapter(getActivity(),
				R.layout.polo_list_item, polosAtivos);
		PoloAdapter poloPassivoAdapter = new PoloAdapter(getActivity(),
				R.layout.polo_list_item, polosPassivos);
		lvPoloAtivo.setAdapter(poloAtivoAdapter);
		lvPoloPassivo.setAdapter(poloPassivoAdapter);
		return v;
	}

	/**
	 * @param polosAtivos
	 * @param polosPassivos
	 */
	private void fillPolosLists(List<TipoPoloProcessual> polosAtivos,
			List<TipoPoloProcessual> polosPassivos) {
		for (TipoPoloProcessual poloProcessual : processoDTO.getProcesso().getProcessoJudicial()
				.getDadosBasicos().getPolo()) {
			if (poloProcessual.getPolo().equals("AT")) {
				polosAtivos.add(poloProcessual);
			} else if (poloProcessual.getPolo().equals("PA")) {
				polosPassivos.add(poloProcessual);
			}
		}
	}
}