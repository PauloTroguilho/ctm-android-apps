/**
 * 
 */
package com.ctm.eadvogado.model;

import java.util.Date;

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
@NamedQueries({
		@NamedQuery(
			name = "compraPorUsuarioSkuPayloadSituacao", 
			query = "select c from Compra as c where c.usuario = :idUsuario AND c.sku = :sku AND c.payload = :payload AND c.situacao = :situacao"),
		@NamedQuery(
			name = "compraPorUsuarioSkuPayload", 
			query = "select c from Compra as c where c.usuario = :idUsuario AND c.sku = :sku AND c.payload = :payload"),
		@NamedQuery(
			name = "compraPorUsuarioSkuTokenPayload", 
			query = "select c from Compra as c where c.usuario = :idUsuario AND c.sku = :sku AND c.payload = :payload AND c.token = :token")
})
public class Compra extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Date data;
	private Key usuario;
	private String sku;
	private String payload;
	private String token;
	private SituacaoCompra situacao;
	/**
	 * 
	 */
	public Compra() {
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getData() {
		return data;
	}

	public void setData(Date dataLancamento) {
		this.data = dataLancamento;
	}

	public Key getUsuario() {
		return usuario;
	}

	public void setUsuario(Key usuario) {
		this.usuario = usuario;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public SituacaoCompra getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoCompra situacao) {
		this.situacao = situacao;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
