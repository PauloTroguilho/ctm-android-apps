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
		List<Device> devices = getDao().findByUsuarioERegId(usuario, registrationId);
		if (devices == null || devices.isEmpty()) {
			Device device = new Device();
			device.setDeviceInfo(deviceInfo);
			device.setRegistrationId(registrationId);
			device.setTimestamp(System.currentTimeMillis());
			device.setUsuario(usuario.getKey());
			device.setStatus(StatusDevice.ATIVO);
			getDao().insert(device);
		} else {
			logger.warning(String.format(
				"Usuário %s, já possui este dispositivo registrado. %s", usuario.getEmail(), registrationId));
			if (devices.size() == 1) {
				Device device = devices.iterator().next();
				if (device.getStatus().equals(StatusDevice.INATIVO)) {
					device.setStatus(StatusDevice.ATIVO);
					getDao().update(device);
					logger.warning(
						String.format("Reativando o device %s do usuario %s", registrationId, usuario.getEmail()));
				}
			} else {
				logger.severe(String.format(
					"Devices duplicados para Usuário %s, regId %s", usuario.getEmail(), registrationId));
			}
		}
	}
	
	@Transacional
	public void desregistrar(Usuario usuario, String registrationId) throws NegocioException {
		List<Device> devices = getDao().findByUsuarioERegId(usuario, registrationId);
		if (devices != null && !devices.isEmpty()) {
			if (devices.size() > 1) {
				logger.warning(String.format(
						"Removendo devices duplicados para Usuário %s, regId %s", usuario.getEmail(), registrationId));
				for (int i = 1; i < devices.size(); i++) {
					Device device = devices.get(i);
					getDao().remove(device);
				}
			}
			Device device = devices.get(0);
			if (device.getStatus().equals(StatusDevice.ATIVO)) {
				device.setStatus(StatusDevice.INATIVO);
				getDao().update(device);
				logger.info(String.format("Desativando o device %s do usuario %s", registrationId, usuario.getEmail()));
			}
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
	
	/**
	 * @param usuario
	 * @return
	 * @throws PersistenceException
	 */
	public List<Device> findAtivosByUsuario(Usuario usuario) throws PersistenceException {
		return getDao().findByUsuarioEStatus(usuario, StatusDevice.ATIVO);
	}

}
