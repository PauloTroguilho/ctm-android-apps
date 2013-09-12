/**
 * 
 */
package com.ctm.eadvogado.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.ctm.eadvogado.model.Processo;
import com.ctm.eadvogado.model.Usuario;
import com.google.appengine.api.datastore.Key;

/**
 * @author Cleber
 *
 */
@Named
public class UsuarioDao extends BaseDao<Usuario> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param entityClass
	 */
	public UsuarioDao() {
		super(Usuario.class);
	}
	
	/**
	 * @param email
	 * @return
	 * @throws PersistenceException
	 * @throws NoResultException
	 */
	public Usuario findByEmail(String email) throws PersistenceException, NoResultException {
		Query query = entityManager.createNamedQuery("usuarioPorEmail");
		query.setParameter("email", email.toLowerCase());
		Usuario usuario = null;
		try {
			usuario = (Usuario) query.getSingleResult();
		} catch(NonUniqueResultException e) {
			logger.log(Level.WARNING, 
				String.format("Foram encontrados mais de um usuário com o email: %s", email));
			query.setMaxResults(1);
			usuario = (Usuario) query.getResultList().get(0);
		}
		return usuario;
	}
	
	/**
	 * @param processo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> findByProcesso(Processo processo) {
		Query query = entityManager.createNamedQuery("usuariosPorProcesso");
		query.setParameter("idProcesso", processo.getKey());
		List<Key> usuariosKeys = new ArrayList<Key>(); 
		try {
			usuariosKeys = query.getResultList();
		} catch(NoResultException e) {
			logger.log(Level.INFO, "Nenhum usuario associado ao processo: "+ processo.getNpu(), e);
		}
		List<Usuario> resultList = new ArrayList<Usuario>();
		if (!usuariosKeys.isEmpty()) {
			query = entityManager.createNamedQuery("usuariosInElements");
			query.setParameter("usuarios", usuariosKeys);
			try {
				resultList = query.getResultList();
			} catch(PersistenceException e) {
				logger.log(Level.SEVERE, "Erro ao consultar usuarios do processo: "+ processo.getNpu(), e);
			}
		}
		return resultList;
	}
	
}
