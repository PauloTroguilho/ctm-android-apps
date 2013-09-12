/**
 * 
 */
package com.ctm.eadvogado.negocio;

import javax.inject.Inject;
import javax.inject.Named;

import com.ctm.eadvogado.dao.AdvogadoDao;
import com.ctm.eadvogado.model.Advogado;

/**
 * @author Cleber
 *
 */
@Named
public class AdvogadoNegocio extends BaseNegocio<Advogado, AdvogadoDao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	public void setDao(AdvogadoDao dao) {
		super.setDao(dao);
	}

}
