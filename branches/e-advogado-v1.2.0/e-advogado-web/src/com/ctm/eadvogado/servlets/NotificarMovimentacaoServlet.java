/**
 * 
 */
package com.ctm.eadvogado.servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.google.api.server.spi.response.NotFoundException;

/**
 * @author Cleber
 * 
 */
public class NotificarMovimentacaoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(NotificarMovimentacaoServlet.class.getSimpleName());
	
	/*
	 * TODO: Fill this in with the server key that you've obtained from the API
	 * Console (https://code.google.com/apis/console). This is required for
	 * using Google Cloud Messaging from your AppEngine application even if you
	 * are using a App Engine's local development server.
	 */
	private static final String API_KEY = "AIzaSyDbfKFwbwXZXF5al1vUC8QVE2Q4veRXv-E";

	@Inject
	private ProcessoNegocio processoNegocio;
	@Inject
	private UsuarioNegocio usuarioNegocio;
	@Inject
	private DeviceNegocio deviceNegocio;

	/**
	 * 
	 */
	public NotificarMovimentacaoServlet() {
		processoNegocio = WeldUtils.getBean(ProcessoNegocio.class);
		usuarioNegocio = WeldUtils.getBean(UsuarioNegocio.class);
		deviceNegocio = WeldUtils.getBean(DeviceNegocio.class);
	}
	
	/**
	 * @param npu
	 * @param idTribunal
	 * @param tipoJuizo
	 * @throws NotFoundException
	 */
	public void notificarMovimentacaoProcessual(String npu, Long idTribunal, TipoJuizo tipoJuizo)
			throws NotFoundException {
		Processo processo = processoNegocio.findByNpuTribunalTipoJuizo(npu, idTribunal, tipoJuizo);
		if (processo != null) {
			Sender sender = new Sender(API_KEY);
			// This message object is a Google Cloud Messaging object, it is NOT
			// related to the MessageData class
			Message msg = new Message.Builder()
				.addData("titulo", "Processo atualizado!")
				.addData("mensagem", String.format("O processo %s foi recentemente atualizado!", npu))
				.addData("npu", npu)
				.addData("idTribunal", idTribunal.toString())
				.addData("tipoJuizo", tipoJuizo.name())
				.build();
			
			List<Usuario> usuarios = usuarioNegocio.findByProcesso(processo);
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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pNpu = req.getParameter("npu");
		String pIdTribunal = req.getParameter("idTribunal");
		String pTipoJuizo = req.getParameter("tipoJuizo");
		try {
			notificarMovimentacaoProcessual(pNpu, Long.parseLong(pIdTribunal), TipoJuizo.valueOf(pTipoJuizo));
		} catch (Exception e) {
			log(String.format("Erro ao enviar notificação do processo %s.", pNpu), e);
		}
	}

}
