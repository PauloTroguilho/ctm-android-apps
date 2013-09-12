/**
 * 
 */
package com.ctm.eadvogado.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Persistent;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.appengine.api.datastore.Key;

/**
 * @author Cleber
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(
		name = "tribunaisNaoAtualizadosDataAtualizacaoNull",
		query = "select t from Tribunal as t where t.dataAtualizacao is null"
	),
	@NamedQuery(
		name = "tribunaisNaoAtualizadosDataAtualizacaoIntervalo",
		query = "select t from Tribunal as t where t.dataAtualizacao <= :intervalo"
	)
})
public class Tribunal extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private String nome;
	private String sigla;
	private String pje1gEndpoint;
	private String pje2gEndpoint;
	private Date dataAtualizacao;
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

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

}
