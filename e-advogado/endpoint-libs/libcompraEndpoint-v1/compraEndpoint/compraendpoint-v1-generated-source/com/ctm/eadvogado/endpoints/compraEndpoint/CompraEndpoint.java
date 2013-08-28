/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2013-08-21 15:27:30 UTC)
 * on 2013-08-28 at 02:04:23 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.endpoints.compraEndpoint;

/**
 * Service definition for CompraEndpoint (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link CompraEndpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class CompraEndpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.15.0-rc of the  library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://e-advogado-web.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "compraEndpoint/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public CompraEndpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  CompraEndpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "cancelarContaPremium".
   *
   * This request holds the parameters needed by the the compraEndpoint server.  After setting any
   * optional parameters, call the {@link CancelarContaPremium#execute()} method to invoke the remote
   * operation.
   *
   * @param email
   * @param senha
   * @return the request
   */
  public CancelarContaPremium cancelarContaPremium(java.lang.String email, java.lang.String senha) throws java.io.IOException {
    CancelarContaPremium result = new CancelarContaPremium(email, senha);
    initialize(result);
    return result;
  }

  public class CancelarContaPremium extends CompraEndpointRequest<com.ctm.eadvogado.endpoints.compraEndpoint.model.WrappedBoolean> {

    private static final String REST_PATH = "cancelarContaPremium/{email}/{senha}";

    /**
     * Create a request for the method "cancelarContaPremium".
     *
     * This request holds the parameters needed by the the compraEndpoint server.  After setting any
     * optional parameters, call the {@link CancelarContaPremium#execute()} method to invoke the
     * remote operation. <p> {@link CancelarContaPremium#initialize(com.google.api.client.googleapis.s
     * ervices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param email
     * @param senha
     * @since 1.13
     */
    protected CancelarContaPremium(java.lang.String email, java.lang.String senha) {
      super(CompraEndpoint.this, "POST", REST_PATH, null, com.ctm.eadvogado.endpoints.compraEndpoint.model.WrappedBoolean.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.senha = com.google.api.client.util.Preconditions.checkNotNull(senha, "Required parameter senha must be specified.");
    }

    @Override
    public CancelarContaPremium setAlt(java.lang.String alt) {
      return (CancelarContaPremium) super.setAlt(alt);
    }

    @Override
    public CancelarContaPremium setFields(java.lang.String fields) {
      return (CancelarContaPremium) super.setFields(fields);
    }

    @Override
    public CancelarContaPremium setKey(java.lang.String key) {
      return (CancelarContaPremium) super.setKey(key);
    }

    @Override
    public CancelarContaPremium setOauthToken(java.lang.String oauthToken) {
      return (CancelarContaPremium) super.setOauthToken(oauthToken);
    }

    @Override
    public CancelarContaPremium setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (CancelarContaPremium) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public CancelarContaPremium setQuotaUser(java.lang.String quotaUser) {
      return (CancelarContaPremium) super.setQuotaUser(quotaUser);
    }

    @Override
    public CancelarContaPremium setUserIp(java.lang.String userIp) {
      return (CancelarContaPremium) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public CancelarContaPremium setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String senha;

    /**

     */
    public java.lang.String getSenha() {
      return senha;
    }

    public CancelarContaPremium setSenha(java.lang.String senha) {
      this.senha = senha;
      return this;
    }

    @Override
    public CancelarContaPremium set(String parameterName, Object value) {
      return (CancelarContaPremium) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "confirmarCompra".
   *
   * This request holds the parameters needed by the the compraEndpoint server.  After setting any
   * optional parameters, call the {@link ConfirmarCompra#execute()} method to invoke the remote
   * operation.
   *
   * @param email
   * @param senha
   * @param sku
   * @param payload
   * @param token
   * @param orderId
   * @return the request
   */
  public ConfirmarCompra confirmarCompra(java.lang.String email, java.lang.String senha, java.lang.String sku, java.lang.String payload, java.lang.String token, java.lang.String orderId) throws java.io.IOException {
    ConfirmarCompra result = new ConfirmarCompra(email, senha, sku, payload, token, orderId);
    initialize(result);
    return result;
  }

  public class ConfirmarCompra extends CompraEndpointRequest<com.ctm.eadvogado.endpoints.compraEndpoint.model.Compra> {

    private static final String REST_PATH = "confirmarCompra/{email}/{senha}/{sku}/{payload}/{token}/{orderId}";

    /**
     * Create a request for the method "confirmarCompra".
     *
     * This request holds the parameters needed by the the compraEndpoint server.  After setting any
     * optional parameters, call the {@link ConfirmarCompra#execute()} method to invoke the remote
     * operation. <p> {@link ConfirmarCompra#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param email
     * @param senha
     * @param sku
     * @param payload
     * @param token
     * @param orderId
     * @since 1.13
     */
    protected ConfirmarCompra(java.lang.String email, java.lang.String senha, java.lang.String sku, java.lang.String payload, java.lang.String token, java.lang.String orderId) {
      super(CompraEndpoint.this, "POST", REST_PATH, null, com.ctm.eadvogado.endpoints.compraEndpoint.model.Compra.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.senha = com.google.api.client.util.Preconditions.checkNotNull(senha, "Required parameter senha must be specified.");
      this.sku = com.google.api.client.util.Preconditions.checkNotNull(sku, "Required parameter sku must be specified.");
      this.payload = com.google.api.client.util.Preconditions.checkNotNull(payload, "Required parameter payload must be specified.");
      this.token = com.google.api.client.util.Preconditions.checkNotNull(token, "Required parameter token must be specified.");
      this.orderId = com.google.api.client.util.Preconditions.checkNotNull(orderId, "Required parameter orderId must be specified.");
    }

    @Override
    public ConfirmarCompra setAlt(java.lang.String alt) {
      return (ConfirmarCompra) super.setAlt(alt);
    }

    @Override
    public ConfirmarCompra setFields(java.lang.String fields) {
      return (ConfirmarCompra) super.setFields(fields);
    }

    @Override
    public ConfirmarCompra setKey(java.lang.String key) {
      return (ConfirmarCompra) super.setKey(key);
    }

    @Override
    public ConfirmarCompra setOauthToken(java.lang.String oauthToken) {
      return (ConfirmarCompra) super.setOauthToken(oauthToken);
    }

    @Override
    public ConfirmarCompra setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ConfirmarCompra) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ConfirmarCompra setQuotaUser(java.lang.String quotaUser) {
      return (ConfirmarCompra) super.setQuotaUser(quotaUser);
    }

    @Override
    public ConfirmarCompra setUserIp(java.lang.String userIp) {
      return (ConfirmarCompra) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public ConfirmarCompra setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String senha;

    /**

     */
    public java.lang.String getSenha() {
      return senha;
    }

    public ConfirmarCompra setSenha(java.lang.String senha) {
      this.senha = senha;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String sku;

    /**

     */
    public java.lang.String getSku() {
      return sku;
    }

    public ConfirmarCompra setSku(java.lang.String sku) {
      this.sku = sku;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String payload;

    /**

     */
    public java.lang.String getPayload() {
      return payload;
    }

    public ConfirmarCompra setPayload(java.lang.String payload) {
      this.payload = payload;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String token;

    /**

     */
    public java.lang.String getToken() {
      return token;
    }

    public ConfirmarCompra setToken(java.lang.String token) {
      this.token = token;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String orderId;

    /**

     */
    public java.lang.String getOrderId() {
      return orderId;
    }

    public ConfirmarCompra setOrderId(java.lang.String orderId) {
      this.orderId = orderId;
      return this;
    }

    @Override
    public ConfirmarCompra set(String parameterName, Object value) {
      return (ConfirmarCompra) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "confirmarCompraPendente".
   *
   * This request holds the parameters needed by the the compraEndpoint server.  After setting any
   * optional parameters, call the {@link ConfirmarCompraPendente#execute()} method to invoke the
   * remote operation.
   *
   * @param email
   * @param senha
   * @param sku
   * @param payload
   * @return the request
   */
  public ConfirmarCompraPendente confirmarCompraPendente(java.lang.String email, java.lang.String senha, java.lang.String sku, java.lang.String payload) throws java.io.IOException {
    ConfirmarCompraPendente result = new ConfirmarCompraPendente(email, senha, sku, payload);
    initialize(result);
    return result;
  }

  public class ConfirmarCompraPendente extends CompraEndpointRequest<com.ctm.eadvogado.endpoints.compraEndpoint.model.Compra> {

    private static final String REST_PATH = "confirmarCompraPendente/{email}/{senha}/{sku}/{payload}";

    /**
     * Create a request for the method "confirmarCompraPendente".
     *
     * This request holds the parameters needed by the the compraEndpoint server.  After setting any
     * optional parameters, call the {@link ConfirmarCompraPendente#execute()} method to invoke the
     * remote operation. <p> {@link ConfirmarCompraPendente#initialize(com.google.api.client.googleapi
     * s.services.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param email
     * @param senha
     * @param sku
     * @param payload
     * @since 1.13
     */
    protected ConfirmarCompraPendente(java.lang.String email, java.lang.String senha, java.lang.String sku, java.lang.String payload) {
      super(CompraEndpoint.this, "POST", REST_PATH, null, com.ctm.eadvogado.endpoints.compraEndpoint.model.Compra.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.senha = com.google.api.client.util.Preconditions.checkNotNull(senha, "Required parameter senha must be specified.");
      this.sku = com.google.api.client.util.Preconditions.checkNotNull(sku, "Required parameter sku must be specified.");
      this.payload = com.google.api.client.util.Preconditions.checkNotNull(payload, "Required parameter payload must be specified.");
    }

    @Override
    public ConfirmarCompraPendente setAlt(java.lang.String alt) {
      return (ConfirmarCompraPendente) super.setAlt(alt);
    }

    @Override
    public ConfirmarCompraPendente setFields(java.lang.String fields) {
      return (ConfirmarCompraPendente) super.setFields(fields);
    }

    @Override
    public ConfirmarCompraPendente setKey(java.lang.String key) {
      return (ConfirmarCompraPendente) super.setKey(key);
    }

    @Override
    public ConfirmarCompraPendente setOauthToken(java.lang.String oauthToken) {
      return (ConfirmarCompraPendente) super.setOauthToken(oauthToken);
    }

    @Override
    public ConfirmarCompraPendente setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ConfirmarCompraPendente) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ConfirmarCompraPendente setQuotaUser(java.lang.String quotaUser) {
      return (ConfirmarCompraPendente) super.setQuotaUser(quotaUser);
    }

    @Override
    public ConfirmarCompraPendente setUserIp(java.lang.String userIp) {
      return (ConfirmarCompraPendente) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public ConfirmarCompraPendente setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String senha;

    /**

     */
    public java.lang.String getSenha() {
      return senha;
    }

    public ConfirmarCompraPendente setSenha(java.lang.String senha) {
      this.senha = senha;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String sku;

    /**

     */
    public java.lang.String getSku() {
      return sku;
    }

    public ConfirmarCompraPendente setSku(java.lang.String sku) {
      this.sku = sku;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String payload;

    /**

     */
    public java.lang.String getPayload() {
      return payload;
    }

    public ConfirmarCompraPendente setPayload(java.lang.String payload) {
      this.payload = payload;
      return this;
    }

    @Override
    public ConfirmarCompraPendente set(String parameterName, Object value) {
      return (ConfirmarCompraPendente) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "gerarCompraPendente".
   *
   * This request holds the parameters needed by the the compraEndpoint server.  After setting any
   * optional parameters, call the {@link GerarCompraPendente#execute()} method to invoke the remote
   * operation.
   *
   * @param email
   * @param senha
   * @param sku
   * @return the request
   */
  public GerarCompraPendente gerarCompraPendente(java.lang.String email, java.lang.String senha, java.lang.String sku) throws java.io.IOException {
    GerarCompraPendente result = new GerarCompraPendente(email, senha, sku);
    initialize(result);
    return result;
  }

  public class GerarCompraPendente extends CompraEndpointRequest<com.ctm.eadvogado.endpoints.compraEndpoint.model.Compra> {

    private static final String REST_PATH = "gerarCompraPendente/{email}/{senha}/{sku}";

    /**
     * Create a request for the method "gerarCompraPendente".
     *
     * This request holds the parameters needed by the the compraEndpoint server.  After setting any
     * optional parameters, call the {@link GerarCompraPendente#execute()} method to invoke the remote
     * operation. <p> {@link GerarCompraPendente#initialize(com.google.api.client.googleapis.services.
     * AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param email
     * @param senha
     * @param sku
     * @since 1.13
     */
    protected GerarCompraPendente(java.lang.String email, java.lang.String senha, java.lang.String sku) {
      super(CompraEndpoint.this, "POST", REST_PATH, null, com.ctm.eadvogado.endpoints.compraEndpoint.model.Compra.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.senha = com.google.api.client.util.Preconditions.checkNotNull(senha, "Required parameter senha must be specified.");
      this.sku = com.google.api.client.util.Preconditions.checkNotNull(sku, "Required parameter sku must be specified.");
    }

    @Override
    public GerarCompraPendente setAlt(java.lang.String alt) {
      return (GerarCompraPendente) super.setAlt(alt);
    }

    @Override
    public GerarCompraPendente setFields(java.lang.String fields) {
      return (GerarCompraPendente) super.setFields(fields);
    }

    @Override
    public GerarCompraPendente setKey(java.lang.String key) {
      return (GerarCompraPendente) super.setKey(key);
    }

    @Override
    public GerarCompraPendente setOauthToken(java.lang.String oauthToken) {
      return (GerarCompraPendente) super.setOauthToken(oauthToken);
    }

    @Override
    public GerarCompraPendente setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GerarCompraPendente) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GerarCompraPendente setQuotaUser(java.lang.String quotaUser) {
      return (GerarCompraPendente) super.setQuotaUser(quotaUser);
    }

    @Override
    public GerarCompraPendente setUserIp(java.lang.String userIp) {
      return (GerarCompraPendente) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public GerarCompraPendente setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String senha;

    /**

     */
    public java.lang.String getSenha() {
      return senha;
    }

    public GerarCompraPendente setSenha(java.lang.String senha) {
      this.senha = senha;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String sku;

    /**

     */
    public java.lang.String getSku() {
      return sku;
    }

    public GerarCompraPendente setSku(java.lang.String sku) {
      this.sku = sku;
      return this;
    }

    @Override
    public GerarCompraPendente set(String parameterName, Object value) {
      return (GerarCompraPendente) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link CompraEndpoint}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link CompraEndpoint}. */
    @Override
    public CompraEndpoint build() {
      return new CompraEndpoint(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link CompraEndpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setCompraEndpointRequestInitializer(
        CompraEndpointRequestInitializer compraendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(compraendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
