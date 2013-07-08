/**
 * 
 */
package com.ctm.eadvogado.model;

import java.util.Date;

import javax.jdo.annotations.Persistent;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.appengine.api.datastore.Key;

/**
 * @author Cleber
 * 
 */
@Entity
@NamedQueries(
		@NamedQuery(name = "totalLancamentosPorUsuarioETipo", query = "select SUM(lanc.quantidade) from Lancamento as lanc where lanc.usuario = :idUsuario AND lanc.tipo = :tipo")
	)
public class Lancamento extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Date data;
	private TipoLancamento tipo;
	private Integer quantidade;
	private Key usuario;

	/**
	 * 
	 */
	public Lancamento() {
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getData() {
		return data;
	}

	public void setData(Date dataLancamento) {
		this.data = dataLancamento;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoLancamento tipoLancamento) {
		this.tipo = tipoLancamento;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	@Persistent
	public Key getUsuario() {
		return usuario;
	}

	public void setUsuario(Key usuario) {
		this.usuario = usuario;
	}

}
