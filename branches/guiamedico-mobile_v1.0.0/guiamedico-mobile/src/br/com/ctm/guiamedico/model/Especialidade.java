/**
 * 
 */
package br.com.ctm.guiamedico.model;


/**
 * @author Cleber
 * 
 */
public class Especialidade extends AuditedEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nome;
	private String descricao;

	/**
	 * 
	 */
	public Especialidade() {
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
	public void setNome(String titulo) {
		this.nome = titulo;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao
	 *            the senha to set
	 */
	public void setDescricao(String cpf) {
		this.descricao = cpf;
	}

}
