/**
 * 
 */
package com.ctm.eadvogado.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.ctm.eadvogado.model.TipoDocumento;
import com.ctm.eadvogado.model.TipoJuizo;
import com.ctm.eadvogado.model.Tribunal;
import com.google.appengine.api.datastore.Key;

/**
 * @author Cleber
 *
 */
@Named
public class TipoDocumentoDao extends BaseDao<TipoDocumento> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param entityClass
	 */
	public TipoDocumentoDao() {
		super(TipoDocumento.class);
	}
	
	/**
	 * @param tribunal
	 * @param registrationId
	 * @return
	 * @throws PersistenceException
	 */
	@SuppressWarnings("unchecked")
	public List<TipoDocumento> findPorTribunalETipoJuizo(Tribunal tribunal, TipoJuizo tipoJuizo) throws PersistenceException {
		Query query = entityManager.createNamedQuery("tipoDocumentoPorTribunalETipoJuizo");
		query.setParameter("idTribunal", tribunal.getKey());
		query.setParameter("tipoJuizo", tipoJuizo);
		List<TipoDocumento> tiposDocumentos = new ArrayList<TipoDocumento>();
		try {
			tiposDocumentos = query.getResultList();
		} catch(NoResultException e) {
			logger.warning(String.format("Nenhum TipoDocumento encontrado para tribunal/tipoJuizo: %s/%s", tribunal.getSigla(), tipoJuizo));
		}
		return tiposDocumentos;
	}
	
	/**
	 * @param tribunal
	 * @param tipoJuizo
	 * @param codigo
	 * @return
	 * @throws PersistenceException
	 */
	public TipoDocumento findPorTribunalETipoJuizoECodigo(Tribunal tribunal, TipoJuizo tipoJuizo, String codigo) throws PersistenceException {
		return findPorTribunalETipoJuizoECodigo(tribunal.getKey(), tipoJuizo, codigo);
	}
	
	/**
	 * @param tribunalKey
	 * @param tipoJuizo
	 * @param codigo
	 * @return
	 * @throws PersistenceException
	 */
	public TipoDocumento findPorTribunalETipoJuizoECodigo(Key tribunalKey, TipoJuizo tipoJuizo, String codigo) throws PersistenceException {
		Query query = entityManager.createNamedQuery("tipoDocumentoPorTribunalETipoJuizoECodigo");
		query.setParameter("idTribunal", tribunalKey);
		query.setParameter("tipoJuizo", tipoJuizo);
		query.setParameter("codigo", codigo);
		query.setMaxResults(1);
		TipoDocumento tipoDocumento = null;
		try {
			tipoDocumento = (TipoDocumento) query.getSingleResult();
		} catch(NoResultException e) {
			//logger.warning(String.format("TipoDocumento nao encontrado para tribunal/tipoJuizo/codigo: %s/%s/%s", tribunal.getSigla(), tipoJuizo, codigo));
		}
		return tipoDocumento;
	}

	
}
