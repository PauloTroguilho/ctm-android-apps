package com.ctm.eadvogado.endpoints;

import javax.inject.Named;
import javax.persistence.NoResultException;

import com.ctm.eadvogado.model.Lancamento;
import com.ctm.eadvogado.model.Usuario;
import com.ctm.eadvogado.negocio.LancamentoNegocio;
import com.ctm.eadvogado.negocio.UsuarioNegocio;
import com.ctm.eadvogado.util.WeldUtils;
import com.ctm.eadvogado.wrapped.WrappedLong;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;

@Api(name = "lancamentoEndpoint", namespace = @ApiNamespace(ownerDomain = "eadvogado.ctm.com", ownerName = "eadvogado.ctm.com", packagePath = "endpoints"))
public class LancamentoEndpoint extends BaseEndpoint<Lancamento, LancamentoNegocio> {
	
	private UsuarioNegocio usuarioNegocio;

	public LancamentoEndpoint() {
		setNegocio(WeldUtils.getBean(LancamentoNegocio.class));
		usuarioNegocio = WeldUtils.getBean(UsuarioNegocio.class);
	}
	
	@ApiMethod(name = "saldoLancamentos")
	public WrappedLong saldoLancamentos(@Named("email") String email,
			@Named("senha") String senha) throws NotFoundException, UnauthorizedException {
		Usuario usuario = null;
		try {
			usuario = usuarioNegocio.autenticar(email, senha);
		} catch(NoResultException e) {
			throw new NotFoundException("Usuário não encontrado!");
		} catch (SecurityException e) {
			throw new UnauthorizedException("Usuário e/ou senha inválidos!");
		}
		return new WrappedLong(getNegocio().getSaldoLancamentos(usuario));
	}
	
	@ApiMethod(name = "registrarLancamento")
	public WrappedLong registrarLancamento(@Named("email") String email,
			@Named("senha") String senha, @Named("sku") String sku,
			@Named("orderId") String orderId) throws NotFoundException, UnauthorizedException {
		Usuario usuario = null;
		try {
			usuario = usuarioNegocio.autenticar(email, senha);
		} catch(NoResultException e) {
			throw new NotFoundException("Usuário não encontrado!");
		} catch (SecurityException e) {
			throw new UnauthorizedException("Usuário e/ou senha inválidos!");
		}
		getNegocio().registrarCompra(usuario, sku, orderId);
		return new WrappedLong(getNegocio().getSaldoLancamentos(usuario));
	}

}
