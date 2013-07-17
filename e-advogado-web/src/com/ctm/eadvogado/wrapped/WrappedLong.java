/**
 * 
 */
package com.ctm.eadvogado.wrapped;

import java.io.Serializable;

/**
 * @author Cleber
 *
 */
public class WrappedLong implements Serializable {
	
	private Long value;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrappedLong() {
		super();
	}

	public WrappedLong(Long value) {
		this.value = value;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
	
	
}
