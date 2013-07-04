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
public class Tribunal extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private String nome;
	private String sigla;
	private String pje1gEndpoint;
	private String pje2gEndpoint;
	private Set<Key> processos = new HashSet<Key>();

	/**
	 * 
	 */
	public Tribunal() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getPje1gEndpoint() {
		return pje1gEndpoint;
	}

	public void setPje1gEndpoint(String pje1gEndpoint) {
		this.pje1gEndpoint = pje1gEndpoint;
	}

	public String getPje2gEndpoint() {
		return pje2gEndpoint;
	}

	public void setPje2gEndpoint(String pje2gEndpoint) {
		this.pje2gEndpoint = pje2gEndpoint;
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
