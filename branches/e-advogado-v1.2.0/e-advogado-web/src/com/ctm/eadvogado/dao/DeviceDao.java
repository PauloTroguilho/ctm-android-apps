/**
 * 
 */
package com.ctm.eadvogado.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.ctm.eadvogado.model.Device;
import com.ctm.eadvogado.model.Device.StatusDevice;
import com.ctm.eadvogado.model.Usuario;

/**
 * @author Cleber
 *
 */
@Named
public class DeviceDao extends BaseDao<Device> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param entityClass
	 */
	public DeviceDao() {
		super(Device.class);
	}
	
	/**
	 * Localiza um Device
	 * @param usuario
	 * @param registrationId
	 * @return
	 * @throws PersistenceException
	 */
	public Device findByUsuarioERegId(Usuario usuario, String registrationId) throws PersistenceException {
		Query query = entityManager.createNamedQuery("devicePorUsuarioERegId");
		query.setParameter("idUsuario", usuario.getKey());
		query.setParameter("registrationId", registrationId);
		Device device = null;
		try {
			device = (Device) query.getSingleResult();
		} catch(NoResultException e) {
			logger.info(String.format("Device não encontrado. %s, %s", usuario.getEmail(), registrationId));
		}
		return device;
	}
	
	/**
	 * @param usuario
	 * @param registrationId
	 * @param status
	 * @return
	 * @throws PersistenceException
	 */
	public Device findByUsuarioRegIdEStatus(Usuario usuario, String registrationId, StatusDevice status) throws PersistenceException {
		Query query = entityManager.createNamedQuery("devicePorUsuarioRegIdEStatus");
		query.setParameter("idUsuario", usuario.getKey());
		query.setParameter("registrationId", registrationId);
		query.setParameter("status", status);
		
		Device device = null;
		try {
			device = (Device) query.getSingleResult();
		} catch(NoResultException e) {
			logger.info(String.format("Device não encontrado. %s, %s, %s", usuario.getEmail(), registrationId, status));
		}
		return device;
	}
	
	/**
	 * @param usuario
	 * @return
	 * @throws PersistenceException
	 */
	public List<Device> findByUsuario(Usuario usuario) throws PersistenceException {
		Query query = entityManager.createNamedQuery("devicePorUsuario");
		query.setParameter("idUsuario", usuario.getKey());
		List<Device> devices = new ArrayList<Device>();
		try {
			devices = query.getResultList();
		} catch(NoResultException e) {
			logger.info(String.format("Nenhum device encontrado para o usuário %s", usuario.getEmail()));
		}
		return devices;
	}

}
