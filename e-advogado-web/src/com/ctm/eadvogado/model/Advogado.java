/**
 * 
 */
package com.ctm.eadvogado.model;

import javax.persistence.Entity;

/**
 * @author Cleber
 * 
 */
@Entity
public class Advogado extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String numeroOab;
	private String nome;

	/**
	 * 
	 */
	public Advogado() {
	}

	public String getNumeroOab() {
		return numeroOab;
	}

	public void setNumeroOab(String numeroOab) {
		this.numeroOab = numeroOab;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
