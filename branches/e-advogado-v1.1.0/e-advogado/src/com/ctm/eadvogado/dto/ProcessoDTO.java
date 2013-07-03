package com.ctm.eadvogado.dto;

import java.io.Serializable;

import com.ctm.eadvogado.processoendpoint.model.Processo;
import com.ctm.eadvogado.tribunalendpoint.model.Tribunal;

public class ProcessoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Processo processo;
	private Tribunal tribunal;

	public ProcessoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProcessoDTO(Processo processo) {
		super();
		this.processo = processo;
	}

	public ProcessoDTO(Processo processo, Tribunal tribunal) {
		super();
		this.processo = processo;
		this.tribunal = tribunal;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Tribunal getTribunal() {
		return tribunal;
	}

	public void setTribunal(Tribunal tribunal) {
		this.tribunal = tribunal;
	}

}