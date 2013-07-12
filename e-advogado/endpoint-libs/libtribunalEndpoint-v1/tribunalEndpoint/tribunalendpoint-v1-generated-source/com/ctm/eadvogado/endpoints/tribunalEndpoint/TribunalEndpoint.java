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
 * (build: 2013-06-26 16:27:34 UTC)
 * on 2013-07-12 at 04:00:41 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.endpoints.tribunalEndpoint;

/**
 * Service definition for TribunalEndpoint (v1).
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
 * This service uses {@link TribunalEndpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class TribunalEndpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

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
  public static final String DEFAULT_SERVICE_PATH = "tribunalEndpoint/v1/";

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
  public TribunalEndpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  TribunalEndpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getByID".
   *
   * This request holds the parameters needed by the the tribunalEndpoint server.  After setting any
   * optional parameters, call the {@link GetByID#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetByID getByID(java.lang.Long id) throws java.io.IOException {
    GetByID result = new GetByID(id);
    initialize(result);
    return result;
  }

  public class GetByID extends TribunalEndpointRequest<com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal> {

    private static final String REST_PATH = "tribunal/{id}";

    /**
     * Create a request for the method "getByID".
     *
     * This request holds the parameters needed by the the tribunalEndpoint server.  After setting any
     * optional parameters, call the {@link GetByID#execute()} method to invoke the remote operation.
     * <p> {@link
     * GetByID#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetByID(java.lang.Long id) {
      super(TribunalEndpoint.this, "GET", REST_PATH, null, com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal.class);
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
    public GetByID setAlt(java.lang.String alt) {
      return (GetByID) super.setAlt(alt);
    }

    @Override
    public GetByID setFields(java.lang.String fields) {
      return (GetByID) super.setFields(fields);
    }

    @Override
    public GetByID setKey(java.lang.String key) {
      return (GetByID) super.setKey(key);
    }

    @Override
    public GetByID setOauthToken(java.lang.String oauthToken) {
      return (GetByID) super.setOauthToken(oauthToken);
    }

    @Override
    public GetByID setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetByID) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetByID setQuotaUser(java.lang.String quotaUser) {
      return (GetByID) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetByID setUserIp(java.lang.String userIp) {
      return (GetByID) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetByID setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetByID set(String parameterName, Object value) {
      return (GetByID) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insert".
   *
   * This request holds the parameters needed by the the tribunalEndpoint server.  After setting any
   * optional parameters, call the {@link Insert#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal}
   * @return the request
   */
  public Insert insert(com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal content) throws java.io.IOException {
    Insert result = new Insert(content);
    initialize(result);
    return result;
  }

  public class Insert extends TribunalEndpointRequest<com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal> {

    private static final String REST_PATH = "tribunal";

    /**
     * Create a request for the method "insert".
     *
     * This request holds the parameters needed by the the tribunalEndpoint server.  After setting any
     * optional parameters, call the {@link Insert#execute()} method to invoke the remote operation.
     * <p> {@link
     * Insert#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal}
     * @since 1.13
     */
    protected Insert(com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal content) {
      super(TribunalEndpoint.this, "POST", REST_PATH, content, com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal.class);
    }

    @Override
    public Insert setAlt(java.lang.String alt) {
      return (Insert) super.setAlt(alt);
    }

    @Override
    public Insert setFields(java.lang.String fields) {
      return (Insert) super.setFields(fields);
    }

    @Override
    public Insert setKey(java.lang.String key) {
      return (Insert) super.setKey(key);
    }

    @Override
    public Insert setOauthToken(java.lang.String oauthToken) {
      return (Insert) super.setOauthToken(oauthToken);
    }

    @Override
    public Insert setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Insert) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Insert setQuotaUser(java.lang.String quotaUser) {
      return (Insert) super.setQuotaUser(quotaUser);
    }

    @Override
    public Insert setUserIp(java.lang.String userIp) {
      return (Insert) super.setUserIp(userIp);
    }

    @Override
    public Insert set(String parameterName, Object value) {
      return (Insert) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listAll".
   *
   * This request holds the parameters needed by the the tribunalEndpoint server.  After setting any
   * optional parameters, call the {@link ListAll#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public ListAll listAll() throws java.io.IOException {
    ListAll result = new ListAll();
    initialize(result);
    return result;
  }

  public class ListAll extends TribunalEndpointRequest<com.ctm.eadvogado.endpoints.tribunalEndpoint.model.CollectionResponseTribunal> {

    private static final String REST_PATH = "tribunal";

    /**
     * Create a request for the method "listAll".
     *
     * This request holds the parameters needed by the the tribunalEndpoint server.  After setting any
     * optional parameters, call the {@link ListAll#execute()} method to invoke the remote operation.
     * <p> {@link
     * ListAll#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListAll() {
      super(TribunalEndpoint.this, "GET", REST_PATH, null, com.ctm.eadvogado.endpoints.tribunalEndpoint.model.CollectionResponseTribunal.class);
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
    public ListAll setAlt(java.lang.String alt) {
      return (ListAll) super.setAlt(alt);
    }

    @Override
    public ListAll setFields(java.lang.String fields) {
      return (ListAll) super.setFields(fields);
    }

    @Override
    public ListAll setKey(java.lang.String key) {
      return (ListAll) super.setKey(key);
    }

    @Override
    public ListAll setOauthToken(java.lang.String oauthToken) {
      return (ListAll) super.setOauthToken(oauthToken);
    }

    @Override
    public ListAll setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListAll) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListAll setQuotaUser(java.lang.String quotaUser) {
      return (ListAll) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListAll setUserIp(java.lang.String userIp) {
      return (ListAll) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer firstReault;

    /**

     */
    public java.lang.Integer getFirstReault() {
      return firstReault;
    }

    public ListAll setFirstReault(java.lang.Integer firstReault) {
      this.firstReault = firstReault;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String sortField;

    /**

     */
    public java.lang.String getSortField() {
      return sortField;
    }

    public ListAll setSortField(java.lang.String sortField) {
      this.sortField = sortField;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String sortOrder;

    /**

     */
    public java.lang.String getSortOrder() {
      return sortOrder;
    }

    public ListAll setSortOrder(java.lang.String sortOrder) {
      this.sortOrder = sortOrder;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer maxResults;

    /**

     */
    public java.lang.Integer getMaxResults() {
      return maxResults;
    }

    public ListAll setMaxResults(java.lang.Integer maxResults) {
      this.maxResults = maxResults;
      return this;
    }

    @Override
    public ListAll set(String parameterName, Object value) {
      return (ListAll) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "remove".
   *
   * This request holds the parameters needed by the the tribunalEndpoint server.  After setting any
   * optional parameters, call the {@link Remove#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public Remove remove(java.lang.Long id) throws java.io.IOException {
    Remove result = new Remove(id);
    initialize(result);
    return result;
  }

  public class Remove extends TribunalEndpointRequest<com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal> {

    private static final String REST_PATH = "remove/{id}";

    /**
     * Create a request for the method "remove".
     *
     * This request holds the parameters needed by the the tribunalEndpoint server.  After setting any
     * optional parameters, call the {@link Remove#execute()} method to invoke the remote operation.
     * <p> {@link
     * Remove#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected Remove(java.lang.Long id) {
      super(TribunalEndpoint.this, "DELETE", REST_PATH, null, com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public Remove setAlt(java.lang.String alt) {
      return (Remove) super.setAlt(alt);
    }

    @Override
    public Remove setFields(java.lang.String fields) {
      return (Remove) super.setFields(fields);
    }

    @Override
    public Remove setKey(java.lang.String key) {
      return (Remove) super.setKey(key);
    }

    @Override
    public Remove setOauthToken(java.lang.String oauthToken) {
      return (Remove) super.setOauthToken(oauthToken);
    }

    @Override
    public Remove setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Remove) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Remove setQuotaUser(java.lang.String quotaUser) {
      return (Remove) super.setQuotaUser(quotaUser);
    }

    @Override
    public Remove setUserIp(java.lang.String userIp) {
      return (Remove) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public Remove setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public Remove set(String parameterName, Object value) {
      return (Remove) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "update".
   *
   * This request holds the parameters needed by the the tribunalEndpoint server.  After setting any
   * optional parameters, call the {@link Update#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal}
   * @return the request
   */
  public Update update(com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal content) throws java.io.IOException {
    Update result = new Update(content);
    initialize(result);
    return result;
  }

  public class Update extends TribunalEndpointRequest<com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal> {

    private static final String REST_PATH = "tribunal";

    /**
     * Create a request for the method "update".
     *
     * This request holds the parameters needed by the the tribunalEndpoint server.  After setting any
     * optional parameters, call the {@link Update#execute()} method to invoke the remote operation.
     * <p> {@link
     * Update#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal}
     * @since 1.13
     */
    protected Update(com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal content) {
      super(TribunalEndpoint.this, "PUT", REST_PATH, content, com.ctm.eadvogado.endpoints.tribunalEndpoint.model.Tribunal.class);
    }

    @Override
    public Update setAlt(java.lang.String alt) {
      return (Update) super.setAlt(alt);
    }

    @Override
    public Update setFields(java.lang.String fields) {
      return (Update) super.setFields(fields);
    }

    @Override
    public Update setKey(java.lang.String key) {
      return (Update) super.setKey(key);
    }

    @Override
    public Update setOauthToken(java.lang.String oauthToken) {
      return (Update) super.setOauthToken(oauthToken);
    }

    @Override
    public Update setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Update) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Update setQuotaUser(java.lang.String quotaUser) {
      return (Update) super.setQuotaUser(quotaUser);
    }

    @Override
    public Update setUserIp(java.lang.String userIp) {
      return (Update) super.setUserIp(userIp);
    }

    @Override
    public Update set(String parameterName, Object value) {
      return (Update) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link TribunalEndpoint}.
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

    /** Builds a new instance of {@link TribunalEndpoint}. */
    @Override
    public TribunalEndpoint build() {
      return new TribunalEndpoint(this);
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
     * Set the {@link TribunalEndpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setTribunalEndpointRequestInitializer(
        TribunalEndpointRequestInitializer tribunalendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(tribunalendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
