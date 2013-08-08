package com.ctm.eadvogado.endpoints;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.ctm.eadvogado.model.Device;
import com.ctm.eadvogado.model.Processo;
import com.ctm.eadvogado.model.TipoJuizo;
import com.ctm.eadvogado.model.Usuario;
import com.ctm.eadvogado.negocio.DeviceNegocio;
import com.ctm.eadvogado.negocio.ProcessoNegocio;
import com.ctm.eadvogado.negocio.UsuarioNegocio;
import com.ctm.eadvogado.util.WeldUtils;
import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.UnauthorizedException;

@Api(name = "usuarioEndpoint", namespace = @ApiNamespace(ownerDomain = "eadvogado.ctm.com", ownerName = "eadvogado.ctm.com", packagePath = "endpoints"))
public class UsuarioEndpoint extends BaseEndpoint<Usuario, UsuarioNegocio> {
	
	/*
	 * TODO: Fill this in with the server key that you've obtained from the API
	 * Console (https://code.google.com/apis/console). This is required for
	 * using Google Cloud Messaging from your AppEngine application even if you
	 * are using a App Engine's local development server.
	 */
	private static final String API_KEY = "AIzaSyDbfKFwbwXZXF5al1vUC8QVE2Q4veRXv-E";

	private DeviceNegocio deviceNegocio;
	private ProcessoNegocio processoNegocio;
	
	public UsuarioEndpoint() {
		setNegocio(WeldUtils.getBean(UsuarioNegocio.class));
		deviceNegocio = WeldUtils.getBean(DeviceNegocio.class);
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
		Processo processo = processoNegocio.findByNpuTribunalTipoJuizo(npu, idTribunal, tipoJuizo);
		if (processo != null) {
			Sender sender = new Sender(API_KEY);
			// This message object is a Google Cloud Messaging object, it is NOT
			// related to the MessageData class
			Message msg = new Message.Builder()
				.addData("titulo", "e-Advogado: Processo atualizado!")
				.addData("mensagem", String.format("O processo %s foi recentemente atualizado!", npu))
				.addData("npu", npu)
				.addData("idTribunal", idTribunal.toString())
				.addData("tipoJuizo", tipoJuizo.name())
				.build();
			
			List<Usuario> usuarios = getNegocio().findByProcesso(processo);
			for (Usuario usuario : usuarios) {
				List<Device> devices = deviceNegocio.findByUsuario(usuario);
				if (!devices.isEmpty()) {
					for (Device device : devices) {
						try {
							Result result = sender.send(msg, device.getRegistrationId(), 5);
							if (result.getMessageId() != null) {
								String canonicalRegId = result.getCanonicalRegistrationId();
								if (canonicalRegId != null) {
									device.setRegistrationId(canonicalRegId);
									deviceNegocio.update(device);
								}
							} else {
								String error = result.getErrorCodeName();
								if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
									deviceNegocio.remove(device);
								}
							}
						} catch (IOException e) {
							logger.log(
								Level.SEVERE, String.format(
									"Falha ao enviar GCM para dispositivo %s, usuário %s",
									device.getRegistrationId(), usuario.getEmail()), e);
						} catch (PersistenceException e) {
							logger.log(
								Level.SEVERE, String.format(
									"Falha ao atualizar dispositivo %s, usuário %s",
									device.getRegistrationId(), usuario.getEmail()), e);
						}
					}
				}
			}
			
		} else {
			throw new NotFoundException("Processo não encontrado!");
		}
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
