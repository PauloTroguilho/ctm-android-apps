package com.ctm.eadvogado.endpoints;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.ctm.eadvogado.dao.BaseDao;
import com.ctm.eadvogado.dao.BaseDao.SortOrder;
import com.ctm.eadvogado.model.BaseEntity;
import com.ctm.eadvogado.negocio.BaseNegocio;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.CollectionResponse;

public abstract class BaseEndpoint<E extends BaseEntity, Negocio extends BaseNegocio<E, ? extends BaseDao<E>>> {
	
	protected static Logger logger = Logger.getLogger(BaseEndpoint.class.getSimpleName());
	
	private Negocio negocio;
	
	protected void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}
	protected Negocio getNegocio() {
		return negocio;
	}

	/**
	 * Busca todos os registros
	 *
	 * @param firstReault
	 * @param maxResults
	 * @param sortField
	 * @param sortOrder
	 * 
	 * @return A CollectionResponse class containing the list of all entities persisted and a cursor to the next page.
	 */
	@ApiMethod(name = "listAll")
	public CollectionResponse<E> listAll(
			@Nullable @Named("firstReault") Integer firstReault,
			@Nullable @Named("maxResults") Integer maxResults,
			@Nullable @Named("sortField") String sortField,
			@Nullable @Named("sortOrder") SortOrder sortOrder) {
		
		List<E> resultList = negocio.findAll(sortField, sortOrder);
		return CollectionResponse.<E> builder().setItems(resultList).build();
	}
	
	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getByID")
	public E getByID(@Named("id") Long id) {
		return negocio.findByID(id);
	}
	
	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param entidade the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insert")
	public E insert(E entidade) {
		return negocio.insert(entidade);
	}
	
	/**
	 * This updates a entity into App Engine datastore.
	 * It uses HTTP POST method.
	 *
	 * @param entidade the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "update")
	public E update(E entidade) {
		return negocio.update(entidade);
	}
	
	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 * @return The deleted entity.
	 */
	@ApiMethod(name = "remove")
	public E remove(@Named("id") Long id) {
		E entidade = negocio.findByID(id);
		if (entidade != null) {
			negocio.remove(entidade);
		}
		return entidade;
	}

}
