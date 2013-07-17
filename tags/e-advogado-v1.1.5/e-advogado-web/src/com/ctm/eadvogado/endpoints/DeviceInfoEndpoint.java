package com.ctm.eadvogado.endpoints;

import com.ctm.eadvogado.model.DeviceInfo;
import com.ctm.eadvogado.negocio.DeviceInfoNegocio;
import com.ctm.eadvogado.util.WeldUtils;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;

@Api(name = "deviceInfoEndpoint", namespace = @ApiNamespace(ownerDomain = "eadvogado.ctm.com", ownerName = "eadvogado.ctm.com", packagePath = "endpoints"))
public class DeviceInfoEndpoint extends BaseEndpoint<DeviceInfo, DeviceInfoNegocio> {

	public DeviceInfoEndpoint() {
		setNegocio(WeldUtils.getBean(DeviceInfoNegocio.class));
	}
	
}
