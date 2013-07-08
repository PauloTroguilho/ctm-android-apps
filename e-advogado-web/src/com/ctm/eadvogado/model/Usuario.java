/**
 * 
 */
package com.ctm.eadvogado.model;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Persistent;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.google.appengine.api.datastore.Key;

/**
 * @author Cleber
 * 
 */
@Entity
@NamedQueries(
	@NamedQuery(name = "usuarioPorEmail", query = "select usu from Usuario as usu where usu.email = :email")
)
public class Usuario extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String email;
	private String senha;
	private TipoConta tipoConta;
	private Set<Key> papeis = new HashSet<Key>();
	private Set<Key> processos = new HashSet<Key>();
	private Key advogado;

	/**
	 * 
	 */
	public Usuario() {
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
	@Persistent
	public TipoConta getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}

	@Persistent
	public Set<Key> getPapeis() {
		if (papeis == null)
			papeis = new HashSet<Key>();
		return papeis;
	}

	public void setPapeis(Set<Key> papeis) {
		this.papeis = papeis;
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

	@Persistent
	public Key getAdvogado() {
		return advogado;
	}

	public void setAdvogado(Key advogado) {
		this.advogado = advogado;
	}

}
