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
 * on 2013-08-09 at 02:51:16 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.endpoints.processoEndpoint.model;

/**
 * Model definition for TipoEndereco.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class TipoEndereco extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String bairro;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String cep;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String cidade;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String complemento;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String estado;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String logradouro;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String numero;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String pais;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getBairro() {
    return bairro;
  }

  /**
   * @param bairro bairro or {@code null} for none
   */
  public TipoEndereco setBairro(java.lang.String bairro) {
    this.bairro = bairro;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCep() {
    return cep;
  }

  /**
   * @param cep cep or {@code null} for none
   */
  public TipoEndereco setCep(java.lang.String cep) {
    this.cep = cep;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCidade() {
    return cidade;
  }

  /**
   * @param cidade cidade or {@code null} for none
   */
  public TipoEndereco setCidade(java.lang.String cidade) {
    this.cidade = cidade;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getComplemento() {
    return complemento;
  }

  /**
   * @param complemento complemento or {@code null} for none
   */
  public TipoEndereco setComplemento(java.lang.String complemento) {
    this.complemento = complemento;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEstado() {
    return estado;
  }

  /**
   * @param estado estado or {@code null} for none
   */
  public TipoEndereco setEstado(java.lang.String estado) {
    this.estado = estado;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getLogradouro() {
    return logradouro;
  }

  /**
   * @param logradouro logradouro or {@code null} for none
   */
  public TipoEndereco setLogradouro(java.lang.String logradouro) {
    this.logradouro = logradouro;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNumero() {
    return numero;
  }

  /**
   * @param numero numero or {@code null} for none
   */
  public TipoEndereco setNumero(java.lang.String numero) {
    this.numero = numero;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPais() {
    return pais;
  }

  /**
   * @param pais pais or {@code null} for none
   */
  public TipoEndereco setPais(java.lang.String pais) {
    this.pais = pais;
    return this;
  }

  @Override
  public TipoEndereco set(String fieldName, Object value) {
    return (TipoEndereco) super.set(fieldName, value);
  }

  @Override
  public TipoEndereco clone() {
    return (TipoEndereco) super.clone();
  }

}
