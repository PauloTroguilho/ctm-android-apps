/**
 * 
 */
package com.ctm.eadvogado.negocio;

import java.util.Date;
import java.util.logging.Level;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.codec.digest.DigestUtils;

import com.ctm.eadvogado.dao.LancamentoDao;
import com.ctm.eadvogado.dao.UsuarioDao;
import com.ctm.eadvogado.exception.DAOException;
import com.ctm.eadvogado.interceptors.Transacional;
import com.ctm.eadvogado.model.Lancamento;
import com.ctm.eadvogado.model.TipoConta;
import com.ctm.eadvogado.model.TipoLancamento;
import com.ctm.eadvogado.model.Usuario;

/**
 * @author Cleber
 *
 */
@Named
public class UsuarioNegocio extends BaseNegocio<Usuario, UsuarioDao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private LancamentoDao lancamentoDao;

	@Override
	@Inject
	public void setDao(UsuarioDao dao) {
		super.setDao(dao);
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
			usuario.setSaldo(lancamentoDao.getSaldoLancamentos(usuario));
			return usuario;
		} else {
			throw new SecurityException("usuario.autenticacao.senhaInvalida");
		}
	}
	
	@Override
	@Transacional
	public Usuario insert(Usuario entity) throws PersistenceException {
		entity.setTipoConta(TipoConta.BASICA);
		entity.setDataCadastro(new Date());
		entity.setEmail(entity.getEmail().toLowerCase());
		entity.setSenha(DigestUtils.sha256Hex(entity.getSenha()));
		Usuario usuario = super.insert(entity);
		try {
			Lancamento lancamento = new Lancamento();
			lancamento.setTipo(TipoLancamento.CREDITO);
			lancamento.setData(new Date());
			lancamento.setQuantidade(2);
			lancamento.setUsuario(usuario.getKey());
			lancamentoDao.insert(lancamento);
		} catch (PersistenceException e) {
			logger.log(Level.SEVERE, "Falha ao registrar lancamento gratuito", e);
			throw e;
		}
		return usuario;
	}
	
	/**
	 * @param email
	 * @return
	 * @throws PersistenceException
	 * @throws NoResultException
	 */
	public Usuario findByEmail(String email) throws PersistenceException, NoResultException {
		return getDao().findByEmail(email);
	}
	
}
