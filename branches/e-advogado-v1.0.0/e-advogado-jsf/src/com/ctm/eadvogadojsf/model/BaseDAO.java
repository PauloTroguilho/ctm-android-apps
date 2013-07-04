/**
 * 
 */
package com.ctm.eadvogadojsf.model;

import java.io.Serializable;

/**
 * @author ctm
 *
 */
public class BaseDAO<E extends BaseEntity> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Class<E> entityClass;

	/**
	 * 
	 */
	public BaseDAO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param entityClass
	 */
	public BaseDAO(Class<E> entityClass) {
		super();
		this.entityClass = entityClass;
	}
	
	

}
