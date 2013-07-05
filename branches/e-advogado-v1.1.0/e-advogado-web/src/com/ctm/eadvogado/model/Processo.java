/**
 * 
 */
package com.ctm.eadvogado.model;

import javax.persistence.Entity;

import com.google.appengine.api.datastore.Key;

/**
 * @author Cleber
 * 
 */
@Entity
public class Processo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String npu;
	private TipoJuizo tipoJuizo;
	private Key tribunal;
	
/*	@Lob @Basic(fetch = FetchType.EAGER)
	private TipoProcessoJudicial processoJudicial;*/

	/**
	 * 
	 */
	public Processo() {
	}

	public String getNpu() {
		return npu;
	}

	public void setNpu(String npu) {
		this.npu = npu;
	}

	public TipoJuizo getTipoJuizo() {
		return tipoJuizo;
	}

	public void setTipoJuizo(TipoJuizo tipoJuizo) {
		this.tipoJuizo = tipoJuizo;
	}

	public Key getTribunal() {
		return tribunal;
	}

	public void setTribunal(Key tribunal) {
		this.tribunal = tribunal;
	}

	/*public TipoProcessoJudicial getProcessoJudicial() {
		return processoJudicial;
	}

	public void setProcessoJudicial(TipoProcessoJudicial processoJudicial) {
		this.processoJudicial = processoJudicial;
	}*/

}
