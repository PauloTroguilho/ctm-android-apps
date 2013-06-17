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
 * on 2013-06-17 at 05:07:45 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.processoendpoint.model;

/**
 * Model definition for TipoParametro.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class TipoParametro extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nome;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String valor;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String value;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNome() {
    return nome;
  }

  /**
   * @param nome nome or {@code null} for none
   */
  public TipoParametro setNome(java.lang.String nome) {
    this.nome = nome;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getValor() {
    return valor;
  }

  /**
   * @param valor valor or {@code null} for none
   */
  public TipoParametro setValor(java.lang.String valor) {
    this.valor = valor;
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
  public TipoParametro setValue(java.lang.String value) {
    this.value = value;
    return this;
  }

  @Override
  public TipoParametro set(String fieldName, Object value) {
    return (TipoParametro) super.set(fieldName, value);
  }

  @Override
  public TipoParametro clone() {
    return (TipoParametro) super.clone();
  }

}
