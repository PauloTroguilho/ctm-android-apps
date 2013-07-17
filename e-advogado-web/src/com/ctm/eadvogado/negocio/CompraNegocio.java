/**
 * 
 */
package com.ctm.eadvogado.negocio;

import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Named;

import com.ctm.eadvogado.dao.CompraDao;
import com.ctm.eadvogado.dao.LancamentoDao;
import com.ctm.eadvogado.dao.UsuarioDao;
import com.ctm.eadvogado.exception.NegocioException;
import com.ctm.eadvogado.interceptors.Transacional;
import com.ctm.eadvogado.model.Compra;
import com.ctm.eadvogado.model.Lancamento;
import com.ctm.eadvogado.model.SituacaoCompra;
import com.ctm.eadvogado.model.TipoConta;
import com.ctm.eadvogado.model.TipoLancamento;
import com.ctm.eadvogado.model.Usuario;

/**
 * @author Cleber
 *
 */
@Named
public class CompraNegocio extends BaseNegocio<Compra, CompraDao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// SKUs for our products: the premium upgrade (non-consumable) and gas (consumable)
    static final String SKU_PROCESSOS_10 = "processos_10";
    static final String SKU_PROCESSOS_25 = "processos_25";
    static final String SKU_PROCESSOS_100 = "processos_100";
    // SKU for our subscription (conta premium)
    static final String SKU_CONTA_PREMIUM = "conta_premium";
	
	@Inject
	private UsuarioDao usuarioDao;
	@Inject
	private LancamentoDao lancamentoDao;
	
	@Override
	@Inject
	public void setDao(CompraDao dao) {
		super.setDao(dao);
	}

	/**
	 * @param usuario
	 * @param sku
	 * @return
	 * @throws NegocioException
	 */
	@Transacional
	public Compra gerarCompraPendente(Usuario usuario, String sku) throws NegocioException {
		if (sku.equals(SKU_CONTA_PREMIUM) || sku.equals(SKU_PROCESSOS_10)
				|| sku.equals(SKU_PROCESSOS_25)
				|| sku.equals(SKU_PROCESSOS_100)) {
			Compra compra = new Compra();
			compra.setSituacao(SituacaoCompra.PENDENTE);
			compra.setData(new Date());
			compra.setUsuario(usuario.getKey());
			compra.setSku(sku);
			compra.setPayload(UUID.randomUUID().toString());
			
			return getDao().insert(compra);
		} else {
			throw new NegocioException("O SKU informado é inválido.");
		}
	}
	
	public static final int UM_DIA = 60000 * 60 * 24;
	public static final int SETE_DIAS = UM_DIA * 7;
	
	/**
	 * @param usuario
	 * @param sku
	 * @param payload
	 * @return
	 */
	@Transacional
	public Compra confirmarCompraPendente(Usuario usuario, String sku, String payload, String token) {
		Compra compra = getDao().findByUsuarioSkuPayload(usuario, sku, payload);
		if (compra != null && compra.getSituacao().equals(SituacaoCompra.PENDENTE)) {
			compra.setSituacao(SituacaoCompra.CONFIMADA);
			compra.setToken(token);
			compra = getDao().update(compra);
			if (sku.equals(SKU_CONTA_PREMIUM)) {
				usuario = usuarioDao.findByID(usuario.getKey().getId());
				usuario.setTipoConta(TipoConta.PREMIUM);
				usuario.setDataExpiracao(new Date(System.currentTimeMillis() + SETE_DIAS));
				usuarioDao.update(usuario);
			} else {
				Lancamento lancamento = new Lancamento();
				if (sku.equals(SKU_PROCESSOS_10)) {
					lancamento.setQuantidade(10);
				} else if (sku.equals(SKU_PROCESSOS_25)) {
					lancamento.setQuantidade(25);
				} else if (sku.equals(SKU_PROCESSOS_100)) {
					lancamento.setQuantidade(100);
				} else {
					throw new NegocioException("O SKU informado é inválido");
				}
				lancamento.setTipo(TipoLancamento.CREDITO);
				lancamento.setUsuario(usuario.getKey());
				lancamento.setData(new Date());
				lancamentoDao.insert(lancamento);
			}
		}
		return compra;
	}
}
