package com.ctm.eadvogado.model;

import javax.persistence.Entity;

@Entity
public class DeviceInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * The Google Cloud Messaging registration token for the device. This token
	 * indicates that the device is able to receive messages sent via GCM.
	 */
	private String deviceRegistrationID;

	/*
	 * Some identifying information about the device, such as its manufacturer
	 * and product name.
	 */
	private String deviceInformation;

	/*
	 * Timestamp indicating when this device registered with the application.
	 */
	private long timestamp;

	public String getDeviceRegistrationID() {
		return deviceRegistrationID;
	}

	public String getDeviceInformation() {
		return this.deviceInformation;
	}

	public void setDeviceRegistrationID(String deviceRegistrationID) {
		this.deviceRegistrationID = deviceRegistrationID;
	}

	public void setDeviceInformation(String deviceInformation) {
		this.deviceInformation = deviceInformation;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
