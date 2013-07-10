/**
 * 
 */
package com.ctm.eadvogado.negocio;

import javax.inject.Inject;
import javax.inject.Named;

import com.ctm.eadvogado.dao.DeviceInfoDao;
import com.ctm.eadvogado.model.DeviceInfo;

/**
 * @author Cleber
 *
 */
@Named
public class DeviceInfoNegocio extends BaseNegocio<DeviceInfo, DeviceInfoDao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	public void setDao(DeviceInfoDao dao) {
		super.setDao(dao);
	}

}
