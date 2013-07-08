/**
 * 
 */
package com.ctm.eadvogado;

import javax.inject.Inject;
import javax.inject.Named;

import com.ctm.eadvogado.model.Usuario;
import com.ctm.eadvogado.negocio.LancamentoNegocio;
import com.ctm.eadvogado.negocio.TribunalNegocio;
import com.ctm.eadvogado.negocio.UsuarioNegocio;

/**
 * @author ctm
 *
 */
@Named
public class MeuBean {
	
	@Inject
	private TribunalNegocio tribunalNegocio;
	@Inject
	private UsuarioNegocio usuarioNegocio;
	@Inject
	private LancamentoNegocio lancamentoNegocio;

	/**
	 * 
	 */
	public MeuBean() {
		// TODO Auto-generated constructor stub
	}
	
	public String getMensagem() {
		
		Usuario usuario = usuarioNegocio.autenticar("cleber@gmail.com", "123");
		/*if (usuario == null) {
			usuario = new Usuario();
			usuario.setEmail("cleber@gmail.com");
			usuario.setSenha("123");
			usuario.setTipoConta(TipoConta.BASICA);
			usuario = usuarioNegocio.insert(usuario);
		}
		Lancamento lancamento1 = new Lancamento();
		lancamento1.setTipo(TipoLancamento.CREDITO);
		lancamento1.setQuantidade(10);
		lancamento1.setUsuario(usuario.getKey());
		lancamento1 = lancamentoNegocio.insert(lancamento1);
		Lancamento lancamento2 = new Lancamento();
		lancamento2.setTipo(TipoLancamento.DEBITO);
		lancamento2.setQuantidade(1);
		lancamento2.setUsuario(usuario.getKey());
		lancamento2 = lancamentoNegocio.insert(lancamento2);
		Lancamento lancamento3 = new Lancamento();
		lancamento3.setTipo(TipoLancamento.CREDITO);
		lancamento3.setQuantidade(100);
		lancamento3.setUsuario(usuario.getKey());
		lancamento3 = lancamentoNegocio.insert(lancamento3);
		Lancamento lancamento4 = new Lancamento();
		lancamento4.setTipo(TipoLancamento.DEBITO);
		lancamento4.setQuantidade(1);
		lancamento4.setUsuario(usuario.getKey());
		lancamento4 = lancamentoNegocio.insert(lancamento4);*/
		
		return String.format("Olá Mundo! Usuario id = %s, email = %s. Saldo = %s", usuario
				.getKey().getId(), usuario.getEmail(), lancamentoNegocio.getSaldoLancamentos(usuario));
	}

}

