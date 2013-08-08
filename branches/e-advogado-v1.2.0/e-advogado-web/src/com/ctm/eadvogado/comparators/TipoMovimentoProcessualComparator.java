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
		try {
			dm1 = movimentoDateFormat.parse(o1.getDataHora());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date dm2 = null;
		try {
			dm2 = movimentoDateFormat.parse(o2.getDataHora());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dm1.compareTo(dm2);
	}
	
}