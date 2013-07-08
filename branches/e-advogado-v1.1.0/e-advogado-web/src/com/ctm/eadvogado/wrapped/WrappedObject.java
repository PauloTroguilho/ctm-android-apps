/**
 * 
 */
package com.ctm.eadvogado.wrapped;

import java.io.Serializable;

/**
 * @author Cleber
 *
 */
public abstract class WrappedObject<T extends Serializable> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private T value;

	/**
	 * 
	 */
	public WrappedObject() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	public WrappedObject(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
