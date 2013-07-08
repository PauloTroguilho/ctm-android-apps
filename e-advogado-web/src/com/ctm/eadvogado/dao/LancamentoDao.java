/**
 * 
 */
package com.ctm.eadvogado.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.ctm.eadvogado.exception.DAOException;
import com.ctm.eadvogado.interceptors.Transacional;
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
	public Long getTotalCreditos(Usuario usuario) {
		Query query = entityManager.createNamedQuery("totalLancamentosPorUsuarioETipo");
		query.setParameter("idUsuario", usuario.getKey());
		query.setParameter("tipo", TipoLancamento.CREDITO);
		return (Long) query.getSingleResult();
	}
	
	/**
	 * @param usuario
	 * @return
	 */
	public Long getTotalDebitos(Usuario usuario) {
		Query query = entityManager.createNamedQuery("totalLancamentosPorUsuarioETipo");
		query.setParameter("idUsuario", usuario.getKey());
		query.setParameter("tipo", TipoLancamento.DEBITO);
		return (Long) query.getSingleResult();
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
