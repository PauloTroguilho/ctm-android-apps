package com.ctm.eadvogado.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ctm.eadvogado.PreferencesActivity;
import com.ctm.eadvogado.ProcessoRootFragment;
import com.ctm.eadvogado.R;
import com.ctm.eadvogado.adapters.MovimentoExpandableAdapter;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.Processo;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.TipoDocumento;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.TipoMovimentoProcessual;
import com.ctm.eadvogado.endpoints.processoEndpoint.model.TipoProcessoJudicial;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

/**
 * @author Cleber
 *
 */
public class TabProcessoMovimentoFragment extends SherlockFragment {
	
	Processo processo;

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		processo = ProcessoRootFragment.PROCESSO;
	}
	
	/**
	 * @param admobViewId
	 */
	protected void initAdmobBanner(int admobViewId, View parentView) {
		if (!PreferencesActivity.isContaPremium(getActivity())) {
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
		TipoProcessoJudicial processoJudicial = processo.getProcessoJudicial();
		Map<TipoMovimentoProcessual, List<TipoDocumento>> movimentosDocumentosMap = 
				buildMovimentosDocumentosMap(processoJudicial);
		
		ExpandableListView expListView = (ExpandableListView) v.findViewById(R.id.expandableListViewMovimentos);
		ExpandableListAdapter expListAdapter = new MovimentoExpandableAdapter(
				this.getActivity(), processoJudicial.getMovimento(),
				movimentosDocumentosMap);
        expListView.setAdapter(expListAdapter);
		
		return v;
	}
	
	/**
	 * @param processoJudicial
	 * @return
	 */
	private Map<TipoMovimentoProcessual, List<TipoDocumento>> buildMovimentosDocumentosMap(TipoProcessoJudicial processoJudicial) {
		Map<TipoMovimentoProcessual, List<TipoDocumento>> movimentosDocumentosMap = new HashMap<TipoMovimentoProcessual, List<TipoDocumento>>();
		if (processoJudicial.getMovimento() != null) {
			for (TipoMovimentoProcessual movimento : processoJudicial.getMovimento()) {
				List<TipoDocumento> documentosVinculados = new ArrayList<TipoDocumento>();
				if (movimento.getIdDocumentoVinculado() != null && processoJudicial.getDocumento() != null) {
					for (String idDocumentoVinculado : movimento.getIdDocumentoVinculado()) {
						for (TipoDocumento tipoDocumento : processoJudicial.getDocumento()) {
							if (idDocumentoVinculado.equals(tipoDocumento.getIdDocumento())) {
								documentosVinculados.add(tipoDocumento);
								break;
							}
						}
					}
				}
				movimentosDocumentosMap.put(movimento, documentosVinculados);
			}
		}
		return movimentosDocumentosMap;
	}
	
}