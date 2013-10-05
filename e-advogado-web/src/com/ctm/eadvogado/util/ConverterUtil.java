package com.ctm.eadvogado.util;

import java.util.List;

import br.jus.cnj.pje.v1.ModalidadePoloProcessual;
import br.jus.cnj.pje.v1.TipoCabecalhoProcesso;
import br.jus.cnj.pje.v1.TipoParte;
import br.jus.cnj.pje.v1.TipoPessoa;
import br.jus.cnj.pje.v1.TipoPoloProcessual;
import br.jus.cnj.pje.v1.TipoProcessoJudicial;

public class ConverterUtil {

	/**
	 * @param polo
	 * @return
	 */
	public static TipoPessoa getTipoPessoa(TipoPoloProcessual polo) {
		TipoPessoa pessoa = null;
		if (!polo.getParte().isEmpty()) {
			TipoParte parte = polo.getParte().iterator().next();
			pessoa = parte.getPessoa();
		}
		return pessoa;
	}

	/**
	 * @param processoJudicial
	 * @param ativos
	 * @param passivos
	 */
	public static void fillPolosProcessuais(
			TipoProcessoJudicial processoJudicial,
			List<TipoPoloProcessual> ativos, List<TipoPoloProcessual> passivos) {
		if (processoJudicial != null) {
			fillPolosProcessuais(processoJudicial.getDadosBasicos(), ativos,
					passivos);
		}
	}

	/**
	 * @param dadosBasicos
	 * @param ativos
	 * @param passivos
	 */
	public static void fillPolosProcessuais(TipoCabecalhoProcesso dadosBasicos,
			List<TipoPoloProcessual> ativos, List<TipoPoloProcessual> passivos) {
		if (dadosBasicos != null) {
			List<TipoPoloProcessual> polos = dadosBasicos.getPolo();
			if (polos != null && !polos.isEmpty()) {
				for (TipoPoloProcessual poloProcessual : polos) {
					if (poloProcessual.getPolo().equals(ModalidadePoloProcessual.AT)) {
						ativos.add(poloProcessual);
					} else if (poloProcessual.getPolo().equals(ModalidadePoloProcessual.PA)) {
						passivos.add(poloProcessual);
					}
				}
			}
		}
	}

}
