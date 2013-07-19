package com.ctm.eadvogado.endpoints;

import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.NoResultException;

import com.ctm.eadvogado.exception.NegocioException;
import com.ctm.eadvogado.model.Compra;
import com.ctm.eadvogado.model.Usuario;
import com.ctm.eadvogado.negocio.CompraNegocio;
import com.ctm.eadvogado.negocio.UsuarioNegocio;
import com.ctm.eadvogado.util.WeldUtils;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.BadRequestException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;

@Api(name = "compraEndpoint", namespace = @ApiNamespace(ownerDomain = "eadvogado.ctm.com", ownerName = "eadvogado.ctm.com", packagePath = "endpoints"))
public class CompraEndpoint {
	
	protected static Logger logger = Logger.getLogger(CompraEndpoint.class.getSimpleName());
	
	private UsuarioNegocio usuarioNegocio;
	private CompraNegocio compraNegocio;
	
	public CompraEndpoint() {
		compraNegocio = WeldUtils.getBean(CompraNegocio.class);
		usuarioNegocio = WeldUtils.getBean(UsuarioNegocio.class);
	}
	
	@ApiMethod(name = "gerarCompraPendente")
	public Compra gerarCompraPendente(@Named("email") String email,
			@Named("senha") String senha, @Named("sku") String sku) throws NotFoundException, UnauthorizedException, BadRequestException{
		Usuario usuario = null;
		try {
			usuario = usuarioNegocio.autenticar(email, senha);
		} catch(NoResultException e) {
			throw new NotFoundException("Usuário não encontrado!");
		} catch (SecurityException e) {
			throw new UnauthorizedException("Usuário e/ou senha inválidos!");
		}
		try {
			return compraNegocio.gerarCompraPendente(usuario, sku);
		}catch(NegocioException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	@ApiMethod(name = "confirmarCompraPendente")
	public Compra confirmarCompraPendente(@Named("email") String email,
			@Named("senha") String senha, @Named("sku") String sku,
			@Named("payload") String payload, @Nullable	@Named("token") String token) throws NotFoundException,
			UnauthorizedException, BadRequestException {
		Usuario usuario = null;
		try {
			usuario = usuarioNegocio.autenticar(email, senha);
		} catch(NoResultException e) {
			throw new NotFoundException("Usuário não encontrado!");
		} catch (SecurityException e) {
			throw new UnauthorizedException("Usuário e/ou senha inválidos!");
		}
		try {
			return compraNegocio.confirmarCompraPendente(usuario, sku, payload, token);
		} catch(NegocioException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

}
