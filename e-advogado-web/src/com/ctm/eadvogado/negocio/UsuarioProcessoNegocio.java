/**
 * 
 */
package com.ctm.eadvogado.negocio;

import javax.inject.Inject;
import javax.inject.Named;

import com.ctm.eadvogado.dao.UsuarioProcessoDao;
import com.ctm.eadvogado.model.UsuarioProcesso;

/**
 * @author Cleber
 * 
 */
@Named
public class UsuarioProcessoNegocio extends BaseNegocio<UsuarioProcesso, UsuarioProcessoDao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	public void setDao(UsuarioProcessoDao dao) {
		super.setDao(dao);
	}

}
