package br.com.ctm.guiamedico.model;

import java.io.Serializable;

/**
 * Superclasse que server de base para todas as entidades da aplicação. 
 * Possui o atributo Id, comum a todas as entidades, porem as anotações devem ser definidas na
 * classe concreta.
 * Implementa hashcode e equals baseado no id.
 * 
 * @author Cleber
 *
 */
public abstract class BaseEntity implements Serializable, Comparable<BaseEntity>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1954018514051702076L;

	protected Long id;
	
	/**
	 * 
	 */
	public BaseEntity() {
		super();
	}

	/**
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(BaseEntity entity) {
		Long thisId = null;
        Long paramId = null;
        int comparacao = 0;

        if (this.getId() != null) {
            thisId = this.getId();
        }

        if (entity != null && entity.getId() != null) {
            paramId = entity.getId();
        }

        if (thisId == null && paramId != null) {
            comparacao = -1;
        } else if (thisId != null && paramId == null) {
            comparacao = 1;
        } else if (thisId == null && paramId == null) {
            comparacao = 0;
        } else {
            comparacao = thisId.compareTo(paramId);
        }

        return comparacao;
	}
	
	

}
