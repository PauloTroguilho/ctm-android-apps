/**
 * 
 */
package com.ctm.eadvogado.util;

import com.ctm.eadvogado.CloudEndpointUtils;
import com.ctm.eadvogado.endpoints.compraEndpoint.CompraEndpoint;
import com.ctm.eadvogado.endpoints.processoEndpoint.ProcessoEndpoint;
import com.ctm.eadvogado.endpoints.tribunalEndpoint.TribunalEndpoint;
import com.ctm.eadvogado.endpoints.usuarioEndpoint.UsuarioEndpoint;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson.JacksonFactory;

/**
 * @author ctm
 *
 */
public class EndpointUtils {

	/**
	 * Inicializa o endpoint {@link UsuarioEndpoint}
	 */
	public static UsuarioEndpoint initUsuarioEndpoint() {
		UsuarioEndpoint.Builder endpointBuilder = new UsuarioEndpoint.Builder(
		        AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
		        new HttpRequestInitializer() {
		          public void initialize(HttpRequest httpRequest) {
		          }
		        });
		return CloudEndpointUtils.updateBuilder(endpointBuilder).build();
	}
	
	/**
	 * Inicializa o endpoint {@link TribunalEndpoint}
	 */
	public static TribunalEndpoint initTribunalEndpoint() {
		TribunalEndpoint.Builder endpointBuilder = new TribunalEndpoint.Builder(
		        AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
		        new HttpRequestInitializer() {
		          public void initialize(HttpRequest httpRequest) {
		          }
		        });
		return CloudEndpointUtils.updateBuilder(endpointBuilder).build();
	}
	
	/**
	 * Inicializa o endpoint {@link ProcessoEndpoint}
	 */
	public static ProcessoEndpoint initProcessoEndpoint() {
		ProcessoEndpoint.Builder endpointBuilder = new ProcessoEndpoint.Builder(
		        AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
		        new HttpRequestInitializer() {
		          public void initialize(HttpRequest httpRequest) {
		          }
		        });
		return CloudEndpointUtils.updateBuilder(endpointBuilder).build();
	}
	
	/**
	 * Inicializa o endpoint {@link CompraEndpoint}
	 */
	public static CompraEndpoint initCompraEndpoint() {
		CompraEndpoint.Builder endpointBuilder = new CompraEndpoint.Builder(
		        AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
		        new HttpRequestInitializer() {
		          public void initialize(HttpRequest httpRequest) {
		          }
		        });
		return CloudEndpointUtils.updateBuilder(endpointBuilder).build();
	}

}
