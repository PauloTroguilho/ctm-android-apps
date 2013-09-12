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
	@SuppressWarnings("unchecked")
	public List<Device> findByUsuarioERegId(Usuario usuario, String registrationId) throws PersistenceException {
		Query query = entityManager.createNamedQuery("devicePorUsuarioERegId");
		query.setParameter("idUsuario", usuario.getKey());
		query.setParameter("registrationId", registrationId);
		List<Device> devices = new ArrayList<Device>();
		try {
			devices = query.getResultList();
		} catch(NoResultException e) {
			logger.warning(String.format("Device n�o encontrado. %s, %s", usuario.getEmail(), registrationId));
		}
		return devices;
	}
	
	/**
	 * @param usuario
	 * @param registrationId
	 * @param status
	 * @return
	 * @throws PersistenceException
	 */
	@SuppressWarnings("unchecked")
	public List<Device> findByUsuarioRegIdEStatus(Usuario usuario, String registrationId, StatusDevice status) throws PersistenceException {
		Query query = entityManager.createNamedQuery("devicePorUsuarioRegIdEStatus");
		query.setParameter("idUsuario", usuario.getKey());
		query.setParameter("registrationId", registrationId);
		query.setParameter("status", status);
		List<Device> devices = new ArrayList<Device>();
		try {
			devices = query.getResultList();
		} catch(NoResultException e) {
			logger.warning(String.format("Device n�o encontrado. %s, %s, %s", usuario.getEmail(), registrationId, status));
		}
		return devices;
	}
	
	/**
	 * @param usuario
	 * @return
	 * @throws PersistenceException
	 */
	@SuppressWarnings("unchecked")
	public List<Device> findByUsuario(Usuario usuario) throws PersistenceException {
		Query query = entityManager.createNamedQuery("devicePorUsuario");
		query.setParameter("idUsuario", usuario.getKey());
		List<Device> devices = new ArrayList<Device>();
		try {
			devices = query.getResultList();
		} catch(NoResultException e) {
			logger.warning(String.format("Nenhum device encontrado para o usu�rio %s", usuario.getEmail()));
		}
		return devices;
	}
	
	/**
	 * @param usuario
	 * @param status
	 * @return
	 * @throws PersistenceException
	 */
	@SuppressWarnings("unchecked")
	public List<Device> findByUsuarioEStatus(Usuario usuario, StatusDevice status) throws PersistenceException {
		Query query = entityManager.createNamedQuery("devicePorUsuarioEStatus");
		query.setParameter("idUsuario", usuario.getKey());
		query.setParameter("status", status);
		List<Device> devices = new ArrayList<Device>();
		try {
			devices = query.getResultList();
		} catch(NoResultException e) {
			logger.warning(String.format("Nenhum device encontrado para o usu�rio %s e status %s", usuario.getEmail(), status));
		}
		return devices;
	}

	
}
