/**
 * 
 */
package com.ctm.eadvogado.model;

import javax.persistence.Entity;

import com.google.appengine.api.datastore.Key;

/**
 * @author Cleber
 * 
 */
@Entity
public class Documento extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private Key processo;

	/**
	 * 
	 */
	public Documento() {

	}
	

}
