/**
 * 
 */
package com.ctm.eadvogado.negocio;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import com.ctm.eadvogado.dao.BaseDao.SortOrder;
import com.ctm.eadvogado.dao.TribunalDao;
import com.ctm.eadvogado.model.Tribunal;

/**
 * @author Cleber
 *
 */
@Named
public class TribunalNegocio extends BaseNegocio<Tribunal, TribunalDao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	@Inject
	public void setDao(TribunalDao dao) {
		super.setDao(dao);
	}
	
	@Override
	public List<Tribunal> findAll(String sortField, SortOrder sortOrder)
			throws PersistenceException {
		if (sortField == null) {
			sortField = "sigla";
		}
		if (sortOrder == null) {
			sortOrder = SortOrder.ASC;
		}
		return super.findAll(sortField, sortOrder);
	}

}
