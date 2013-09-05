package com.ctm.eadvogado.comparators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import br.jus.cnj.pje.v1.TipoMovimentoProcessual;

public class TipoMovimentoProcessualComparator implements
		Comparator<TipoMovimentoProcessual> {
	
	public static SimpleDateFormat movimentoDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	@Override
	public int compare(TipoMovimentoProcessual o1, TipoMovimentoProcessual o2) {
		Date dm1 = null;
		if (o1.getDataHora() != null) {
			try {
				dm1 = movimentoDateFormat.parse(
					o1.getDataHora().replaceAll("[-./:]", ""));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Date dm2 = null;
		if (o2.getDataHora() != null) {
			try {
				dm2 = movimentoDateFormat.parse(
					o2.getDataHora().replaceAll("[-./:]", ""));
			} catch (ParseException e) {
				e.printStackTrace();
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