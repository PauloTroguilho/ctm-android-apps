/**
 * 
 */
package com.ctm.eadvogado;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import br.jus.cnj.pje.v1.TipoProcessoJudicial;

import com.google.appengine.api.datastore.Key;

/**
 * @author Cleber
 * 
 */
@Entity
public class Processo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;
	private String npu;
	private TipoJuizo tipoJuizo;
	private Key tribunal;
	
	@Lob @Basic(fetch = FetchType.EAGER)
	private TipoProcessoJudicial processoJudicial;

	/**
	 * 
	 */
	public Processo() {
	}

	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
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

	public TipoProcessoJudicial getProcessoJudicial() {
		return processoJudicial;
	}

	public void setProcessoJudicial(TipoProcessoJudicial processoJudicial) {
		this.processoJudicial = processoJudicial;
	}

}
