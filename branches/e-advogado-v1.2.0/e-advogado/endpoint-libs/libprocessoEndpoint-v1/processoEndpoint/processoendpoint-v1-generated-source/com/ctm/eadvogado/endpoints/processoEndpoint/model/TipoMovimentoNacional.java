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
 * on 2013-08-14 at 02:20:04 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.endpoints.processoEndpoint.model;

/**
 * Model definition for TipoMovimentoNacional.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class TipoMovimentoNacional extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer codigoNacional;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> complemento;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getCodigoNacional() {
    return codigoNacional;
  }

  /**
   * @param codigoNacional codigoNacional or {@code null} for none
   */
  public TipoMovimentoNacional setCodigoNacional(java.lang.Integer codigoNacional) {
    this.codigoNacional = codigoNacional;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getComplemento() {
    return complemento;
  }

  /**
   * @param complemento complemento or {@code null} for none
   */
  public TipoMovimentoNacional setComplemento(java.util.List<java.lang.String> complemento) {
    this.complemento = complemento;
    return this;
  }

  @Override
  public TipoMovimentoNacional set(String fieldName, Object value) {
    return (TipoMovimentoNacional) super.set(fieldName, value);
  }

  @Override
  public TipoMovimentoNacional clone() {
    return (TipoMovimentoNacional) super.clone();
  }

}
