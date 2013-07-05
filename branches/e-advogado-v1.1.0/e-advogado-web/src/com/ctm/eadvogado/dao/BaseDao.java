/**
 * 
 */
package com.ctm.eadvogado.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.ctm.eadvogado.interceptors.Transacional;
import com.ctm.eadvogado.model.BaseEntity;
import com.ctm.eadvogado.qualifiers.EntityManagerQualifier;

/**
 * @author Cleber
 *
 */
public abstract class BaseDao<E extends BaseEntity> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Tipo de ordenação.
	 * @author Cleber
	 *
	 */
	public static enum SortOrder {
		ASC, DESC;
	}
	
	protected Class<E> entityClass;
	
	@Inject @EntityManagerQualifier
	protected EntityManager entityManager;

	/**
	 * @param entityClass
	 */
	public BaseDao(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * Insere uma entidade
	 * @param entity
	 * @return
	 * @throws PersistenceException
	 */
	@Transacional
	public E insert(E entity) throws PersistenceException {
		entityManager.persist(entity);
		return entity;
	}
	
	/**
	 * Altera uma entidade
	 * @param entity
	 * @return
	 * @throws PersistenceException
	 */
	@Transacional
	public E update(E entity) throws PersistenceException {
		entityManager.merge(entity);
		return entity;
	}
	
	/**
	 * Remove uma entidade
	 * @param entity
	 * @return
	 * @throws PersistenceException
	 */
	@Transacional
	public E remove(E entity) throws PersistenceException {
		entityManager.remove(entity);
		return entity;
	}
	
	/**
	 * Faz um refresh na entidade.
	 * @param entity
	 * @return
	 * @throws PersistenceException
	 */
	@Transacional
	public E refresh(E entity) throws PersistenceException {
		entityManager.refresh(entity);
		return entity;
	}
	
	/**
	 * @param id
	 * @return
	 * @throws PersistenceException
	 */
	public E findByID(Long id) throws PersistenceException {
		return entityManager.find(entityClass, id);
	}
	
	/**
	 * @param sortField
	 * @param sortOrder
	 * @return
	 * @throws PersistenceException
	 */
	@SuppressWarnings("unchecked")
	public List<E> findAll(String sortField, SortOrder sortOrder) throws PersistenceException {
		String strQuery = "select e from " + entityClass.getSimpleName() + " as e ";
		if (sortField != null && sortOrder != null) {
			strQuery+= "order by e." + sortField + " " + sortOrder.name();
		}
		Query query = entityManager.createQuery(strQuery);
		return query.getResultList();
	}
	
}
