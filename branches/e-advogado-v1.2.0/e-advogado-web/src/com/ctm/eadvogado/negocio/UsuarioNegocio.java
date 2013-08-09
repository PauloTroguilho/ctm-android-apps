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
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
	 * @param textContent
	 * @param htmlContent
	 * @param usuario
	 * @throws NegocioException
	 */
	public void enviarEmailParaUsuario(String assunto, String textContent, String htmlContent, Usuario usuario) throws NegocioException {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(CONTATO_EADVOGADO_EMAIL, CONTATO_EADVOGADO_NAME));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(usuario.getEmail(), usuario.getEmail()));
            msg.setSubject(assunto);
            // Unformatted text version
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(textContent);
            // HTML version
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html");
            // Create the Multipart.  Add BodyParts to it.
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(textPart);
            mp.addBodyPart(htmlPart);
            // Set Multipart as the message's content
            msg.setContent(mp);
            
            Transport.send(msg);
        } catch (Exception e) {
        	logger.log(Level.SEVERE, String.format("Falha ao enviar email para %s", usuario.getEmail()), e);
        }
	}
	
	/**
	 * @param assunto
	 * @param textContent
	 * @param htmlContent
	 * @throws NegocioException
	 */
	public void enviarEmailParaTodosUsuarios(String assunto, String textContent, String htmlContent) throws NegocioException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		List<Usuario> usuarios = findAll("email", SortOrder.ASC);
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(CONTATO_EADVOGADO_EMAIL,
					CONTATO_EADVOGADO_NAME));
			for (Usuario usuario : usuarios) {
				msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(
					usuario.getEmail(), usuario.getEmail()));
			}
			msg.setSubject(assunto);
			// Create the Multipart.  Add BodyParts to it.
            Multipart mp = new MimeMultipart();
			if (textContent != null && textContent.length() > 0) {
				// Unformatted text version
	            MimeBodyPart textPart = new MimeBodyPart();
	            textPart.setText(textContent);
	            mp.addBodyPart(textPart);
			}
			if (htmlContent != null && htmlContent.length() > 0) {
				// HTML version
	            MimeBodyPart htmlPart = new MimeBodyPart();
	            htmlPart.setContent(htmlContent, "text/html");
	            mp.addBodyPart(htmlPart);
			}
            // Set Multipart as the message's content
            msg.setContent(mp);
            
            Transport.send(msg);
		} catch (Exception e) {
			logger.log(
				Level.SEVERE, "Falha ao enviar email para todos os usuários.", e);
		}
	}
	
}
