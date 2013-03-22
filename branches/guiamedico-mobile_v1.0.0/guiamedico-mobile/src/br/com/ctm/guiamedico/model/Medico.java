/**
 * 
 */
package br.com.ctm.guiamedico.model;

import java.util.Set;

/**
 * @author Cleber
 * 
 */
public class Medico extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nome;
	private String cpf;
	private Set<Especialidade> especialidades;

	/**
	 * 
	 */
	public Medico() {
	}
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf
	 *            the senha to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Set<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(Set<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

}
