/**
 * 
 */
package com.ctm.eadvogado.dao;

import javax.inject.Named;

import com.ctm.eadvogado.model.Tribunal;

/**
 * @author Cleber
 *
 */
@Named
public class TribunalDao extends BaseDao<Tribunal> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param entityClass
	 */
	public TribunalDao() {
		super(Tribunal.class);
	}

}
