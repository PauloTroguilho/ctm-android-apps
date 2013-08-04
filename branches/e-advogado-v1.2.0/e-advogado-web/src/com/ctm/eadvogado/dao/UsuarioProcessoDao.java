/**
 * 
 */
package com.ctm.eadvogado.dao;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.ctm.eadvogado.model.Processo;
import com.ctm.eadvogado.model.Usuario;
import com.ctm.eadvogado.model.UsuarioProcesso;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * @author Cleber
 *
 */
@Named
public class UsuarioProcessoDao extends BaseDao<UsuarioProcesso> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param entityClass
	 */
	public UsuarioProcessoDao() {
		super(UsuarioProcesso.class);
	}

	/**
	 * @param usuario
	 * @param processo
	 * @return
	 * @throws PersistenceException
	 */
	/**
	 * @param idUsuario
	 * @param idProcesso
	 * @return
	 * @throws PersistenceException
	 */
	public UsuarioProcesso selectPorUsuarioProcesso(Long idUsuario, Long idProcesso) throws PersistenceException {
		Query query = entityManager
				.createNamedQuery("usuarioProcessoPorUsuarioProcesso");
		query.setParameter("idUsuario",
				KeyFactory.createKey(Usuario.class.getSimpleName(), idUsuario));
		query.setParameter("idProcesso", KeyFactory.createKey(
				Processo.class.getSimpleName(), idProcesso));
		UsuarioProcesso usuarioProcesso = null;
		try {
			usuarioProcesso = (UsuarioProcesso)query.getSingleResult();
		} catch(NoResultException e) {}
		return usuarioProcesso;
	}
	
}
