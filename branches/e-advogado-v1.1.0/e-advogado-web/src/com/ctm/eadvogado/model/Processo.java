/**
 * 
 */
package com.ctm.eadvogado.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import br.jus.cnj.pje.v1.TipoProcessoJudicial;

import com.google.appengine.api.datastore.Key;

/**
 * @author Cleber
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(
		name = "processoPorNpuTribunalTipoJuizo", 
		query = "select p from Processo as p where p.npu = :npu and p.tribunal = :idTribunal and p.tipoJuizo = :tipoJuizo"
	),
	@NamedQuery(
		name = "processosPorUsuario",
		query = "select usu.processos from Usuario as usu where usu = :usuario"
	),
	@NamedQuery(
		name = "processosInElements",
		query = "select p from Processo as p where p.key in (:processos)"
	)
})
public class Processo extends BaseEntity {

	private static final long serialVersionUID = 1L;

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
