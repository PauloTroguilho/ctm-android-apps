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
public class Produto extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String identificador;
	private String descricao;

	/**
	 * 
	 */
	public Produto() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
