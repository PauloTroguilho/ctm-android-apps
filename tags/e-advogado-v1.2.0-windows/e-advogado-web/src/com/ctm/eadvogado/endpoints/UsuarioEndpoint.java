package com.ctm.eadvogado.endpoints;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.NoResultException;

import com.ctm.eadvogado.model.TipoJuizo;
import com.ctm.eadvogado.model.Usuario;
import com.ctm.eadvogado.negocio.ProcessoNegocio;
import com.ctm.eadvogado.negocio.UsuarioNegocio;
import com.ctm.eadvogado.util.WeldUtils;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

@Api(name = "usuarioEndpoint", namespace = @ApiNamespace(ownerDomain = "eadvogado.ctm.com", ownerName = "eadvogado.ctm.com", packagePath = "endpoints"))
public class UsuarioEndpoint extends BaseEndpoint<Usuario, UsuarioNegocio> {
	
	private ProcessoNegocio processoNegocio;
	
	public UsuarioEndpoint() {
		setNegocio(WeldUtils.getBean(UsuarioNegocio.class));
		processoNegocio = WeldUtils.getBean(ProcessoNegocio.class);
	}
	
	/**
	 * @param email
	 * @param senha
	 * @return
	 * @throws NotFoundException
	 */
	@ApiMethod(name = "autenticar")
	public Usuario autenticar(@Named("email") String email, @Named("senha") String senha)
			throws NotFoundException, UnauthorizedException {
		Usuario usuario = null;
		try {
			usuario = getNegocio().autenticar(email, senha);
		} catch(NoResultException e) {
			throw new NotFoundException("Usuário não encontrado!");
		} catch (SecurityException e) {
			throw new UnauthorizedException("Usuário e/ou senha inválidos!");
		}
		return usuario;
	}
	
	@ApiMethod(name = "resetarSenha")
	public Usuario resetarSenha(@Named("email") String email)
			throws NotFoundException {
		return getNegocio().resetarSenha(email);
	}
	
	@ApiMethod(name = "notificarMovimentacaoProcessual")
	public void notificarMovimentacaoProcessual(@Named("npu") String npu, @Named("idTribunal") Long idTribunal, @Named("tipoJuizo") TipoJuizo tipoJuizo)
			throws NotFoundException {
		processoNegocio.notificarMovimentacaoProcessual(npu, idTribunal, tipoJuizo);
	}

	@ApiMethod(name = "enviarEmailParaUsuario")
	public void enviarEmailParaUsuario(@Named("from") String from, @Named("assunto") String assunto, @Nullable @Named("textContent") String textContent, @Nullable @Named("htmlContent") String htmlContent, @Named("email") String email)
			throws NotFoundException {
		Usuario usuario = null;
		try {
			usuario = getNegocio().findByEmail(email);
		} catch(NoResultException e) {}
		if (usuario != null) {
			getNegocio().enviarEmailParaUsuario(from, assunto, textContent, htmlContent, usuario);
		} else {
			throw new NotFoundException(String.format("Não foi encontrado usuário com e-mail: %s", email));
		}
	}
	
	@ApiMethod(name = "enviarEmailParaTodosUsuarios")
	public void enviarEmailParaTodosUsuarios(@Named("from") String from, @Named("assunto") String assunto, @Nullable @Named("textContent") String textContent, @Nullable @Named("htmlContent") String htmlContent, @Named("firstRecord") Integer firstRecord, @Named("maxRecords") Integer maxRecords) {
		getNegocio().enviarEmailParaTodosUsuarios(from, assunto, textContent, htmlContent, firstRecord, maxRecords);
	}
	

	@ApiMethod(name = "enviarNotificacao")
	public void enviarNotificacao(@Named("email") String email, @Named("titulo") String titulo, @Named("mensagem") String mensagem, @Named("tipoNotificacao") String tipoNotificacao)
			throws NotFoundException {
		Usuario usuario = null;
		try {
			usuario = getNegocio().findByEmail(email);
		} catch(NoResultException e) {}
		if (usuario != null) {
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withUrl("/enviarNotificacao")
					.method(Method.POST)
					.param("email", usuario.getEmail())
					.param("tipoNotificacao", tipoNotificacao)
					.param("titulo", titulo)
					.param("mensagem", mensagem));
			logger.info(String.format(
				"Queue de notificacao criada para usuario %s", email));
			
		} else {
			throw new NotFoundException(String.format("Não foi encontrado usuário com e-mail: %s", email));
		}
	}
	
	@ApiMethod(name = "enviarNotificacaoParaTodos")
	public void enviarNotificacaoParaTodos(@Named("titulo") String titulo, @Named("mensagem") String mensagem, @Named("tipoNotificacao") String tipoNotificacao)
			throws NotFoundException {
		List<Usuario> usuarios = getNegocio().findAll(null, null);
		for (Usuario usuario : usuarios) {
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withUrl("/enviarNotificacao")
					.method(Method.POST)
					.param("email", usuario.getEmail())
					.param("tipoNotificacao", tipoNotificacao)
					.param("titulo", titulo)
					.param("mensagem", mensagem));
			logger.info(String.format(
				"Queue de notificacao criada para usuario %s", usuario.getEmail()));
		}
		
	}
	
}
