/**
 * 
 */
package com.ctm.eadvogado.negocio;

import javax.inject.Inject;
import javax.inject.Named;

import com.ctm.eadvogado.dao.PapelDao;
import com.ctm.eadvogado.model.Papel;

/**
 * @author Cleber
 *
 */
@Named
public class PapelNegocio extends BaseNegocio<Papel, PapelDao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	public void setDao(PapelDao dao) {
		super.setDao(dao);
	}

}
