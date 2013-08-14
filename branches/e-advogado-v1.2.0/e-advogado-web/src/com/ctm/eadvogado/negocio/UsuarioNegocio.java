/**
 * 
 */
package com.ctm.eadvogado.negocio;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.codec.digest.DigestUtils;

import com.ctm.eadvogado.dao.BaseDao.SortOrder;
import com.ctm.eadvogado.dao.LancamentoDao;
import com.ctm.eadvogado.dao.UsuarioDao;
import com.ctm.eadvogado.dao.UsuarioProcessoDao;
import com.ctm.eadvogado.exception.DAOException;
import com.ctm.eadvogado.exception.NegocioException;
import com.ctm.eadvogado.interceptors.Transacional;
import com.ctm.eadvogado.model.Lancamento;
import com.ctm.eadvogado.model.Processo;
import com.ctm.eadvogado.model.TipoConta;
import com.ctm.eadvogado.model.TipoLancamento;
import com.ctm.eadvogado.model.Usuario;
import com.ctm.eadvogado.model.UsuarioProcesso;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

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
	@Inject
	private UsuarioProcessoDao usuarioProcessoDao;

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
		if (usuario != null && usuario.getSenha().equals(
						DigestUtils.sha256Hex(senha))) {
			usuario.setSaldo(lancamentoDao.getSaldoLancamentos(usuario));
			logger.info(String.format(
					"Usuário %s autenticado. Tipo Conta: %s. Saldo: %s",
					usuario.getEmail(), usuario.getTipoConta(),
					usuario.getSaldo()));
			try {
				fixAssociacaoProcessos(usuario);
			} catch(Exception e) {
				logger.log(Level.SEVERE, "Falha ao efetuar fix dos processos associados.", e);
			}
			return usuario;
		} else {
			throw new SecurityException("usuario.autenticacao.senhaInvalida");
		}
	}
	
	/**
	 * @param usuario
	 */
	@Transacional
	public void fixAssociacaoProcessos(Usuario usuario) throws PersistenceException {
		if (usuario.getProcessos() != null && !usuario.getProcessos().isEmpty()) {
			for (Key processoKey : usuario.getProcessos()) {
				UsuarioProcesso usuarioProcesso = usuarioProcessoDao.selectPorUsuarioProcesso(
						usuario.getKey().getId(), processoKey.getId());
				if (usuarioProcesso == null) {
					usuarioProcesso = new UsuarioProcesso();
					usuarioProcesso.setUsuario(usuario.getKey());
					usuarioProcesso.setProcesso(processoKey);
					usuarioProcessoDao.insert(usuarioProcesso);
				}
			}
			usuario.getProcessos().clear();
			getDao().update(usuario);
		}
	}
	
	
	
	@Override
	@Transacional
	public Usuario insert(Usuario entity) throws PersistenceException, NegocioException {
		Usuario usuario = findByEmail(entity.getEmail().toLowerCase());
		if (usuario == null) {
			entity.setTipoConta(TipoConta.BASICA);
			entity.setDataCadastro(new Date());
			entity.setEmail(entity.getEmail().toLowerCase());
			entity.setSenha(DigestUtils.sha256Hex(entity.getSenha()));
			usuario = super.insert(entity);
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
		} else {
			throw new NegocioException("Este e-mail já está cadastrado no sistema!");
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
	
	/**
	 * @param processo
	 * @return
	 */
	public List<Usuario> findByProcesso(Processo processo) {
		return getDao().findByProcesso(processo);
	}
	
	/**
	 * @param from
	 * @param assunto
	 * @param textContent
	 * @param htmlContent
	 * @param usuario
	 * @throws NegocioException
	 */
	public void enviarEmailParaUsuario(String from, String assunto, String textContent, String htmlContent, Usuario usuario) throws NegocioException {
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(TaskOptions.Builder.withUrl("/sendMail")
				.method(Method.POST)
				.param("from", from)
				.param("to", usuario.getEmail())
				.param("subject", assunto)
				.param("textContent", textContent)
				.param("htmlContent", htmlContent));
		logger.info(String.format(
				"Queue de sendMail criada para o email %s", usuario.getEmail()));
	}
	
	/**
	 * @param from
	 * @param assunto
	 * @param textContent
	 * @param htmlContent
	 * @throws NegocioException
	 */
	public void enviarEmailParaTodosUsuarios(String from, String assunto, String textContent, String htmlContent, Integer firstRecord, Integer maxRecords) throws NegocioException {
		List<Usuario> usuarios = findAll("email", SortOrder.ASC, firstRecord, maxRecords);
		for (Usuario usuario : usuarios) {
			try {
				enviarEmailParaUsuario(from, assunto, textContent, htmlContent, usuario);
			} catch (Exception e) {
				logger.log(Level.SEVERE, 
					String.format("Falha ao enviar email para o usuário: %s", usuario.getEmail()), e);
			}
		}
	}
	
}
