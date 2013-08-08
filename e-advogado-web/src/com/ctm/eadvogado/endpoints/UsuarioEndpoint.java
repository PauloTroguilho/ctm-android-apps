package com.ctm.eadvogado.endpoints;

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
	
	@ApiMethod(name = "notificarMovimentacaoProcessual")
	public void notificarMovimentacaoProcessual(@Named("npu") String npu, @Named("idTribunal") Long idTribunal, @Named("tipoJuizo") TipoJuizo tipoJuizo)
			throws NotFoundException {
		processoNegocio.notificarMovimentacaoProcessual(npu, idTribunal, tipoJuizo);
	}

	@ApiMethod(name = "enviarEmailParaUsuario")
	public void enviarEmailParaUsuario(@Named("assunto") String assunto, @Named("mensagem") String mensagem, @Named("email") String email)
			throws NotFoundException {
		Usuario usuario = getNegocio().findByEmail(email);
		if (usuario != null) {
			getNegocio().enviarEmailParaUsuario(assunto, mensagem, usuario);
		} else {
			throw new NotFoundException(String.format("Não foi encontrado usuário com e-mail: %s", email));
		}
	}
	
	@ApiMethod(name = "enviarEmailParaTodosUsuarios")
	public void enviarEmailParaTodosUsuarios(@Named("assunto") String assunto, @Named("mensagem") String mensagem) {
		getNegocio().enviarEmailParaTodosUsuarios(assunto, mensagem);
	}
}
