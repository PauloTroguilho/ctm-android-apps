package com.ctm.eadvogado.endpoints;

import com.ctm.eadvogado.dao.UsuarioDao;
import com.ctm.eadvogado.model.Usuario;
import com.ctm.eadvogado.util.WeldUtils;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiNamespace;

@Api(name = "usuarioEndpoint", namespace = @ApiNamespace(ownerDomain = "eadvogado.ctm.com", ownerName = "eadvogado.ctm.com", packagePath = "endpoints"))
public class UsuarioEndpoint extends BaseEndpoint<Usuario, UsuarioDao> {

	public UsuarioEndpoint() {
		setDao(WeldUtils.getBean(UsuarioDao.class));
	}

}
