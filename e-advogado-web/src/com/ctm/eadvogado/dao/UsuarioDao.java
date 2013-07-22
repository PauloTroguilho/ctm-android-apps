/**
 * 
 */
package com.ctm.eadvogado.dao;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.ctm.eadvogado.model.Usuario;

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
		return (Usuario) query.getSingleResult();
	}
	
}
