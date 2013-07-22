package com.ctm.eadvogado.endpoints;

import com.ctm.eadvogado.model.Tribunal;
import com.ctm.eadvogado.negocio.TribunalNegocio;
import com.ctm.eadvogado.util.WeldUtils;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;

@Api(name = "tribunalEndpoint", namespace = @ApiNamespace(ownerDomain = "eadvogado.ctm.com", ownerName = "eadvogado.ctm.com", packagePath = "endpoints"))
public class TribunalEndpoint extends BaseEndpoint<Tribunal, TribunalNegocio>{
	
	public TribunalEndpoint() {
		setNegocio(WeldUtils.getBean(TribunalNegocio.class));
	}

}
