/**
 * 
 */
package com.ctm.eadvogado.dao;

import javax.inject.Named;

import com.ctm.eadvogado.model.Tribunal;

/**
 * @author Cleber
 *
 */
@Named
public class TribunalDao extends BaseDao<Tribunal> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param entityClass
	 */
	public TribunalDao() {
		super(Tribunal.class);
	}
	
	/*@SuppressWarnings("unchecked")
	public List<Tribunal> findTribunaisNaoAtualizados() {
		List<Tribunal> resultList = new ArrayList<Tribunal>();
		int maxResults = 5;
		
		Query query = entityManager.createNamedQuery("tribunaisNaoAtualizadosDataAtualizacaoNull");
		query.setMaxResults(5);
		try {
			resultList.addAll(query.getResultList());
		} catch(NoResultException e) {
			logger.info("Nenhum Tribunal encontrado para atualizacao");
		}
		maxResults = maxResults - resultList.size();
		if (maxResults > 0) {
			query = entityManager.createNamedQuery("tribunaisNaoAtualizadosDataAtualizacaoIntervalo");
			long intervalo = System.currentTimeMillis() - INTERVALO_ATUALIZACAO;
			query.setParameter("intervalo", new Date(intervalo));
			query.setMaxResults(maxResults);
			try {
				resultList.addAll(query.getResultList());
			} catch(NoResultException e) {
				logger.info("Nenhum Tribunal encontrado para atualizacao");
			}
		}
		
		return resultList;
		
	}*/

}
