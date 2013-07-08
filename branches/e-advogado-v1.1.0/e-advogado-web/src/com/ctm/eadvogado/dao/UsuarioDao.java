/**
 * 
 */
package com.ctm.eadvogado.dao;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.codec.digest.DigestUtils;

import com.ctm.eadvogado.exception.DAOException;
import com.ctm.eadvogado.interceptors.Transacional;
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
	
	/**
	 * @param email
	 * @param senha
	 * @return
	 * @throws DAOException
	 */
	public Usuario autenticar(String email, String senha) throws SecurityException {
		Usuario usuario = findByEmail(email);
		if (usuario != null
				&& usuario.getSenha().equals(
						DigestUtils.sha256Hex(senha))) {
			return usuario;
		} else {
			throw new SecurityException("usuario.autenticacao.senhaInvalida");
		}
	}
	
	@Override
	@Transacional
	public Usuario insert(Usuario entity) throws PersistenceException {
		entity.setEmail(entity.getEmail().toLowerCase());
		entity.setSenha(DigestUtils.sha256Hex(entity.getSenha()));
		return super.insert(entity);
	}

}
