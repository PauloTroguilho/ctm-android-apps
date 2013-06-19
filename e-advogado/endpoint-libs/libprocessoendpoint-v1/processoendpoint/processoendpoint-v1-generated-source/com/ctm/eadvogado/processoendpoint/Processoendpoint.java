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
 * (build: 2013-06-05 16:09:48 UTC)
 * on 2013-06-19 at 06:09:56 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.processoendpoint;

/**
 * Service definition for Processoendpoint (v1).
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
 * This service uses {@link ProcessoendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Processoendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

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
  public static final String DEFAULT_ROOT_URL = "https://e-advogado.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "processoendpoint/v1/";

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
  public Processoendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Processoendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "associarProcesso".
   *
   * This request holds the parameters needed by the the processoendpoint server.  After setting any
   * optional parameters, call the {@link AssociarProcesso#execute()} method to invoke the remote
   * operation.
   *
   * @param npu
   * @param tipoJuizo
   * @param idTribunal
   * @param email
   * @return the request
   */
  public AssociarProcesso associarProcesso(java.lang.String npu, java.lang.String tipoJuizo, java.lang.Long idTribunal, java.lang.String email) throws java.io.IOException {
    AssociarProcesso result = new AssociarProcesso(npu, tipoJuizo, idTribunal, email);
    initialize(result);
    return result;
  }

  public class AssociarProcesso extends ProcessoendpointRequest<com.ctm.eadvogado.processoendpoint.model.Processo> {

    private static final String REST_PATH = "associarProcesso/{npu}/{tipoJuizo}/{idTribunal}/{email}";

    /**
     * Create a request for the method "associarProcesso".
     *
     * This request holds the parameters needed by the the processoendpoint server.  After setting any
     * optional parameters, call the {@link AssociarProcesso#execute()} method to invoke the remote
     * operation. <p> {@link AssociarProcesso#initialize(com.google.api.client.googleapis.services.Abs
     * tractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param npu
     * @param tipoJuizo
     * @param idTribunal
     * @param email
     * @since 1.13
     */
    protected AssociarProcesso(java.lang.String npu, java.lang.String tipoJuizo, java.lang.Long idTribunal, java.lang.String email) {
      super(Processoendpoint.this, "POST", REST_PATH, null, com.ctm.eadvogado.processoendpoint.model.Processo.class);
      this.npu = com.google.api.client.util.Preconditions.checkNotNull(npu, "Required parameter npu must be specified.");
      this.tipoJuizo = com.google.api.client.util.Preconditions.checkNotNull(tipoJuizo, "Required parameter tipoJuizo must be specified.");
      this.idTribunal = com.google.api.client.util.Preconditions.checkNotNull(idTribunal, "Required parameter idTribunal must be specified.");
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
    }

    @Override
    public AssociarProcesso setAlt(java.lang.String alt) {
      return (AssociarProcesso) super.setAlt(alt);
    }

    @Override
    public AssociarProcesso setFields(java.lang.String fields) {
      return (AssociarProcesso) super.setFields(fields);
    }

    @Override
    public AssociarProcesso setKey(java.lang.String key) {
      return (AssociarProcesso) super.setKey(key);
    }

    @Override
    public AssociarProcesso setOauthToken(java.lang.String oauthToken) {
      return (AssociarProcesso) super.setOauthToken(oauthToken);
    }

    @Override
    public AssociarProcesso setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (AssociarProcesso) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public AssociarProcesso setQuotaUser(java.lang.String quotaUser) {
      return (AssociarProcesso) super.setQuotaUser(quotaUser);
    }

    @Override
    public AssociarProcesso setUserIp(java.lang.String userIp) {
      return (AssociarProcesso) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String npu;

    /**

     */
    public java.lang.String getNpu() {
      return npu;
    }

    public AssociarProcesso setNpu(java.lang.String npu) {
      this.npu = npu;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String tipoJuizo;

    /**

     */
    public java.lang.String getTipoJuizo() {
      return tipoJuizo;
    }

    public AssociarProcesso setTipoJuizo(java.lang.String tipoJuizo) {
      this.tipoJuizo = tipoJuizo;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Long idTribunal;

    /**

     */
    public java.lang.Long getIdTribunal() {
      return idTribunal;
    }

    public AssociarProcesso setIdTribunal(java.lang.Long idTribunal) {
      this.idTribunal = idTribunal;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public AssociarProcesso setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @Override
    public AssociarProcesso set(String parameterName, Object value) {
      return (AssociarProcesso) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "consultarProcesso".
   *
   * This request holds the parameters needed by the the processoendpoint server.  After setting any
   * optional parameters, call the {@link ConsultarProcesso#execute()} method to invoke the remote
   * operation.
   *
   * @param npu
   * @param tipoJuizo
   * @param idTribunal
   * @return the request
   */
  public ConsultarProcesso consultarProcesso(java.lang.String npu, java.lang.String tipoJuizo, java.lang.Long idTribunal) throws java.io.IOException {
    ConsultarProcesso result = new ConsultarProcesso(npu, tipoJuizo, idTribunal);
    initialize(result);
    return result;
  }

  public class ConsultarProcesso extends ProcessoendpointRequest<com.ctm.eadvogado.processoendpoint.model.Processo> {

    private static final String REST_PATH = "consultarProcesso/{npu}/{tipoJuizo}/{idTribunal}";

    /**
     * Create a request for the method "consultarProcesso".
     *
     * This request holds the parameters needed by the the processoendpoint server.  After setting any
     * optional parameters, call the {@link ConsultarProcesso#execute()} method to invoke the remote
     * operation. <p> {@link ConsultarProcesso#initialize(com.google.api.client.googleapis.services.Ab
     * stractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param npu
     * @param tipoJuizo
     * @param idTribunal
     * @since 1.13
     */
    protected ConsultarProcesso(java.lang.String npu, java.lang.String tipoJuizo, java.lang.Long idTribunal) {
      super(Processoendpoint.this, "POST", REST_PATH, null, com.ctm.eadvogado.processoendpoint.model.Processo.class);
      this.npu = com.google.api.client.util.Preconditions.checkNotNull(npu, "Required parameter npu must be specified.");
      this.tipoJuizo = com.google.api.client.util.Preconditions.checkNotNull(tipoJuizo, "Required parameter tipoJuizo must be specified.");
      this.idTribunal = com.google.api.client.util.Preconditions.checkNotNull(idTribunal, "Required parameter idTribunal must be specified.");
    }

    @Override
    public ConsultarProcesso setAlt(java.lang.String alt) {
      return (ConsultarProcesso) super.setAlt(alt);
    }

    @Override
    public ConsultarProcesso setFields(java.lang.String fields) {
      return (ConsultarProcesso) super.setFields(fields);
    }

    @Override
    public ConsultarProcesso setKey(java.lang.String key) {
      return (ConsultarProcesso) super.setKey(key);
    }

    @Override
    public ConsultarProcesso setOauthToken(java.lang.String oauthToken) {
      return (ConsultarProcesso) super.setOauthToken(oauthToken);
    }

    @Override
    public ConsultarProcesso setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ConsultarProcesso) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ConsultarProcesso setQuotaUser(java.lang.String quotaUser) {
      return (ConsultarProcesso) super.setQuotaUser(quotaUser);
    }

    @Override
    public ConsultarProcesso setUserIp(java.lang.String userIp) {
      return (ConsultarProcesso) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String npu;

    /**

     */
    public java.lang.String getNpu() {
      return npu;
    }

    public ConsultarProcesso setNpu(java.lang.String npu) {
      this.npu = npu;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String tipoJuizo;

    /**

     */
    public java.lang.String getTipoJuizo() {
      return tipoJuizo;
    }

    public ConsultarProcesso setTipoJuizo(java.lang.String tipoJuizo) {
      this.tipoJuizo = tipoJuizo;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Long idTribunal;

    /**

     */
    public java.lang.Long getIdTribunal() {
      return idTribunal;
    }

    public ConsultarProcesso setIdTribunal(java.lang.Long idTribunal) {
      this.idTribunal = idTribunal;
      return this;
    }

    @Override
    public ConsultarProcesso set(String parameterName, Object value) {
      return (ConsultarProcesso) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getProcesso".
   *
   * This request holds the parameters needed by the the processoendpoint server.  After setting any
   * optional parameters, call the {@link GetProcesso#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetProcesso getProcesso(java.lang.Long id) throws java.io.IOException {
    GetProcesso result = new GetProcesso(id);
    initialize(result);
    return result;
  }

  public class GetProcesso extends ProcessoendpointRequest<com.ctm.eadvogado.processoendpoint.model.Processo> {

    private static final String REST_PATH = "processo/{id}";

    /**
     * Create a request for the method "getProcesso".
     *
     * This request holds the parameters needed by the the processoendpoint server.  After setting any
     * optional parameters, call the {@link GetProcesso#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetProcesso#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetProcesso(java.lang.Long id) {
      super(Processoendpoint.this, "GET", REST_PATH, null, com.ctm.eadvogado.processoendpoint.model.Processo.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetProcesso setAlt(java.lang.String alt) {
      return (GetProcesso) super.setAlt(alt);
    }

    @Override
    public GetProcesso setFields(java.lang.String fields) {
      return (GetProcesso) super.setFields(fields);
    }

    @Override
    public GetProcesso setKey(java.lang.String key) {
      return (GetProcesso) super.setKey(key);
    }

    @Override
    public GetProcesso setOauthToken(java.lang.String oauthToken) {
      return (GetProcesso) super.setOauthToken(oauthToken);
    }

    @Override
    public GetProcesso setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetProcesso) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetProcesso setQuotaUser(java.lang.String quotaUser) {
      return (GetProcesso) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetProcesso setUserIp(java.lang.String userIp) {
      return (GetProcesso) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetProcesso setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetProcesso set(String parameterName, Object value) {
      return (GetProcesso) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getProcessoPorNPU".
   *
   * This request holds the parameters needed by the the processoendpoint server.  After setting any
   * optional parameters, call the {@link GetProcessoPorNPU#execute()} method to invoke the remote
   * operation.
   *
   * @param npu
   * @return the request
   */
  public GetProcessoPorNPU getProcessoPorNPU(java.lang.String npu) throws java.io.IOException {
    GetProcessoPorNPU result = new GetProcessoPorNPU(npu);
    initialize(result);
    return result;
  }

  public class GetProcessoPorNPU extends ProcessoendpointRequest<com.ctm.eadvogado.processoendpoint.model.Processo> {

    private static final String REST_PATH = "getProcessoPorNPU/{npu}";

    /**
     * Create a request for the method "getProcessoPorNPU".
     *
     * This request holds the parameters needed by the the processoendpoint server.  After setting any
     * optional parameters, call the {@link GetProcessoPorNPU#execute()} method to invoke the remote
     * operation. <p> {@link GetProcessoPorNPU#initialize(com.google.api.client.googleapis.services.Ab
     * stractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param npu
     * @since 1.13
     */
    protected GetProcessoPorNPU(java.lang.String npu) {
      super(Processoendpoint.this, "GET", REST_PATH, null, com.ctm.eadvogado.processoendpoint.model.Processo.class);
      this.npu = com.google.api.client.util.Preconditions.checkNotNull(npu, "Required parameter npu must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetProcessoPorNPU setAlt(java.lang.String alt) {
      return (GetProcessoPorNPU) super.setAlt(alt);
    }

    @Override
    public GetProcessoPorNPU setFields(java.lang.String fields) {
      return (GetProcessoPorNPU) super.setFields(fields);
    }

    @Override
    public GetProcessoPorNPU setKey(java.lang.String key) {
      return (GetProcessoPorNPU) super.setKey(key);
    }

    @Override
    public GetProcessoPorNPU setOauthToken(java.lang.String oauthToken) {
      return (GetProcessoPorNPU) super.setOauthToken(oauthToken);
    }

    @Override
    public GetProcessoPorNPU setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetProcessoPorNPU) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetProcessoPorNPU setQuotaUser(java.lang.String quotaUser) {
      return (GetProcessoPorNPU) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetProcessoPorNPU setUserIp(java.lang.String userIp) {
      return (GetProcessoPorNPU) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String npu;

    /**

     */
    public java.lang.String getNpu() {
      return npu;
    }

    public GetProcessoPorNPU setNpu(java.lang.String npu) {
      this.npu = npu;
      return this;
    }

    @Override
    public GetProcessoPorNPU set(String parameterName, Object value) {
      return (GetProcessoPorNPU) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertProcesso".
   *
   * This request holds the parameters needed by the the processoendpoint server.  After setting any
   * optional parameters, call the {@link InsertProcesso#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.ctm.eadvogado.processoendpoint.model.Processo}
   * @return the request
   */
  public InsertProcesso insertProcesso(com.ctm.eadvogado.processoendpoint.model.Processo content) throws java.io.IOException {
    InsertProcesso result = new InsertProcesso(content);
    initialize(result);
    return result;
  }

  public class InsertProcesso extends ProcessoendpointRequest<com.ctm.eadvogado.processoendpoint.model.Processo> {

    private static final String REST_PATH = "processo";

    /**
     * Create a request for the method "insertProcesso".
     *
     * This request holds the parameters needed by the the processoendpoint server.  After setting any
     * optional parameters, call the {@link InsertProcesso#execute()} method to invoke the remote
     * operation. <p> {@link InsertProcesso#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.ctm.eadvogado.processoendpoint.model.Processo}
     * @since 1.13
     */
    protected InsertProcesso(com.ctm.eadvogado.processoendpoint.model.Processo content) {
      super(Processoendpoint.this, "POST", REST_PATH, content, com.ctm.eadvogado.processoendpoint.model.Processo.class);
    }

    @Override
    public InsertProcesso setAlt(java.lang.String alt) {
      return (InsertProcesso) super.setAlt(alt);
    }

    @Override
    public InsertProcesso setFields(java.lang.String fields) {
      return (InsertProcesso) super.setFields(fields);
    }

    @Override
    public InsertProcesso setKey(java.lang.String key) {
      return (InsertProcesso) super.setKey(key);
    }

    @Override
    public InsertProcesso setOauthToken(java.lang.String oauthToken) {
      return (InsertProcesso) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertProcesso setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertProcesso) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertProcesso setQuotaUser(java.lang.String quotaUser) {
      return (InsertProcesso) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertProcesso setUserIp(java.lang.String userIp) {
      return (InsertProcesso) super.setUserIp(userIp);
    }

    @Override
    public InsertProcesso set(String parameterName, Object value) {
      return (InsertProcesso) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listProcesso".
   *
   * This request holds the parameters needed by the the processoendpoint server.  After setting any
   * optional parameters, call the {@link ListProcesso#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListProcesso listProcesso() throws java.io.IOException {
    ListProcesso result = new ListProcesso();
    initialize(result);
    return result;
  }

  public class ListProcesso extends ProcessoendpointRequest<com.ctm.eadvogado.processoendpoint.model.CollectionResponseProcesso> {

    private static final String REST_PATH = "processo";

    /**
     * Create a request for the method "listProcesso".
     *
     * This request holds the parameters needed by the the processoendpoint server.  After setting any
     * optional parameters, call the {@link ListProcesso#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListProcesso#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListProcesso() {
      super(Processoendpoint.this, "GET", REST_PATH, null, com.ctm.eadvogado.processoendpoint.model.CollectionResponseProcesso.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListProcesso setAlt(java.lang.String alt) {
      return (ListProcesso) super.setAlt(alt);
    }

    @Override
    public ListProcesso setFields(java.lang.String fields) {
      return (ListProcesso) super.setFields(fields);
    }

    @Override
    public ListProcesso setKey(java.lang.String key) {
      return (ListProcesso) super.setKey(key);
    }

    @Override
    public ListProcesso setOauthToken(java.lang.String oauthToken) {
      return (ListProcesso) super.setOauthToken(oauthToken);
    }

    @Override
    public ListProcesso setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListProcesso) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListProcesso setQuotaUser(java.lang.String quotaUser) {
      return (ListProcesso) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListProcesso setUserIp(java.lang.String userIp) {
      return (ListProcesso) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListProcesso setCursor(java.lang.String cursor) {
      this.cursor = cursor;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer limit;

    /**

     */
    public java.lang.Integer getLimit() {
      return limit;
    }

    public ListProcesso setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListProcesso set(String parameterName, Object value) {
      return (ListProcesso) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listProcessoNPUsPorAdvogado".
   *
   * This request holds the parameters needed by the the processoendpoint server.  After setting any
   * optional parameters, call the {@link ListProcessoNPUsPorAdvogado#execute()} method to invoke the
   * remote operation.
   *
   * @param email
   * @param senha
   * @return the request
   */
  public ListProcessoNPUsPorAdvogado listProcessoNPUsPorAdvogado(java.lang.String email, java.lang.String senha) throws java.io.IOException {
    ListProcessoNPUsPorAdvogado result = new ListProcessoNPUsPorAdvogado(email, senha);
    initialize(result);
    return result;
  }

  public class ListProcessoNPUsPorAdvogado extends ProcessoendpointRequest<com.ctm.eadvogado.processoendpoint.model.ProcessoCollection> {

    private static final String REST_PATH = "processo/{email}/{senha}";

    /**
     * Create a request for the method "listProcessoNPUsPorAdvogado".
     *
     * This request holds the parameters needed by the the processoendpoint server.  After setting any
     * optional parameters, call the {@link ListProcessoNPUsPorAdvogado#execute()} method to invoke
     * the remote operation. <p> {@link ListProcessoNPUsPorAdvogado#initialize(com.google.api.client.g
     * oogleapis.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @param email
     * @param senha
     * @since 1.13
     */
    protected ListProcessoNPUsPorAdvogado(java.lang.String email, java.lang.String senha) {
      super(Processoendpoint.this, "GET", REST_PATH, null, com.ctm.eadvogado.processoendpoint.model.ProcessoCollection.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.senha = com.google.api.client.util.Preconditions.checkNotNull(senha, "Required parameter senha must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListProcessoNPUsPorAdvogado setAlt(java.lang.String alt) {
      return (ListProcessoNPUsPorAdvogado) super.setAlt(alt);
    }

    @Override
    public ListProcessoNPUsPorAdvogado setFields(java.lang.String fields) {
      return (ListProcessoNPUsPorAdvogado) super.setFields(fields);
    }

    @Override
    public ListProcessoNPUsPorAdvogado setKey(java.lang.String key) {
      return (ListProcessoNPUsPorAdvogado) super.setKey(key);
    }

    @Override
    public ListProcessoNPUsPorAdvogado setOauthToken(java.lang.String oauthToken) {
      return (ListProcessoNPUsPorAdvogado) super.setOauthToken(oauthToken);
    }

    @Override
    public ListProcessoNPUsPorAdvogado setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListProcessoNPUsPorAdvogado) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListProcessoNPUsPorAdvogado setQuotaUser(java.lang.String quotaUser) {
      return (ListProcessoNPUsPorAdvogado) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListProcessoNPUsPorAdvogado setUserIp(java.lang.String userIp) {
      return (ListProcessoNPUsPorAdvogado) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public ListProcessoNPUsPorAdvogado setEmail(java.lang.String email) {
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

    public ListProcessoNPUsPorAdvogado setSenha(java.lang.String senha) {
      this.senha = senha;
      return this;
    }

    @Override
    public ListProcessoNPUsPorAdvogado set(String parameterName, Object value) {
      return (ListProcessoNPUsPorAdvogado) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeProcesso".
   *
   * This request holds the parameters needed by the the processoendpoint server.  After setting any
   * optional parameters, call the {@link RemoveProcesso#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveProcesso removeProcesso(java.lang.Long id) throws java.io.IOException {
    RemoveProcesso result = new RemoveProcesso(id);
    initialize(result);
    return result;
  }

  public class RemoveProcesso extends ProcessoendpointRequest<com.ctm.eadvogado.processoendpoint.model.Processo> {

    private static final String REST_PATH = "processo/{id}";

    /**
     * Create a request for the method "removeProcesso".
     *
     * This request holds the parameters needed by the the processoendpoint server.  After setting any
     * optional parameters, call the {@link RemoveProcesso#execute()} method to invoke the remote
     * operation. <p> {@link RemoveProcesso#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveProcesso(java.lang.Long id) {
      super(Processoendpoint.this, "DELETE", REST_PATH, null, com.ctm.eadvogado.processoendpoint.model.Processo.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveProcesso setAlt(java.lang.String alt) {
      return (RemoveProcesso) super.setAlt(alt);
    }

    @Override
    public RemoveProcesso setFields(java.lang.String fields) {
      return (RemoveProcesso) super.setFields(fields);
    }

    @Override
    public RemoveProcesso setKey(java.lang.String key) {
      return (RemoveProcesso) super.setKey(key);
    }

    @Override
    public RemoveProcesso setOauthToken(java.lang.String oauthToken) {
      return (RemoveProcesso) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveProcesso setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveProcesso) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveProcesso setQuotaUser(java.lang.String quotaUser) {
      return (RemoveProcesso) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveProcesso setUserIp(java.lang.String userIp) {
      return (RemoveProcesso) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveProcesso setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveProcesso set(String parameterName, Object value) {
      return (RemoveProcesso) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateProcesso".
   *
   * This request holds the parameters needed by the the processoendpoint server.  After setting any
   * optional parameters, call the {@link UpdateProcesso#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.ctm.eadvogado.processoendpoint.model.Processo}
   * @return the request
   */
  public UpdateProcesso updateProcesso(com.ctm.eadvogado.processoendpoint.model.Processo content) throws java.io.IOException {
    UpdateProcesso result = new UpdateProcesso(content);
    initialize(result);
    return result;
  }

  public class UpdateProcesso extends ProcessoendpointRequest<com.ctm.eadvogado.processoendpoint.model.Processo> {

    private static final String REST_PATH = "processo";

    /**
     * Create a request for the method "updateProcesso".
     *
     * This request holds the parameters needed by the the processoendpoint server.  After setting any
     * optional parameters, call the {@link UpdateProcesso#execute()} method to invoke the remote
     * operation. <p> {@link UpdateProcesso#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.ctm.eadvogado.processoendpoint.model.Processo}
     * @since 1.13
     */
    protected UpdateProcesso(com.ctm.eadvogado.processoendpoint.model.Processo content) {
      super(Processoendpoint.this, "PUT", REST_PATH, content, com.ctm.eadvogado.processoendpoint.model.Processo.class);
    }

    @Override
    public UpdateProcesso setAlt(java.lang.String alt) {
      return (UpdateProcesso) super.setAlt(alt);
    }

    @Override
    public UpdateProcesso setFields(java.lang.String fields) {
      return (UpdateProcesso) super.setFields(fields);
    }

    @Override
    public UpdateProcesso setKey(java.lang.String key) {
      return (UpdateProcesso) super.setKey(key);
    }

    @Override
    public UpdateProcesso setOauthToken(java.lang.String oauthToken) {
      return (UpdateProcesso) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateProcesso setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateProcesso) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateProcesso setQuotaUser(java.lang.String quotaUser) {
      return (UpdateProcesso) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateProcesso setUserIp(java.lang.String userIp) {
      return (UpdateProcesso) super.setUserIp(userIp);
    }

    @Override
    public UpdateProcesso set(String parameterName, Object value) {
      return (UpdateProcesso) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Processoendpoint}.
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

    /** Builds a new instance of {@link Processoendpoint}. */
    @Override
    public Processoendpoint build() {
      return new Processoendpoint(this);
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
     * Set the {@link ProcessoendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setProcessoendpointRequestInitializer(
        ProcessoendpointRequestInitializer processoendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(processoendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
