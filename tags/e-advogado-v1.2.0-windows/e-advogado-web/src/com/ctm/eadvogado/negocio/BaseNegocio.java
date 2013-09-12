package com.ctm.eadvogado.negocio;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.PersistenceException;

import com.ctm.eadvogado.dao.BaseDao;
import com.ctm.eadvogado.dao.BaseDao.SortOrder;
import com.ctm.eadvogado.interceptors.Transacional;
import com.ctm.eadvogado.model.BaseEntity;

/**
 * @author Cleber
 *
 * @param <E>
 */
public abstract class BaseNegocio<E extends BaseEntity, DAO extends BaseDao<E>> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static Logger logger = Logger.getLogger(BaseNegocio.class.getSimpleName());
	
	protected DAO dao;

	protected DAO getDao() {
		return dao;
	}

	protected void setDao(DAO dao) {
		this.dao = dao;
	}

	/**
	 * Insere uma entidade
	 * @param entity
	 * @return
	 * @throws PersistenceException
	 */
	@Transacional
	public E insert(E entity) throws PersistenceException {
		return getDao().insert(entity);
	}
	
	/**
	 * Altera uma entidade
	 * @param entity
	 * @return
	 * @throws PersistenceException
	 */
	@Transacional
	public E update(E entity) throws PersistenceException {
		return getDao().update(entity);
	}
	
	/**
	 * Remove uma entidade
	 * @param entity
	 * @return
	 * @throws PersistenceException
	 */
	@Transacional
	public E remove(E entity) throws PersistenceException {
		return getDao().remove(entity);
	}
	
	/**
	 * Faz um refresh na entidade.
	 * @param entity
	 * @return
	 * @throws PersistenceException
	 */
	@Transacional
	public E refresh(E entity) throws PersistenceException {
		return getDao().refresh(entity);
	}
	
	/**
	 * @param id
	 * @return
	 * @throws PersistenceException
	 */
	public E findByID(Long id) throws PersistenceException {
		return getDao().findByID(id);
	}
	
	/**
	 * @param sortField
	 * @param sortOrder
	 * @return
	 * @throws PersistenceException
	 */
	public List<E> findAll(String sortField, SortOrder sortOrder) throws PersistenceException {
		return getDao().findAll(sortField, sortOrder);
	}
	
	/**
	 * @param sortField
	 * @param sortOrder
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws PersistenceException
	 */
	public List<E> findAll(String sortField, SortOrder sortOrder, int firstResult, int maxResults) throws PersistenceException{
		return getDao().findAll(sortField, sortOrder, firstResult, maxResults);
	}
}
