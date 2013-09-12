/**
 * 
 */
package com.ctm.eadvogado.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.google.appengine.api.datastore.Key;

/**
 * @author Cleber
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(
		name = "tipoDocumentoPorTribunalETipoJuizo",
		query = "select d from TipoDocumento as d where d.tribunal = :idTribunal and d.tipoJuizo = :tipoJuizo order by descricao asc"
	),
	@NamedQuery(
		name = "tipoDocumentoPorTribunalETipoJuizoECodigo",
		query = "select d from TipoDocumento as d where d.tribunal = :idTribunal and d.tipoJuizo = :tipoJuizo and d.codigo = :codigo order by descricao asc"
	)
})
public class TipoDocumento extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private String codigo;
	private String descricao;
	private Key tribunal;
	private TipoJuizo tipoJuizo;

	/**
	 * 
	 */
	public TipoDocumento() {

	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Key getTribunal() {
		return tribunal;
	}

	public void setTribunal(Key tribunal) {
		this.tribunal = tribunal;
	}

	public TipoJuizo getTipoJuizo() {
		return tipoJuizo;
	}

	public void setTipoJuizo(TipoJuizo tipoJuizo) {
		this.tipoJuizo = tipoJuizo;
	}
	
}
