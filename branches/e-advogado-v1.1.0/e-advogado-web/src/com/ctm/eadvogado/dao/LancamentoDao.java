/**
 * 
 */
package com.ctm.eadvogado.dao;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;

import com.ctm.eadvogado.model.Lancamento;
import com.ctm.eadvogado.model.TipoLancamento;
import com.ctm.eadvogado.model.Usuario;

/**
 * @author Cleber
 *
 */
@Named
public class LancamentoDao extends BaseDao<Lancamento> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioDao usuarioDao;

	/**
	 * @param entityClass
	 */
	public LancamentoDao() {
		super(Lancamento.class);
	}
	
	/**
	 * @param usuario
	 * @return
	 */
	public Long getTotalCreditos(Usuario usuario) {
		Query query = entityManager.createNamedQuery("totalLancamentosPorUsuarioETipo");
		query.setParameter("idUsuario", usuario.getKey());
		query.setParameter("tipo", TipoLancamento.CREDITO);
		Long result = (Long) query.getSingleResult();
		return result != null ? result : 0;
	}
	
	/**
	 * @param usuario
	 * @return
	 */
	public Long getTotalDebitos(Usuario usuario) {
		Query query = entityManager.createNamedQuery("totalLancamentosPorUsuarioETipo");
		query.setParameter("idUsuario", usuario.getKey());
		query.setParameter("tipo", TipoLancamento.DEBITO);
		Long result = (Long) query.getSingleResult();
		return result != null ? result : 0;
	}
	
	/**
	 * @param usuario
	 * @return
	 */
	public Long getSaldoLancamentos(Usuario usuario) {
		Long sumCreditos = getTotalCreditos(usuario);
		Long sumDebitos = getTotalDebitos(usuario);
		return sumCreditos.longValue() - sumDebitos.longValue();
	}
	
}
