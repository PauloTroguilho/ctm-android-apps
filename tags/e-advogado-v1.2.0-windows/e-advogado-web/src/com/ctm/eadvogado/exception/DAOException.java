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
public class DAOException extends PersistenceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> erros = new ArrayList<String>();

	/**
	 * 
	 */
	public DAOException() {
	}
	
	public DAOException(List<String> erros) {
		super();
		this.erros = erros;
	}

	/**
	 * @param message
	 */
	public DAOException(String message) {
		super(message);
		erros.add(message);
	}

	/**
	 * @param cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DAOException(String message, Throwable cause) {
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
