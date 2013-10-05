/**
 * 
 */
package com.ctm.eadvogado.endpoints;

import java.io.Serializable;

import com.ctm.eadvogado.model.TipoJuizo;

/**
 * @author Cleber
 * 
 */
public class ProcessoUsuario implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String npu;
	private Long idTribunal;
	private TipoJuizo tipoJuizo;
	private Long idProcesso;
	private String poloAtivo;
	private String poloPassivo;

	/**
	 * 
	 */
	public ProcessoUsuario() {

	}

	public Long getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(Long idProcesso) {
		this.idProcesso = idProcesso;
	}

	public String getNpu() {
		return npu;
	}

	public void setNpu(String npu) {
		this.npu = npu;
	}

	public Long getIdTribunal() {
		return idTribunal;
	}

	public void setIdTribunal(Long idTribunal) {
		this.idTribunal = idTribunal;
	}

	public TipoJuizo getTipoJuizo() {
		return tipoJuizo;
	}

	public void setTipoJuizo(TipoJuizo tipoJuizo) {
		this.tipoJuizo = tipoJuizo;
	}

	public String getPoloAtivo() {
		return poloAtivo;
	}

	public void setPoloAtivo(String poloAtivo) {
		this.poloAtivo = poloAtivo;
	}

	public String getPoloPassivo() {
		return poloPassivo;
	}

	public void setPoloPassivo(String poloPassivo) {
		this.poloPassivo = poloPassivo;
	}

}
