/**
 * 
 */
package com.ctm.eadvogadojsf.model;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Persistent;
import javax.persistence.Entity;

import com.google.appengine.api.datastore.Key;

/**
 * @author Cleber
 * 
 */
@Entity
public class Advogado extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String email;
	private String senha;
	private String nome;
	private String numeroOab;
	private Set<Key> processos = new HashSet<Key>();

	/**
	 * 
	 */
	public Advogado() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumeroOab() {
		return numeroOab;
	}

	public void setNumeroOab(String numeroOab) {
		this.numeroOab = numeroOab;
	}
	
	@Persistent
	public Set<Key> getProcessos() {
		if (processos == null)
			processos = new HashSet<Key>();
		return processos;
	}

	public void setProcessos(Set<Key> processos) {
		this.processos = processos;
	}

}
