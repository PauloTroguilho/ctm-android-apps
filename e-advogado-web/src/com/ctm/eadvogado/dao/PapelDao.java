/**
 * 
 */
package com.ctm.eadvogado.dao;

import javax.inject.Named;

import com.ctm.eadvogado.model.Papel;

/**
 * @author Cleber
 *
 */
@Named
public class PapelDao extends BaseDao<Papel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param entityClass
	 */
	public PapelDao() {
		super(Papel.class);
	}

}
