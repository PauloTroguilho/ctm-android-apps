package com.ctm.eadvogado.fragment;

import java.text.NumberFormat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.ctm.eadvogado.ProcessoTabsPagerFragment;
import com.ctm.eadvogado.R;
import com.ctm.eadvogado.dto.ProcessoDTO;
import com.ctm.eadvogado.dto.TipoJuizo;
import com.ctm.eadvogado.processoendpoint.model.TipoCabecalhoProcesso;
import com.ctm.eadvogado.util.Consts;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

/**
 * @author Cleber
 *
 */
public class TabProcessoDadosFragment extends SherlockFragment {
	
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
		View v = inflater.inflate(R.layout.processo_tab_dados, container, false);
		initAdmobBanner(R.id.adView, v);
		TipoCabecalhoProcesso dadosBasicos = processoDTO.getProcesso().getProcessoJudicial().getDadosBasicos();
		TextView tvNPU = (TextView) v.findViewById(R.id.tvTabDados_NPU);
		tvNPU.setText(dadosBasicos.getNumero());
		TextView tvTribunal = (TextView) v.findViewById(R.id.tvTabDados_Tribunal);
		tvTribunal.setText(processoDTO.getTribunal().getNome());
		TextView tvTipoJuizo = (TextView) v.findViewById(R.id.tvTabDados_TipoJuizo);
		if (processoDTO.getProcesso().getTipoJuizo().equals(TipoJuizo.PRIMEIRO_GRAU.name())) {
			tvTipoJuizo.setText(R.string.processo_tipoJuizo_1g);
		} else {
			tvTipoJuizo.setText(R.string.processo_tipoJuizo_2g);
		}
		TextView tvOrgao = (TextView) v.findViewById(R.id.tvTabDados_OrgaoJulg);
		tvOrgao.setText(dadosBasicos.getCodigoOrgaoJulgador());
		TextView tvClasse = (TextView) v.findViewById(R.id.tvTabDados_ClasseProc);
		tvClasse.setText(dadosBasicos.getClasseProcessual() + "");
		TextView tvValor = (TextView) v.findViewById(R.id.tvTabDados_ValorCausa);
		tvValor.setText(NumberFormat.getCurrencyInstance().format(
				dadosBasicos.getValorCausa()));
		return v;
	}
	
}