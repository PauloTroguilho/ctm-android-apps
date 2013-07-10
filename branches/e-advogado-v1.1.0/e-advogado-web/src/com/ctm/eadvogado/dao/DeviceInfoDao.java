/**
 * 
 */
package com.ctm.eadvogado.dao;

import javax.inject.Named;

import com.ctm.eadvogado.model.DeviceInfo;

/**
 * @author Cleber
 *
 */
@Named
public class DeviceInfoDao extends BaseDao<DeviceInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param entityClass
	 */
	public DeviceInfoDao() {
		super(DeviceInfo.class);
	}

}
