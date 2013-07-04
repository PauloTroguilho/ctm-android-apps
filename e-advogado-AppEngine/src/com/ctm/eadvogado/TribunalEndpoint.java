package com.ctm.eadvogado;

import com.ctm.eadvogado.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "tribunalendpoint", namespace = @ApiNamespace(ownerDomain = "ctm.com", ownerName = "ctm.com", packagePath = "eadvogado"))
public class TribunalEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listTribunal")
	public CollectionResponse<Tribunal> listTribunal(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Tribunal> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Tribunal as Tribunal order by Tribunal.sigla asc");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Tribunal>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Tribunal obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Tribunal> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getTribunal")
	public Tribunal getTribunal(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Tribunal tribunal = null;
		try {
			tribunal = mgr.find(Tribunal.class, id);
		} finally {
			mgr.close();
		}
		return tribunal;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param tribunal the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertTribunal")
	public Tribunal insertTribunal(Tribunal tribunal) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsTribunal(tribunal)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(tribunal);
		} finally {
			mgr.close();
		}
		return tribunal;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param tribunal the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateTribunal")
	public Tribunal updateTribunal(Tribunal tribunal) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsTribunal(tribunal)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(tribunal);
		} finally {
			mgr.close();
		}
		return tribunal;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 * @return The deleted entity.
	 */
	@ApiMethod(name = "removeTribunal")
	public Tribunal removeTribunal(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Tribunal tribunal = null;
		try {
			tribunal = mgr.find(Tribunal.class, id);
			mgr.remove(tribunal);
		} finally {
			mgr.close();
		}
		return tribunal;
	}

	private boolean containsTribunal(Tribunal tribunal) {
		if (tribunal.getId() == null)
			return false;
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Tribunal item = mgr.find(Tribunal.class, tribunal.getId());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
