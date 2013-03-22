package br.com.ctm.guiamedico.model;


/**
 * Superclasse {@link MappedSuperclass} que server de base para todas as
 * entidades que necessitam suportar exclusão lógica.
 * 
 * @author Cleber
 * 
 */
public abstract class AuditedEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1954018514051702076L;

	/**
	 * Enum que classifica se o registro está ativo
	 * 
	 * @author Cleber
	 * 
	 */
	public enum Status {
		INACTIVE, ACTIVE;
	};

	private String dataCriacao;
	private String dataModificacao;

	private Status status = Status.ACTIVE;

	public String getDataCriacao() {
		return dataCriacao;
	}

	/**
	 * @return the {@link Status}
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @param dataCriacao
	 *            the dataCriacao to set
	 */
	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * @param dataModificacao
	 *            the dataModificacao to set
	 */
	public void setDataModificacao(String dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	public String getDataModificacao() {
		return dataModificacao;
	}

}
