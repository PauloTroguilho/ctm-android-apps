/**
 * 
 */
package com.ctm.eadvogado.dao;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.ctm.eadvogado.model.Processo;
import com.ctm.eadvogado.model.TipoJuizo;
import com.ctm.eadvogado.model.Tribunal;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * @author Cleber
 *
 */
@Named
public class ProcessoDao extends BaseDao<Processo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param entityClass
	 */
	public ProcessoDao() {
		super(Processo.class);
	}
	
	/**
	 * Busca um processo pelo NPU
	 * @param npu
	 * @return
	 * @throws PersistenceException
	 */
	public Processo findByNpuTribunalTipoJuizo(String npu, Long idTribunal, TipoJuizo tipoJuizo) throws PersistenceException {
		Query query = entityManager.createNamedQuery("processoPorNpuTribunalTipoJuizo");
		query.setParameter("npu", npu.replaceAll("[.-]", ""));
		query.setParameter("idTribunal", 
				KeyFactory.createKey(Tribunal.class.getSimpleName(), idTribunal));
		query.setParameter("tipoJuizo", tipoJuizo);
		Processo processo = null;
		try {
			processo = (Processo) query.getSingleResult();
		} catch(NoResultException e) {}
		return processo;
	}
	
}
