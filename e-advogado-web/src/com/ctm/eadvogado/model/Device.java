package com.ctm.eadvogado.model;

import javax.jdo.annotations.Persistent;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.google.appengine.api.datastore.Key;

@Entity
@NamedQueries({
	@NamedQuery(
		name = "devicePorUsuario",
		query = "select d from Device as d where d.usuario = :idUsuario"
	),
	@NamedQuery(
		name = "devicePorUsuarioEStatus",
		query = "select d from Device as d where d.usuario = :idUsuario and d.status = :status"
	),
	@NamedQuery(
		name = "devicePorUsuarioERegId",
		query = "select d from Device as d where d.usuario = :idUsuario and d.registrationId = :registrationId"
	),
	@NamedQuery(
		name = "devicePorUsuarioRegIdEStatus",
		query = "select d from Device as d where d.usuario = :idUsuario and d.registrationId = :registrationId and d.status = :status"
	)
})
public class Device extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static enum StatusDevice {
		ATIVO, INATIVO;
	}

	/*
	 * The Google Cloud Messaging registration token for the device. This token
	 * indicates that the device is able to receive messages sent via GCM.
	 */
	private String registrationId;

	/*
	 * Some identifying information about the device, such as its manufacturer
	 * and product name.
	 */
	private String deviceInfo;

	/*
	 * Timestamp indicating when this device registered with the application.
	 */
	private long timestamp;
	
	private Key usuario;
	
	private StatusDevice status;
	

	public String getRegistrationId() {
		return registrationId;
	}

	public String getDeviceInfo() {
		return this.deviceInfo;
	}

	public void setRegistrationId(String deviceRegistrationID) {
		this.registrationId = deviceRegistrationID;
	}

	public void setDeviceInfo(String deviceInformation) {
		this.deviceInfo = deviceInformation;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Persistent
	public Key getUsuario() {
		return usuario;
	}

	public void setUsuario(Key usuario) {
		this.usuario = usuario;
	}

	public StatusDevice getStatus() {
		return status;
	}

	public void setStatus(StatusDevice status) {
		this.status = status;
	}
	
	
}
