/**
 * 
 */
package com.ctm.eadvogado.negocio;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.codec.digest.DigestUtils;

import com.ctm.eadvogado.dao.BaseDao.SortOrder;
import com.ctm.eadvogado.dao.DeviceDao;
import com.ctm.eadvogado.dao.LancamentoDao;
import com.ctm.eadvogado.dao.UsuarioDao;
import com.ctm.eadvogado.dao.UsuarioProcessoDao;
import com.ctm.eadvogado.exception.DAOException;
import com.ctm.eadvogado.exception.NegocioException;
import com.ctm.eadvogado.interceptors.Transacional;
import com.ctm.eadvogado.model.Device;
import com.ctm.eadvogado.model.Lancamento;
import com.ctm.eadvogado.model.Processo;
import com.ctm.eadvogado.model.TipoConta;
import com.ctm.eadvogado.model.TipoLancamento;
import com.ctm.eadvogado.model.Usuario;
import com.ctm.eadvogado.model.UsuarioProcesso;
import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.response.NotFoundException;
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
	
	/*
	 * TODO: Fill this in with the server key that you've obtained from the API
	 * Console (https://code.google.com/apis/console). This is required for
	 * using Google Cloud Messaging from your AppEngine application even if you
	 * are using a App Engine's local development server.
	 */
	private static final String API_KEY = "AIzaSyDbfKFwbwXZXF5al1vUC8QVE2Q4veRXv-E";
	
	@Inject
	private LancamentoDao lancamentoDao;
	@Inject
	private UsuarioProcessoDao usuarioProcessoDao;
	@Inject
	private DeviceDao deviceDao;
	@Inject
	private UsuarioDao usuarioDao;

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
	 * @param email
	 * @param novaSenha
	 * @return
	 * @throws SecurityException
	 */
	@Transacional
	public Usuario alterarSenha(String email, String senhaAtual, String novaSenha) throws SecurityException {
		Usuario usuario = autenticar(email, senhaAtual);
		usuario.setSenha(DigestUtils.sha256Hex(novaSenha));
		getDao().update(usuario);
		return usuario;
	}
	
	/**
	 * @param email
	 * @return
	 * @throws SecurityException
	 */
	@Transacional
	public Usuario resetarSenha(String email) throws NotFoundException {
		Usuario usuario = null;
		try {
			usuario = findByEmail(email);
		} catch(NoResultException e) {
			logger.log(Level.WARNING, String.format("Usuario nao encontrado com o e-mail: %s", email), e);
		}
		if (usuario != null) {
			String uuidString = UUID.randomUUID().toString();
			uuidString = uuidString.replaceAll("[-.]", "");
			String novaSenha = uuidString.substring(0, 6);
			usuario.setSenha(DigestUtils.sha256Hex(novaSenha));
			getDao().update(usuario);
			
			StringBuffer emailRecup = new StringBuffer();
			emailRecup.append("<html><body style=\"font-size:12px;font-family:trebuchet ms,helvetica,sans-serif;\">");
			emailRecup.append("<p>Prezado usu&aacute;rio,</p>");
			emailRecup.append("<p>Conforme solicitado, segue abaixo sua nova senha de acesso ao sistema.</p>");
			emailRecup.append("<p><b>"+novaSenha+"</b></p><br/>");
			emailRecup.append("<p>Para eventuais d&uacute;vidas, reclama&ccedil;&otilde;es ou sugest&otilde;es, favor entrar em contato atrav&eacute;s dos nossos canais de atendimento ao usu&aacute;rio:</p>");
			emailRecup.append("<p>Twitter: http://twitter.com/e_Advogado<br />Facebook: http://www.facebook.com/eAdvogado<br />E-mail: contato.eadvogado@gmail.com</p>");
			emailRecup.append("<p>Obrigado!</p>");
			emailRecup.append("<p>Equipe e-Advogado.</p>");
			emailRecup.append("</body></html>");
			
			enviarEmailParaUsuario("contato.eadvogado@gmail.com", "Recuperação de senha", emailRecup.toString(), emailRecup.toString(), usuario);
		} else {
			logger.warning(String.format("Nenhum usuário encontrado com email: %s", email));
			throw new NotFoundException("Usuário não encontrado!");
		}
		return usuario;
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
		Usuario usuario = null;
		try {
			usuario = findByEmail(entity.getEmail().toLowerCase());
		} catch (NoResultException e) {}
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
	
	@Transacional
	public void enviarNotificacao(Usuario usuario, Map<String, String> paramsMap) {
		Sender sender = new Sender(API_KEY);
		// This message object is a Google Cloud Messaging object, it is NOT
		// related to the MessageData class
		Builder builder = new Message.Builder();
		for (String paramName : paramsMap.keySet()) {
			builder.addData(paramName, paramsMap.get(paramName));
		}
		Message msg = builder.build();
		List<Device> devices = deviceDao.findByUsuario(usuario);
		if (!devices.isEmpty()) {
			for (Device device : devices) {
				try {
					Result result = sender.send(msg, device.getRegistrationId(), 5);
					if (result.getMessageId() != null) {
						String canonicalRegId = result.getCanonicalRegistrationId();
						if (canonicalRegId != null) {
							device.setRegistrationId(canonicalRegId);
							deviceDao.update(device);
						}
					} else {
						String error = result.getErrorCodeName();
						if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
							deviceDao.remove(device);
						}
					}
				} catch (IOException e) {
					logger.log(Level.SEVERE, String.format(
							"Falha ao enviar GCM para dispositivo %s, usuário %s",
							device.getRegistrationId(), usuario.getEmail()), e);
				} catch (PersistenceException e) {
					logger.log(Level.SEVERE, String.format(
							"Falha ao atualizar dispositivo %s, usuário %s",
							device.getRegistrationId(), usuario.getEmail()), e);
				}
			}
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			String uuidString = UUID.randomUUID().toString();
			uuidString = uuidString.replaceAll("[-.]", "");
			String novaSenha = uuidString.substring(0, 6);
			System.out.println(novaSenha);
		}
		
	}
	
	
}
