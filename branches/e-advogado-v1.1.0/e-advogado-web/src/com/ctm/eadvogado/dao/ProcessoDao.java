/**
 * 
 */
package com.ctm.eadvogado.dao;

import javax.inject.Named;

import com.ctm.eadvogado.model.Processo;

/**
 * @author Cleber
 *
 */
@Named
public class ProcessoDao extends BaseDao<Processo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param entityClass
	 */
	public ProcessoDao() {
		super(Processo.class);
	}

}
