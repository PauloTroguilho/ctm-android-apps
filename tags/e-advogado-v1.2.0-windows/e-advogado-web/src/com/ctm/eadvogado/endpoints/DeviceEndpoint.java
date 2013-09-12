package com.ctm.eadvogado.endpoints;

import java.util.logging.Level;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.ctm.eadvogado.model.Device;
import com.ctm.eadvogado.model.Usuario;
import com.ctm.eadvogado.negocio.DeviceNegocio;
import com.ctm.eadvogado.negocio.UsuarioNegocio;
import com.ctm.eadvogado.util.WeldUtils;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;

@Api(name = "deviceEndpoint", namespace = @ApiNamespace(ownerDomain = "eadvogado.ctm.com", ownerName = "eadvogado.ctm.com", packagePath = "endpoints"))
public class DeviceEndpoint extends BaseEndpoint<Device, DeviceNegocio> {
	
	private UsuarioNegocio usuarioNegocio;

	public DeviceEndpoint() {
		setNegocio(WeldUtils.getBean(DeviceNegocio.class));
		usuarioNegocio = WeldUtils.getBean(UsuarioNegocio.class);
	}
	
	@ApiMethod(name = "registrarDispositivo")
	public void registrarDispositivo(@Named("email") String email, @Named("senha") String senha, 
			@Named("registrationId") String registrationId, @Named("deviceInfo") String deviceInfo) throws NotFoundException, UnauthorizedException, InternalServerErrorException {
		Usuario usuario = null;
		try {
			usuario = usuarioNegocio.autenticar(email, senha);
		} catch(NoResultException e) {
			throw new NotFoundException("Usuário não encontrado!");
		} catch (SecurityException e) {
			throw new UnauthorizedException("Usuário e/ou senha inválidos!");
		}
		try {
			getNegocio().registrar(usuario, registrationId, deviceInfo);
		} catch (PersistenceException e) {
			logger.log(
					Level.SEVERE,
					String.format(
							"Erro ao registrar dispositivo do usuário %s. RegId: %s DevInfo: %s",
							email, registrationId, deviceInfo), e);
			throw new InternalServerErrorException("Houve uma falha ao registrar o dispositivo!", e);
		}
	}
	
	/**
	 * @param email
	 * @param senha
	 * @param registrationId
	 * @throws NotFoundException
	 * @throws UnauthorizedException
	 * @throws InternalServerErrorException
	 */
	@ApiMethod(name = "desregistrarDispositivo")
	public void desregistrarDispositivo(@Named("email") String email, @Named("senha") String senha, 
			@Named("registrationId") String registrationId) throws NotFoundException, UnauthorizedException, InternalServerErrorException {
		Usuario usuario = null;
		try {
			usuario = usuarioNegocio.autenticar(email, senha);
		} catch(NoResultException e) {
			throw new NotFoundException("Usuário não encontrado!");
		} catch (SecurityException e) {
			throw new UnauthorizedException("Usuário e/ou senha inválidos!");
		}
		try {
			getNegocio().desregistrar(usuario, registrationId);
		} catch (PersistenceException e) {
			throw new InternalServerErrorException("Houve uma falha ao desregistrar o dispositivo!", e);
		}
	}
	
	
}
