/**
 * 
 */
package com.ctm.eadvogado.dao;

import javax.inject.Named;

import com.ctm.eadvogado.model.Advogado;

/**
 * @author Cleber
 *
 */
@Named
public class AdvogadoDao extends BaseDao<Advogado> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param entityClass
	 */
	public AdvogadoDao() {
		super(Advogado.class);
	}

}
