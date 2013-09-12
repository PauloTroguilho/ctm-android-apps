/**
 * 
 */
package com.ctm.eadvogado.wrapped;

import java.io.Serializable;

/**
 * @author Cleber
 *
 */
public class WrappedBoolean implements Serializable {
	
	private Boolean value;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrappedBoolean() {
		super();
	}

	public WrappedBoolean(Boolean value) {
		this.value = value;
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

}
