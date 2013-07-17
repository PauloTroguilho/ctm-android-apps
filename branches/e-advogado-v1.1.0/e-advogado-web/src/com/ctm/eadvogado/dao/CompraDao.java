/**
 * 
 */
package com.ctm.eadvogado.dao;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.ctm.eadvogado.model.Compra;
import com.ctm.eadvogado.model.SituacaoCompra;
import com.ctm.eadvogado.model.Usuario;

/**
 * @author Cleber
 *
 */
@Named
public class CompraDao extends BaseDao<Compra> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioDao usuarioDao;

	/**
	 * @param entityClass
	 */
	public CompraDao() {
		super(Compra.class);
	}
	
	/**
	 * Busca uma compra pelo usuario e payload
	 * @param usuario
	 * @param payload
	 * @return
	 */
	public Compra findByUsuarioSkuPayloadSituacao(Usuario usuario, String sku, String payload, SituacaoCompra situacao) {
		Query query = entityManager.createNamedQuery("compraPorUsuarioSkuPayloadPendente");
		query.setParameter("idUsuario", usuario.getKey());
		query.setParameter("sku", sku);
		query.setParameter("payload", payload);
		query.setParameter("situacao", situacao);
		Compra compra = null;
		try {
			compra = (Compra) query.getSingleResult();
		}catch (NoResultException e) { }
		
		return compra;
	}
	
	/**
	 * @param usuario
	 * @param sku
	 * @param payload
	 * @return
	 */
	public Compra findByUsuarioSkuPayloadSituacaoPendente(Usuario usuario, String sku, String payload) {
		return findByUsuarioSkuPayloadSituacao(usuario, sku, payload, SituacaoCompra.PENDENTE);
	}
	
	/**
	 * @param usuario
	 * @param sku
	 * @param payload
	 * @return
	 */
	public Compra findByUsuarioSkuPayload(Usuario usuario, String sku, String payload) {
		Query query = entityManager.createNamedQuery("compraPorUsuarioSkuPayload");
		query.setParameter("idUsuario", usuario.getKey());
		query.setParameter("sku", sku);
		query.setParameter("payload", payload);
		Compra compra = null;
		try {
			compra = (Compra) query.getSingleResult();
		}catch (NoResultException e) { }
		
		return compra;
	}
	
	/**
	 * @param usuario
	 * @param sku
	 * @param payload
	 * @param token
	 * @return
	 */
	public Compra findByUsuarioSkuPayload(Usuario usuario, String sku, String payload, String token) {
		Query query = entityManager.createNamedQuery("compraPorUsuarioSkuTokenPayload");
		query.setParameter("idUsuario", usuario.getKey());
		query.setParameter("sku", sku);
		query.setParameter("payload", payload);
		query.setParameter("token", token);
		Compra compra = null;
		try {
			compra = (Compra) query.getSingleResult();
		}catch (NoResultException e) { }
		
		return compra;
	}
	
	
}
