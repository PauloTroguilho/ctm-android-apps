package com.ctm.eadvogado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;
import br.jus.cnj.pje.v1.TipoProcessoJudicial;

import com.ctm.eadvogado.util.PJeServiceUtil;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;
import com.google.appengine.datanucleus.query.JPACursorHelper;

@Api(name = "processoendpoint", namespace = @ApiNamespace(ownerDomain = "ctm.com", ownerName = "ctm.com", packagePath = "eadvogado"))
public class ProcessoEndpoint {
	
	private static Cache cache;
	
	
	/**
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Cache getCache() {
		if (cache == null) {
			Map props = new HashMap();
	        props.put(GCacheFactory.EXPIRATION_DELTA, 60 * 60);
	        try {
	            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
	            cache = cacheFactory.createCache(props);
	        } catch (CacheException e) {
	            e.printStackTrace();
	        }
		}
        return cache;
	}
	
	/**
	 * @param npu
	 * @param tipoJuizo
	 * @param idTribunal
	 * @return
	 */
	private String getProcessoCacheKey(String npu, String tipoJuizo, 
			Long idTribunal) {
		return String.format("%s-%s-%s", npu, tipoJuizo, idTribunal);
	}
	
	/**
	 * @param npu
	 * @param tipoJuizo
	 * @param idTribunal
	 * @return
	 */
	private TipoProcessoJudicial getProcessoJudicialFromCache(String npu, String tipoJuizo, 
			Long idTribunal) {
		TipoProcessoJudicial processoJudicial = null;
		String processoCacheKey = getProcessoCacheKey(npu, tipoJuizo, idTribunal);
		Cache cache = getCache();
		if (cache != null && cache.containsKey(processoCacheKey)) {
			processoJudicial = (TipoProcessoJudicial) cache.get(processoCacheKey);
		}
		return processoJudicial;
	}
	
	/**
	 * @param npu
	 * @param tipoJuizo
	 * @param idTribunal
	 * @param processoJudicial
	 */
	private void putProcessoJudicialToCache(String npu, String tipoJuizo, 
			Long idTribunal, TipoProcessoJudicial processoJudicial) {
		String processoCacheKey = getProcessoCacheKey(npu, tipoJuizo, idTribunal);
		Cache cache = getCache();
		cache.put(processoCacheKey, processoJudicial);
	}
	
	

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listProcesso")
	public CollectionResponse<Processo> listProcesso(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Processo> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Processo as Processo");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Processo>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Processo obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Processo> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}
	
	
	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 * @throws UnauthorizedException 
	 * @throws NotFoundException 
	 */
	@ApiMethod(name = "listProcessoNPUsPorAdvogado")
	public List<Processo> listProcessoNPUsPorAdvogado(
			@Named("email") String email,
			@Named("senha") String senha) throws NotFoundException, UnauthorizedException {
		AdvogadoEndpoint advEndpoint = new AdvogadoEndpoint();
		Advogado advogado = advEndpoint.autenticarAdvogado(email, senha);
		EntityManager mgr = null;
		List<Processo> result = new ArrayList<Processo>();

		try {
			mgr = getEntityManager();
			for (Key processoKey : advogado.getProcessos()) {
				Processo p = mgr.find(Processo.class, processoKey.getId());
				result.add(p);
			}
		} finally {
			mgr.close();
		}
		return result;
	}
	
	

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getProcesso")
	public Processo getProcesso(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Processo processo = null;
		try {
			processo = mgr.find(Processo.class, id);
		} finally {
			mgr.close();
		}
		return processo;
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
	
	private static Processo queryProcesso(EntityManager entityManager, String npu, String tipoJuizo, 
			Tribunal tribunal) throws NoResultException{
		Query query = entityManager.createQuery("SELECT p FROM Processo p WHERE p.npu = :npu and p.tipoJuizo = :tipoJuizo and p.tribunal = :tribunal");
		query.setParameter("npu", npu);
		query.setParameter("tipoJuizo", TipoJuizo.valueOf(tipoJuizo));
		query.setParameter("tribunal", tribunal.getId());
		return (Processo) query.getSingleResult();
	}
	
	/**
	 * Busca um processo pelo NPU
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 * @throws UnauthorizedException 
	 * @throws NotFoundException 
	 */
	@ApiMethod(name = "associarProcesso")
	public Processo associarProcesso(@Named("npu") String npu, @Named("tipoJuizo") String tipoJuizo, 
			@Named("idTribunal") Long idTribunal, @Named("email") String email) throws NotFoundException {
		EntityManager mgr = getEntityManager();
		Processo processo = null;
		try {
			Advogado advogado = queryAdvogadoPorEmail(mgr, email);
			Tribunal tribunal = mgr.find(Tribunal.class, idTribunal);
			processo = queryProcesso(mgr, npu, tipoJuizo, tribunal);
			if (processo != null) {
				advogado.getProcessos().add(processo.getId());
				mgr.persist(advogado);
			} else {
				throw new NotFoundException(String.format("Nao foi possivel localizar processo com o NPU: %s", npu));
			}
		} catch(NoResultException e) {
			e.printStackTrace();
			throw new NotFoundException(String.format("Nao foi possivel localizar processo com o NPU: %s", npu));
		} finally {
			mgr.close();
		}
		return processo;
	}
	
	/**
	 * Busca um processo pelo NPU
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getProcessoPorNPU", path = "getProcessoPorNPU/{npu}")
	public Processo getProcessoPorNPU(@Named("npu") String npu) {
		EntityManager mgr = getEntityManager();
		Processo processo = null;
		try {
			Query query = mgr.createQuery("SELECT p FROM Processo p WHERE p.npu = :npu");
			query.setParameter("npu", npu);
			processo = (Processo) query.getSingleResult();
		} catch(NoResultException e) {
			e.printStackTrace();
		} finally {
			mgr.close();
		}
		return processo;
	}
	
	/**
	 * Busca um processo pelo NPU
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "consultarProcesso")
	public Processo consultarProcesso(@Named("npu") String npu, @Named("tipoJuizo") String tipoJuizo, 
			@Named("idTribunal") Long idTribunal) throws NotFoundException {
		Processo processo = null;
		TipoProcessoJudicial processoJudicial = null;
		EntityManager mgr = getEntityManager();
		try {
			processoJudicial = getProcessoJudicialFromCache(npu, tipoJuizo, idTribunal);
			TipoJuizo aTipoJuizo = TipoJuizo.valueOf(tipoJuizo);
			Tribunal tribunal = mgr.find(Tribunal.class, idTribunal);
			
			if (processoJudicial == null) {
				if (tribunal == null) {
					throw new NotFoundException("O tribunal não foi encontrado.");
				}
				String endpoint = null;
				switch (aTipoJuizo) {
					case PRIMEIRO_GRAU:
						endpoint = tribunal.getPje1gEndpoint();
						break;
					case SEGUNDO_GRAU:
						endpoint = tribunal.getPje2gEndpoint();
						break;
				}
				if (endpoint != null) {
					try {
						processoJudicial = PJeServiceUtil.consultarProcessoJudicial(endpoint, npu);
					} catch(Exception e) {
						throw new NotFoundException("Não foi possível consultar este processo neste momento");
					}
				} else {
					throw new NotFoundException("Serviço não disponivel para o Tipo de Juízo informado.");
				}
				try {
					putProcessoJudicialToCache(npu, tipoJuizo, idTribunal, processoJudicial);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			if (processoJudicial != null) {
				try {
					processo = queryProcesso(mgr, npu, tipoJuizo, tribunal);
				} catch (NoResultException nre) {}
				if (processo == null) {
					processo = new Processo();
					processo.setNpu(npu);
					processo.setTipoJuizo(TipoJuizo.valueOf(tipoJuizo));
					processo.setTribunal(tribunal.getId());
					processo.setProcessoJudicial(processoJudicial);
					mgr.persist(processo);
				} else if (processo.getProcessoJudicial() == null) {
					processo.setProcessoJudicial(processoJudicial);
					mgr.persist(processo);
				}
			}
			
		} catch(NoResultException e) {
			throw new NotFoundException("O tribunal não foi encontrado.", e);
		} finally {
			mgr.close();
		}
		
		return processo;
	}
	
	

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param processo the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertProcesso")
	public Processo insertProcesso(Processo processo) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsProcesso(processo)) {
				throw new EntityExistsException("Object already exists");
			}
			Tribunal tribunal = null;
			if (processo.getTribunal() != null){
				tribunal = mgr.find(Tribunal.class, processo.getTribunal().getId());
				processo.setTribunal(tribunal.getId());
			}
			mgr.persist(processo);
			tribunal.getProcessos().add(processo.getId());
			mgr.persist(tribunal);
		} finally {
			mgr.close();
		}
		return processo;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param processo the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateProcesso")
	public Processo updateProcesso(Processo processo) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsProcesso(processo)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.merge(processo);
		} finally {
			mgr.close();
		}
		return processo;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 * @return The deleted entity.
	 */
	@ApiMethod(name = "removeProcesso")
	public Processo removeProcesso(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Processo processo = null;
		try {
			processo = mgr.find(Processo.class, id);
			mgr.remove(processo);
		} finally {
			mgr.close();
		}
		return processo;
	}

	private boolean containsProcesso(Processo processo) {
		if (processo.getId() == null)
			return false;
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Processo item = mgr.find(Processo.class, processo.getId().getId());
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
