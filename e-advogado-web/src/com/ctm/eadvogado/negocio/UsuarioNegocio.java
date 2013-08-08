/**
 * 
 */
package com.ctm.eadvogado.negocio;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.codec.digest.DigestUtils;

import com.ctm.eadvogado.dao.BaseDao.SortOrder;
import com.ctm.eadvogado.dao.LancamentoDao;
import com.ctm.eadvogado.dao.UsuarioDao;
import com.ctm.eadvogado.exception.DAOException;
import com.ctm.eadvogado.exception.NegocioException;
import com.ctm.eadvogado.interceptors.Transacional;
import com.ctm.eadvogado.model.Lancamento;
import com.ctm.eadvogado.model.Processo;
import com.ctm.eadvogado.model.TipoConta;
import com.ctm.eadvogado.model.TipoLancamento;
import com.ctm.eadvogado.model.Usuario;

/**
 * @author Cleber
 *
 */
@Named
public class UsuarioNegocio extends BaseNegocio<Usuario, UsuarioDao> {

	private static final String CONTATO_EADVOGADO_NAME = "Contato e-Advogado App";

	private static final String CONTATO_EADVOGADO_EMAIL = "contato.eadvogado@gmail.com";

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
			logger.info(String.format(
					"Usuário %s autenticado. Tipo Conta: %s. Saldo: %s",
					usuario.getEmail(), usuario.getTipoConta(),
					usuario.getSaldo()));
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
	
	/**
	 * @param processo
	 * @return
	 */
	public List<Usuario> findByProcesso(Processo processo) {
		return getDao().findByProcesso(processo);
	}
	
	/**
	 * @param assunto
	 * @param mensagem
	 * @param usuario
	 * @throws NegocioException
	 */
	public void enviarEmailParaUsuario(String assunto, String mensagem, Usuario usuario) throws NegocioException {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(CONTATO_EADVOGADO_EMAIL, CONTATO_EADVOGADO_NAME));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(usuario.getEmail(), usuario.getEmail()));
            msg.setSubject(assunto);
            msg.setText(mensagem);
            Transport.send(msg);
        } catch (Exception e) {
        	logger.log(Level.SEVERE, String.format("Falha ao enviar email para %s", usuario.getEmail()), e);
        }
	}
	
	/**
	 * @param assunto
	 * @param mensagem
	 * @throws NegocioException
	 */
	public void enviarEmailParaTodosUsuarios(String assunto, String mensagem) throws NegocioException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		List<Usuario> usuarios = findAll("email", SortOrder.ASC);
		for (Usuario usuario : usuarios) {
			try {
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(CONTATO_EADVOGADO_EMAIL,
						CONTATO_EADVOGADO_NAME));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						usuario.getEmail(), usuario.getEmail()));
				msg.setSubject(assunto);
				msg.setText(mensagem);
				Transport.send(msg);
				logger.log(
						Level.FINE,	String.format("Email enviado para %s",
								usuario.getEmail()));
			} catch (Exception e) {
				logger.log(
						Level.SEVERE,
						String.format("Falha ao enviar email para %s",
								usuario.getEmail()), e);
			}
		}
	}
	
}
