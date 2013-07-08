package com.ctm.eadvogado.endpoints;

import javax.inject.Named;

import com.ctm.eadvogado.dao.LancamentoDao;
import com.ctm.eadvogado.dao.UsuarioDao;
import com.ctm.eadvogado.model.Lancamento;
import com.ctm.eadvogado.model.Usuario;
import com.ctm.eadvogado.util.WeldUtils;
import com.ctm.eadvogado.wrapped.WrappedLong;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

@Api(name = "lancamentoEndpoint", namespace = @ApiNamespace(ownerDomain = "eadvogado.ctm.com", ownerName = "eadvogado.ctm.com", packagePath = "endpoints"))
public class LancamentoEndpoint extends BaseEndpoint<Lancamento, LancamentoDao> {
	
	private UsuarioDao usuarioDao;

	public LancamentoEndpoint() {
		setDao(WeldUtils.getBean(LancamentoDao.class));
		usuarioDao = WeldUtils.getBean(UsuarioDao.class);
	}
	
	@ApiMethod(name = "saldoLancamentos")
	public WrappedLong saldoLancamentos(@Named("email") String email,
			@Named("senha") String senha) {
		Usuario usuario = usuarioDao.autenticar(email, senha);
		return new WrappedLong(getDao().getSaldoLancamentos(usuario));
	}

}
