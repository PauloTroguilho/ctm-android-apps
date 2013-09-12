package com.ctm.eadvogado.comparators;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.jus.cnj.pje.v1.TipoMovimentoProcessual;

public class TipoMovimentoProcessualComparator implements
		Comparator<TipoMovimentoProcessual> {
	
	public static final Logger LOGGER = Logger.getLogger(TipoMovimentoProcessualComparator.class.getName());
	
	private SimpleDateFormat movimentoDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public int compare(TipoMovimentoProcessual o1, TipoMovimentoProcessual o2) {
		Date dm1 = null;
		if (o1.getDataHora() != null) {
			try {
				dm1 = movimentoDateFormat.parse(
					o1.getDataHora().replaceAll("[-./:]", ""));
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, String.format("Falha na conversao da data: %s", o1.getDataHora()), e);
			}
		}
		Date dm2 = null;
		if (o2.getDataHora() != null) {
			try {
				dm2 = movimentoDateFormat.parse(
					o2.getDataHora().replaceAll("[-./:]", ""));
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, String.format("Falha na conversao da data: %s", o2.getDataHora()), e);
			}
		}
		if (dm1 != null && dm2 != null) {
			return dm1.compareTo(dm2);
		} else if (dm1 != null) {
			return 1;
		} else if (dm2 != null) {
			return -1;
		} else {
			return 0;
		}
		
	}

}