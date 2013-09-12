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
public class Papel extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String descricao;

	/**
	 * 
	 */
	public Papel() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	

}
