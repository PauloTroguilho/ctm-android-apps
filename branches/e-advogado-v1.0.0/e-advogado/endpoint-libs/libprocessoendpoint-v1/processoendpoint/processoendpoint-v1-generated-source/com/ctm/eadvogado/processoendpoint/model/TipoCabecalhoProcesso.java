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
 * on 2013-07-03 at 01:43:44 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.processoendpoint.model;

/**
 * Model definition for TipoCabecalhoProcesso.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class TipoCabecalhoProcesso extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<TipoAssuntoProcessual> assunto;

  static {
    // hack to force ProGuard to consider TipoAssuntoProcessual used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(TipoAssuntoProcessual.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer classeProcessual;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String codigoLocalidade;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String codigoOrgaoJulgador;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer competencia;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean intervencaoMP;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> magistradoAtuante;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer nivelSigilo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String numero;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<TipoParametro> outroParametro;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<TipoPoloProcessual> polo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> prioridade;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<TipoVinculacaoProcessual> processoVinculado;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer tamanhoProcesso;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double valorCausa;

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<TipoAssuntoProcessual> getAssunto() {
    return assunto;
  }

  /**
   * @param assunto assunto or {@code null} for none
   */
  public TipoCabecalhoProcesso setAssunto(java.util.List<TipoAssuntoProcessual> assunto) {
    this.assunto = assunto;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getClasseProcessual() {
    return classeProcessual;
  }

  /**
   * @param classeProcessual classeProcessual or {@code null} for none
   */
  public TipoCabecalhoProcesso setClasseProcessual(java.lang.Integer classeProcessual) {
    this.classeProcessual = classeProcessual;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCodigoLocalidade() {
    return codigoLocalidade;
  }

  /**
   * @param codigoLocalidade codigoLocalidade or {@code null} for none
   */
  public TipoCabecalhoProcesso setCodigoLocalidade(java.lang.String codigoLocalidade) {
    this.codigoLocalidade = codigoLocalidade;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCodigoOrgaoJulgador() {
    return codigoOrgaoJulgador;
  }

  /**
   * @param codigoOrgaoJulgador codigoOrgaoJulgador or {@code null} for none
   */
  public TipoCabecalhoProcesso setCodigoOrgaoJulgador(java.lang.String codigoOrgaoJulgador) {
    this.codigoOrgaoJulgador = codigoOrgaoJulgador;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getCompetencia() {
    return competencia;
  }

  /**
   * @param competencia competencia or {@code null} for none
   */
  public TipoCabecalhoProcesso setCompetencia(java.lang.Integer competencia) {
    this.competencia = competencia;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getIntervencaoMP() {
    return intervencaoMP;
  }

  /**
   * @param intervencaoMP intervencaoMP or {@code null} for none
   */
  public TipoCabecalhoProcesso setIntervencaoMP(java.lang.Boolean intervencaoMP) {
    this.intervencaoMP = intervencaoMP;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getMagistradoAtuante() {
    return magistradoAtuante;
  }

  /**
   * @param magistradoAtuante magistradoAtuante or {@code null} for none
   */
  public TipoCabecalhoProcesso setMagistradoAtuante(java.util.List<java.lang.String> magistradoAtuante) {
    this.magistradoAtuante = magistradoAtuante;
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
  public TipoCabecalhoProcesso setNivelSigilo(java.lang.Integer nivelSigilo) {
    this.nivelSigilo = nivelSigilo;
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
  public TipoCabecalhoProcesso setNumero(java.lang.String numero) {
    this.numero = numero;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<TipoParametro> getOutroParametro() {
    return outroParametro;
  }

  /**
   * @param outroParametro outroParametro or {@code null} for none
   */
  public TipoCabecalhoProcesso setOutroParametro(java.util.List<TipoParametro> outroParametro) {
    this.outroParametro = outroParametro;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<TipoPoloProcessual> getPolo() {
    return polo;
  }

  /**
   * @param polo polo or {@code null} for none
   */
  public TipoCabecalhoProcesso setPolo(java.util.List<TipoPoloProcessual> polo) {
    this.polo = polo;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getPrioridade() {
    return prioridade;
  }

  /**
   * @param prioridade prioridade or {@code null} for none
   */
  public TipoCabecalhoProcesso setPrioridade(java.util.List<java.lang.String> prioridade) {
    this.prioridade = prioridade;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<TipoVinculacaoProcessual> getProcessoVinculado() {
    return processoVinculado;
  }

  /**
   * @param processoVinculado processoVinculado or {@code null} for none
   */
  public TipoCabecalhoProcesso setProcessoVinculado(java.util.List<TipoVinculacaoProcessual> processoVinculado) {
    this.processoVinculado = processoVinculado;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getTamanhoProcesso() {
    return tamanhoProcesso;
  }

  /**
   * @param tamanhoProcesso tamanhoProcesso or {@code null} for none
   */
  public TipoCabecalhoProcesso setTamanhoProcesso(java.lang.Integer tamanhoProcesso) {
    this.tamanhoProcesso = tamanhoProcesso;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getValorCausa() {
    return valorCausa;
  }

  /**
   * @param valorCausa valorCausa or {@code null} for none
   */
  public TipoCabecalhoProcesso setValorCausa(java.lang.Double valorCausa) {
    this.valorCausa = valorCausa;
    return this;
  }

  @Override
  public TipoCabecalhoProcesso set(String fieldName, Object value) {
    return (TipoCabecalhoProcesso) super.set(fieldName, value);
  }

  @Override
  public TipoCabecalhoProcesso clone() {
    return (TipoCabecalhoProcesso) super.clone();
  }

}
