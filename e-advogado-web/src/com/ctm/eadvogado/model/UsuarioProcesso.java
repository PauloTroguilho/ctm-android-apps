/**
 * 
 */
package com.ctm.eadvogado.model;

import javax.jdo.annotations.Persistent;
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
	@NamedQuery(name = "usuariosPorProcesso", query = "select usuProc.usuario from UsuarioProcesso as usuProc where usuProc.processo = :idProcesso"),
	@NamedQuery(name = "processosPorUsuario", query = "select usuProc.processo from UsuarioProcesso as usuProc where usuProc.usuario = :idUsuario"),
	@NamedQuery(name = "usuarioProcessoPorUsuarioProcesso", query = "select usuProc from UsuarioProcesso as usuProc where usuProc.usuario = :idUsuario and usuProc.processo = :idProcesso"),
	@NamedQuery(name = "processosAssociados", query = "select usuProc.processo from UsuarioProcesso as usuProc")
})
public class UsuarioProcesso extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Key usuario;
	private Key processo;
	
	/**
	 * 
	 */
	public UsuarioProcesso() {
	}

	@Persistent
	public Key getUsuario() {
		return usuario;
	}

	public void setUsuario(Key usuario) {
		this.usuario = usuario;
	}

	@Persistent
	public Key getProcesso() {
		return processo;
	}

	public void setProcesso(Key processo) {
		this.processo = processo;
	}


}
