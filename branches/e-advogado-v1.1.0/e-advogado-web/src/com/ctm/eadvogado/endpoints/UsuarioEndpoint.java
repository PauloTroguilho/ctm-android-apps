package com.ctm.eadvogado.endpoints;

import javax.inject.Named;
import javax.persistence.NoResultException;

import com.ctm.eadvogado.dao.UsuarioDao;
import com.ctm.eadvogado.model.Usuario;
import com.ctm.eadvogado.util.WeldUtils;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;

@Api(name = "usuarioEndpoint", namespace = @ApiNamespace(ownerDomain = "eadvogado.ctm.com", ownerName = "eadvogado.ctm.com", packagePath = "endpoints"))
public class UsuarioEndpoint extends BaseEndpoint<Usuario, UsuarioDao> {

	public UsuarioEndpoint() {
		setDao(WeldUtils.getBean(UsuarioDao.class));
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
			usuario = getDao().autenticar(email, senha);
		} catch(NoResultException e) {
			throw new NotFoundException("Usuário não encontrado!");
		} catch (SecurityException e) {
			throw new UnauthorizedException("Usuário e/ou senha inválidos!");
		}
		return usuario;
	}

}
