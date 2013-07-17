package com.ctm.eadvogado.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ctm.eadvogado.ProcessoTabsPagerFragment;
import com.ctm.eadvogado.R;
import com.ctm.eadvogado.adapters.MovimentoExpandableAdapter;
import com.ctm.eadvogado.dto.ProcessoDTO;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.TipoMovimentoProcessual;
import com.ctm.eadvogado.util.Consts;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

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
		processoDTO = ProcessoTabsPagerFragment.processoResult;
	}
	
	/**
	 * @param admobViewId
	 */
	protected void initAdmobBanner(int admobViewId, View parentView) {
		if (Consts.VERSAO_GRATIS) {
			// Look up the AdView as a resource and load a request.
		    AdView adView = (AdView) parentView.findViewById(admobViewId);
		    AdRequest request = new AdRequest();
			request.addTestDevice(AdRequest.TEST_EMULATOR);
			adView.loadAd(request);
			adView.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.processo_tab_movimento, container, false);
		initAdmobBanner(R.id.adView, v);
		//ListView lvMovimentos = (ListView) v.findViewById(R.id.lvTabMov_movimentos);
		List<TipoMovimentoProcessual> movimentos = processoDTO.getProcesso().getProcessoJudicial().getMovimento();
		/*MovimentoAdapter movAdapter = new MovimentoAdapter(getActivity(), R.layout.movimento_list_item, movimentos);
		lvMovimentos.setAdapter(movAdapter);*/
		
		ExpandableListView expListView = (ExpandableListView) v.findViewById(R.id.expandableListViewMovimentos);
        ExpandableListAdapter expListAdapter = new MovimentoExpandableAdapter(this.getActivity(), movimentos);
        expListView.setAdapter(expListAdapter);
		
		return v;
	}
	
}