/**
 * 
 */
package com.ctm.eadvogado.exception;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

/**
 * @author Cleber
 *
 */
public class NegocioException extends PersistenceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> erros = new ArrayList<String>();

	/**
	 * 
	 */
	public NegocioException() {
	}
	
	public NegocioException(List<String> erros) {
		super();
		this.erros = erros;
	}

	/**
	 * @param message
	 */
	public NegocioException(String message) {
		super(message);
		erros.add(message);
	}

	/**
	 * @param cause
	 */
	public NegocioException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NegocioException(String message, Throwable cause) {
		super(message, cause);
		erros.add(message);
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}

}
