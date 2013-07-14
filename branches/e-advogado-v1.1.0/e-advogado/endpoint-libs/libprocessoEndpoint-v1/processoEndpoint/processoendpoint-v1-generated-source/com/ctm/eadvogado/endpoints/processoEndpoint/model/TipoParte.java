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
 * on 2013-07-13 at 18:20:54 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.endpoints.processoEndpoint.model;

/**
 * Model definition for TipoParte.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class TipoParte extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<TipoRepresentanteProcessual> advogado;

  static {
    // hack to force ProGuard to consider TipoRepresentanteProcessual used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(TipoRepresentanteProcessual.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean assistenciaJudiciaria;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String interessePublico;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer intimacaoPendente;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private TipoPessoa pessoa;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<TipoParte> pessoaProcessualRelacionada;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String relacionamentoProcessual;

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<TipoRepresentanteProcessual> getAdvogado() {
    return advogado;
  }

  /**
   * @param advogado advogado or {@code null} for none
   */
  public TipoParte setAdvogado(java.util.List<TipoRepresentanteProcessual> advogado) {
    this.advogado = advogado;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getAssistenciaJudiciaria() {
    return assistenciaJudiciaria;
  }

  /**
   * @param assistenciaJudiciaria assistenciaJudiciaria or {@code null} for none
   */
  public TipoParte setAssistenciaJudiciaria(java.lang.Boolean assistenciaJudiciaria) {
    this.assistenciaJudiciaria = assistenciaJudiciaria;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getInteressePublico() {
    return interessePublico;
  }

  /**
   * @param interessePublico interessePublico or {@code null} for none
   */
  public TipoParte setInteressePublico(java.lang.String interessePublico) {
    this.interessePublico = interessePublico;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getIntimacaoPendente() {
    return intimacaoPendente;
  }

  /**
   * @param intimacaoPendente intimacaoPendente or {@code null} for none
   */
  public TipoParte setIntimacaoPendente(java.lang.Integer intimacaoPendente) {
    this.intimacaoPendente = intimacaoPendente;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public TipoPessoa getPessoa() {
    return pessoa;
  }

  /**
   * @param pessoa pessoa or {@code null} for none
   */
  public TipoParte setPessoa(TipoPessoa pessoa) {
    this.pessoa = pessoa;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<TipoParte> getPessoaProcessualRelacionada() {
    return pessoaProcessualRelacionada;
  }

  /**
   * @param pessoaProcessualRelacionada pessoaProcessualRelacionada or {@code null} for none
   */
  public TipoParte setPessoaProcessualRelacionada(java.util.List<TipoParte> pessoaProcessualRelacionada) {
    this.pessoaProcessualRelacionada = pessoaProcessualRelacionada;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getRelacionamentoProcessual() {
    return relacionamentoProcessual;
  }

  /**
   * @param relacionamentoProcessual relacionamentoProcessual or {@code null} for none
   */
  public TipoParte setRelacionamentoProcessual(java.lang.String relacionamentoProcessual) {
    this.relacionamentoProcessual = relacionamentoProcessual;
    return this;
  }

  @Override
  public TipoParte set(String fieldName, Object value) {
    return (TipoParte) super.set(fieldName, value);
  }

  @Override
  public TipoParte clone() {
    return (TipoParte) super.clone();
  }

}
