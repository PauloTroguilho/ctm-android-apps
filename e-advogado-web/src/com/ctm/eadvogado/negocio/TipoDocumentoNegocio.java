/**
 * 
 */
package com.ctm.eadvogado.negocio;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import com.ctm.eadvogado.dao.TipoDocumentoDao;
import com.ctm.eadvogado.interceptors.Transacional;
import com.ctm.eadvogado.model.TipoDocumento;
import com.ctm.eadvogado.model.TipoJuizo;
import com.ctm.eadvogado.model.Tribunal;

/**
 * @author Cleber
 *
 */
@Named
public class TipoDocumentoNegocio extends BaseNegocio<TipoDocumento, TipoDocumentoDao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	public void setDao(TipoDocumentoDao dao) {
		super.setDao(dao);
	}
	
	/**
	 * @param tribunal
	 * @return
	 * @throws PersistenceException
	 */
	public List<TipoDocumento> findPorTribunalETipoJuizo(Tribunal tribunal, TipoJuizo tipoJuizo) throws PersistenceException {
		return getDao().findPorTribunalETipoJuizo(tribunal, tipoJuizo);
	}
	
	/**
	 * @param tribunal
	 * @param codigo
	 * @return
	 * @throws PersistenceException
	 */
	/**
	 * @param tribunal
	 * @param tipoJuizo
	 * @param codigo
	 * @return
	 * @throws PersistenceException
	 */
	public TipoDocumento findByTribunalETipoJuizoECodigo(Tribunal tribunal, TipoJuizo tipoJuizo, String codigo) throws PersistenceException {
		return getDao().findPorTribunalETipoJuizoECodigo(tribunal, tipoJuizo, codigo);
	}
	
	@Override
	@Transacional
	public TipoDocumento insert(TipoDocumento entity)
			throws PersistenceException {
		if (getDao().findPorTribunalETipoJuizoECodigo(entity.getTribunal(), entity.getTipoJuizo(), entity.getCodigo()) == null) {
			return super.insert(entity);
		}
		return entity;
	}

}
