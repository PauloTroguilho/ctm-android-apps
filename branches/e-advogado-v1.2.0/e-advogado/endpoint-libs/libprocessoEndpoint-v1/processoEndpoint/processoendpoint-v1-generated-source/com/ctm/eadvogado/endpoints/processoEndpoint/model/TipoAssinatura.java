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
 * on 2013-07-22 at 12:34:49 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.endpoints.processoEndpoint.model;

/**
 * Model definition for TipoAssinatura.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class TipoAssinatura extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String algoritmoHash;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String assinatura;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String cadeiaCertificado;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String dataAssinatura;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String value;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAlgoritmoHash() {
    return algoritmoHash;
  }

  /**
   * @param algoritmoHash algoritmoHash or {@code null} for none
   */
  public TipoAssinatura setAlgoritmoHash(java.lang.String algoritmoHash) {
    this.algoritmoHash = algoritmoHash;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAssinatura() {
    return assinatura;
  }

  /**
   * @param assinatura assinatura or {@code null} for none
   */
  public TipoAssinatura setAssinatura(java.lang.String assinatura) {
    this.assinatura = assinatura;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCadeiaCertificado() {
    return cadeiaCertificado;
  }

  /**
   * @param cadeiaCertificado cadeiaCertificado or {@code null} for none
   */
  public TipoAssinatura setCadeiaCertificado(java.lang.String cadeiaCertificado) {
    this.cadeiaCertificado = cadeiaCertificado;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDataAssinatura() {
    return dataAssinatura;
  }

  /**
   * @param dataAssinatura dataAssinatura or {@code null} for none
   */
  public TipoAssinatura setDataAssinatura(java.lang.String dataAssinatura) {
    this.dataAssinatura = dataAssinatura;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getValue() {
    return value;
  }

  /**
   * @param value value or {@code null} for none
   */
  public TipoAssinatura setValue(java.lang.String value) {
    this.value = value;
    return this;
  }

  @Override
  public TipoAssinatura set(String fieldName, Object value) {
    return (TipoAssinatura) super.set(fieldName, value);
  }

  @Override
  public TipoAssinatura clone() {
    return (TipoAssinatura) super.clone();
  }

}
