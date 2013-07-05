/**
 * 
 */
package com.ctm.eadvogado;

import javax.inject.Inject;
import javax.inject.Named;

import com.ctm.eadvogado.dao.TribunalDao;
import com.ctm.eadvogado.model.Tribunal;

/**
 * @author ctm
 *
 */
@Named
public class MeuBean {
	
	@Inject
	private TribunalDao tribunalDao;

	/**
	 * 
	 */
	public MeuBean() {
		// TODO Auto-generated constructor stub
	}
	
	public String getMensagem() {
		
		Tribunal t = new Tribunal();
		t.setNome("TJPE");
		t.setSigla("TJPE");
		
		t = tribunalDao.insert(t);
		
		return String.format("Olá Mundo! Tribunal id = %s, sigla = %s", t.getKey().getId(), t.getSigla());
	}

}

