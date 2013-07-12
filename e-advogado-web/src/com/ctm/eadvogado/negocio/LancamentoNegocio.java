/**
 * 
 */
package com.ctm.eadvogado.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import com.ctm.eadvogado.dao.LancamentoDao;
import com.ctm.eadvogado.dao.UsuarioDao;
import com.ctm.eadvogado.exception.DAOException;
import com.ctm.eadvogado.interceptors.Transacional;
import com.ctm.eadvogado.model.Lancamento;
import com.ctm.eadvogado.model.TipoConta;
import com.ctm.eadvogado.model.TipoLancamento;
import com.ctm.eadvogado.model.Usuario;

/**
 * @author Cleber
 *
 */
@Named
public class LancamentoNegocio extends BaseNegocio<Lancamento, LancamentoDao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioDao usuarioDao;
	
	@Override
	@Inject
	public void setDao(LancamentoDao dao) {
		super.setDao(dao);
	}

	@Override
	@Transacional
	public Lancamento insert(Lancamento entity) throws PersistenceException {
		Usuario usuario = null;
		List<String> erros = new ArrayList<String>();
		entity.setData(new Date());
		if (entity.getQuantidade() == null || entity.getQuantidade().intValue() < 0) {
			erros.add("lancamento.erro.qtde.maiorOuIgualAZero");
		}
		if (entity.getTipo() == null) {
			erros.add("lancamento.erro.tipo.obrigatorio");
		}
		if (entity.getUsuario() != null) {
			usuario = usuarioDao.findByID(entity.getUsuario().getId());
			if (usuario != null) {
				entity.setUsuario(usuario.getKey());
			} else {
				erros.add("lancamento.erro.usuario.naoEncontrado");
			}
		} else {
			erros.add("lancamento.erro.usuario.obrigatorio");
		}
		if (erros.isEmpty()){
			entity = super.insert(entity);
		} else {
			throw new DAOException(erros);
		}
		return entity;
	}
	
	/**
	 * @param usuario
	 * @return
	 */
	public Long getSaldoLancamentos(Usuario usuario) {
		Long sumCreditos = getDao().getTotalCreditos(usuario);
		Long sumDebitos = getDao().getTotalDebitos(usuario);
		return sumCreditos.longValue() - sumDebitos.longValue();
	}
	
	// SKUs for our products: the premium upgrade (non-consumable) and gas (consumable)
    static final String SKU_PROCESSOS_10 = "processos_10";
    static final String SKU_PROCESSOS_25 = "processos_25";
    static final String SKU_PROCESSOS_100 = "processos_100";
    // SKU for our subscription (conta premium)
    static final String SKU_CONTA_PREMIUM = "conta_premium";
	
    /**
     * Registra uma compra no banco.
     * 
     * @param usuario
     * @param sku
     * @param orderId
     * @throws PersistenceException
     */
    @Transacional
	public void registrarCompra(Usuario usuario, String sku,
			@Named("orderId") String orderId) throws PersistenceException{
		Lancamento lancamento = new Lancamento();
		lancamento.setUsuario(usuario.getKey());
		if (sku.equals(SKU_PROCESSOS_10)) {
			lancamento.setTipo(TipoLancamento.CREDITO);
			lancamento.setQuantidade(10);
		} else if (sku.equals(SKU_PROCESSOS_25)) {
			lancamento.setTipo(TipoLancamento.CREDITO);
			lancamento.setQuantidade(25);
		} else if (sku.equals(SKU_PROCESSOS_100)) {
			lancamento.setTipo(TipoLancamento.CREDITO);
			lancamento.setQuantidade(100);
		} else if (sku.equals(SKU_CONTA_PREMIUM)) {
			lancamento.setTipo(TipoLancamento.CONTA_PREMIUM);
			lancamento.setQuantidade(0);
		}
		lancamento.setSku(sku);
		lancamento.setOrderId(orderId);
		lancamento.setData(new Date());
		getDao().insert(lancamento);
		if (sku.equals(SKU_CONTA_PREMIUM)) {
			usuario = usuarioDao.findByID(usuario.getKey().getId());
			usuario.setTipoConta(TipoConta.PREMIUM);
			usuarioDao.update(usuario);
		}
	}
}
