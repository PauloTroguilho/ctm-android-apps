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
 * (build: 2013-08-07 19:00:49 UTC)
 * on 2013-08-09 at 02:54:02 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.endpoints.compraEndpoint.model;

/**
 * Model definition for Compra.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Compra extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime data;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key key;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String orderId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String payload;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String situacao;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String sku;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String token;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key usuario;

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getData() {
    return data;
  }

  /**
   * @param data data or {@code null} for none
   */
  public Compra setData(com.google.api.client.util.DateTime data) {
    this.data = data;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Key getKey() {
    return key;
  }

  /**
   * @param key key or {@code null} for none
   */
  public Compra setKey(Key key) {
    this.key = key;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getOrderId() {
    return orderId;
  }

  /**
   * @param orderId orderId or {@code null} for none
   */
  public Compra setOrderId(java.lang.String orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPayload() {
    return payload;
  }

  /**
   * @param payload payload or {@code null} for none
   */
  public Compra setPayload(java.lang.String payload) {
    this.payload = payload;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSituacao() {
    return situacao;
  }

  /**
   * @param situacao situacao or {@code null} for none
   */
  public Compra setSituacao(java.lang.String situacao) {
    this.situacao = situacao;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSku() {
    return sku;
  }

  /**
   * @param sku sku or {@code null} for none
   */
  public Compra setSku(java.lang.String sku) {
    this.sku = sku;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getToken() {
    return token;
  }

  /**
   * @param token token or {@code null} for none
   */
  public Compra setToken(java.lang.String token) {
    this.token = token;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Key getUsuario() {
    return usuario;
  }

  /**
   * @param usuario usuario or {@code null} for none
   */
  public Compra setUsuario(Key usuario) {
    this.usuario = usuario;
    return this;
  }

  @Override
  public Compra set(String fieldName, Object value) {
    return (Compra) super.set(fieldName, value);
  }

  @Override
  public Compra clone() {
    return (Compra) super.clone();
  }

}
