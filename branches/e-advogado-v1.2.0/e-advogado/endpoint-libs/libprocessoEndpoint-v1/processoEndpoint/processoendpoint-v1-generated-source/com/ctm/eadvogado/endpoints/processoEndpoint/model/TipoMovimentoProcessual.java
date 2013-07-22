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
 * on 2013-07-22 at 03:49:51 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.endpoints.processoEndpoint.model;

/**
 * Model definition for TipoMovimentoProcessual.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class TipoMovimentoProcessual extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> complemento;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String dataHora;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> idDocumentoVinculado;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String identificadorMovimento;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String movimentoLocal;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private TipoMovimentoNacional movimentoNacional;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer nivelSigilo;

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getComplemento() {
    return complemento;
  }

  /**
   * @param complemento complemento or {@code null} for none
   */
  public TipoMovimentoProcessual setComplemento(java.util.List<java.lang.String> complemento) {
    this.complemento = complemento;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDataHora() {
    return dataHora;
  }

  /**
   * @param dataHora dataHora or {@code null} for none
   */
  public TipoMovimentoProcessual setDataHora(java.lang.String dataHora) {
    this.dataHora = dataHora;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getIdDocumentoVinculado() {
    return idDocumentoVinculado;
  }

  /**
   * @param idDocumentoVinculado idDocumentoVinculado or {@code null} for none
   */
  public TipoMovimentoProcessual setIdDocumentoVinculado(java.util.List<java.lang.String> idDocumentoVinculado) {
    this.idDocumentoVinculado = idDocumentoVinculado;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getIdentificadorMovimento() {
    return identificadorMovimento;
  }

  /**
   * @param identificadorMovimento identificadorMovimento or {@code null} for none
   */
  public TipoMovimentoProcessual setIdentificadorMovimento(java.lang.String identificadorMovimento) {
    this.identificadorMovimento = identificadorMovimento;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getMovimentoLocal() {
    return movimentoLocal;
  }

  /**
   * @param movimentoLocal movimentoLocal or {@code null} for none
   */
  public TipoMovimentoProcessual setMovimentoLocal(java.lang.String movimentoLocal) {
    this.movimentoLocal = movimentoLocal;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public TipoMovimentoNacional getMovimentoNacional() {
    return movimentoNacional;
  }

  /**
   * @param movimentoNacional movimentoNacional or {@code null} for none
   */
  public TipoMovimentoProcessual setMovimentoNacional(TipoMovimentoNacional movimentoNacional) {
    this.movimentoNacional = movimentoNacional;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getNivelSigilo() {
    return nivelSigilo;
  }

  /**
   * @param nivelSigilo nivelSigilo or {@code null} for none
   */
  public TipoMovimentoProcessual setNivelSigilo(java.lang.Integer nivelSigilo) {
    this.nivelSigilo = nivelSigilo;
    return this;
  }

  @Override
  public TipoMovimentoProcessual set(String fieldName, Object value) {
    return (TipoMovimentoProcessual) super.set(fieldName, value);
  }

  @Override
  public TipoMovimentoProcessual clone() {
    return (TipoMovimentoProcessual) super.clone();
  }

}
