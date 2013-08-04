/**
 * 
 */
package com.ctm.eadvogado.negocio;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import com.ctm.eadvogado.dao.DeviceDao;
import com.ctm.eadvogado.exception.NegocioException;
import com.ctm.eadvogado.interceptors.Transacional;
import com.ctm.eadvogado.model.Device;
import com.ctm.eadvogado.model.Device.StatusDevice;
import com.ctm.eadvogado.model.Usuario;

/**
 * @author Cleber
 *
 */
@Named
public class DeviceNegocio extends BaseNegocio<Device, DeviceDao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	public void setDao(DeviceDao dao) {
		super.setDao(dao);
	}
	
	@Transacional
	public void registrar(Usuario usuario, String registrationId,
			String deviceInfo) throws NegocioException {
		Device device = getDao().findByUsuarioRegIdEStatus(usuario, registrationId, StatusDevice.ATIVO);
		if (device == null) {
			device = new Device();
			device.setDeviceInfo(deviceInfo);
			device.setRegistrationId(registrationId);
			device.setTimestamp(System.currentTimeMillis());
			device.setUsuario(usuario.getKey());
			device.setStatus(StatusDevice.ATIVO);
			getDao().insert(device);
		} else {
			logger.warning(String.format(
					"Usuário %s, já possui este dispositivo registrado. %s",
					usuario.getEmail(), registrationId));
			if (device.getStatus().equals(StatusDevice.INATIVO)) {
				device.setStatus(StatusDevice.ATIVO);
				getDao().update(device);
				logger.info(String.format("Reativando device %s do usuario %s", registrationId, usuario.getEmail()));
			}
		}
	}
	
	@Transacional
	public void desregistrar(Usuario usuario, String registrationId) throws NegocioException {
		Device device = getDao().findByUsuarioRegIdEStatus(usuario, registrationId, StatusDevice.ATIVO);
		if (device != null) {
			device.setStatus(StatusDevice.INATIVO);
			getDao().update(device);
		} else {
			logger.warning(String.format(
					"O device %s do usuário %s já está desativado",
					registrationId, usuario.getEmail()));
		}
	}
	
	/**
	 * @param usuario
	 * @return
	 * @throws PersistenceException
	 */
	public List<Device> findByUsuario(Usuario usuario) throws PersistenceException {
		return getDao().findByUsuario(usuario);
	}

}
