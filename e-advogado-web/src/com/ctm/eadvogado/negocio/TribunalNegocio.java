/**
 * 
 */
package com.ctm.eadvogado.negocio;

import javax.inject.Inject;
import javax.inject.Named;

import com.ctm.eadvogado.dao.TribunalDao;
import com.ctm.eadvogado.model.Tribunal;

/**
 * @author Cleber
 *
 */
@Named
public class TribunalNegocio extends BaseNegocio<Tribunal, TribunalDao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	public void setDao(TribunalDao dao) {
		super.setDao(dao);
	}

}
