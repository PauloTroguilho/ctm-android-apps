/**
 * 
 */
package com.ctm.eadvogado.model;

import java.io.Serializable;

/**
 * @author Cleber
 * 
 */
public class Documento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String idDocumento;
	private String mimeType;
	private String conteudo;

	/**
	 * 
	 */
	public Documento() {

	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
}
