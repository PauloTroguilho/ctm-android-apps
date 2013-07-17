package com.ctm.eadvogado;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

@Api(name = "advogadoendpoint", namespace = @ApiNamespace(ownerDomain = "ctm.com", ownerName = "ctm.com", packagePath = "eadvogado"))
public class AdvogadoEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listAdvogado")
	public CollectionResponse<Advogado> listAdvogado(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Advogado> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Advogado as Advogado");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Advogado>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Advogado obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Advogado> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getAdvogado")
	public Advogado getAdvogado(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Advogado advogado = null;
		try {
			advogado = mgr.find(Advogado.class, id);
		} finally {
			mgr.close();
		}
		return advogado;
	}
	
	

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param advogado the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertAdvogado")
	public Advogado insertAdvogado(Advogado advogado) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsAdvogado(advogado)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(advogado);
		} finally {
			mgr.close();
		}
		return advogado;
	}
	
	/**
	 * This method authenticates the user
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "autenticarAdvogado")
	public Advogado autenticarAdvogado(@Named("email") String email, @Named("senha") String senha) 
			throws NotFoundException, UnauthorizedException{
		EntityManager mgr = getEntityManager();
		Advogado advogado = null;
		try {
			advogado = queryAdvogadoPorEmail(mgr, email);
			if (advogado != null) {
				if (!advogado.getSenha().equals(senha)) {
					throw new UnauthorizedException(String.format("Senha inválida para o email: %s", email));
				} 
			} else {
				throw new NotFoundException(String.format("Usuario nao encontrado com o email: %s", email));
			}
		} catch(NoResultException e){
			throw new NotFoundException(String.format("Usuario nao encontrado com o email: %s", email));
		} finally {
			mgr.close();
		}
		return advogado;
	}
	
	
	/**
	 * @param entityManager
	 * @param email
	 * @return
	 * @throws NoResultException
	 */
	private static Advogado queryAdvogadoPorEmail(EntityManager entityManager, String email) throws NoResultException{
		Query query = entityManager.createQuery("SELECT a FROM Advogado a where a.email = :email");
		query.setParameter("email", email.toLowerCase());
		return (Advogado) query.getSingleResult();
	}
	

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param advogado the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateAdvogado")
	public Advogado updateAdvogado(Advogado advogado) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsAdvogado(advogado)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.merge(advogado);
		} finally {
			mgr.close();
		}
		return advogado;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 * @return The deleted entity.
	 */
	@ApiMethod(name = "removeAdvogado")
	public Advogado removeAdvogado(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Advogado advogado = null;
		try {
			advogado = mgr.find(Advogado.class, id);
			mgr.remove(advogado);
		} finally {
			mgr.close();
		}
		return advogado;
	}

	private boolean containsAdvogado(Advogado advogado) {
		if (advogado.getId() == null)
			return false;
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Advogado item = mgr.find(Advogado.class, advogado.getId());
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
