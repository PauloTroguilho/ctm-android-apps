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
 * (build: 2013-08-21 15:27:30 UTC)
 * on 2013-08-28 at 01:59:49 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.endpoints.processoEndpoint.model;

/**
 * Model definition for TipoRepresentanteProcessual.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class TipoRepresentanteProcessual extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<TipoEndereco> endereco;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String inscricao;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean intimacao;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nome;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String numeroDocumentoPrincipal;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String tipoRepresentante;

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<TipoEndereco> getEndereco() {
    return endereco;
  }

  /**
   * @param endereco endereco or {@code null} for none
   */
  public TipoRepresentanteProcessual setEndereco(java.util.List<TipoEndereco> endereco) {
    this.endereco = endereco;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getInscricao() {
    return inscricao;
  }

  /**
   * @param inscricao inscricao or {@code null} for none
   */
  public TipoRepresentanteProcessual setInscricao(java.lang.String inscricao) {
    this.inscricao = inscricao;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getIntimacao() {
    return intimacao;
  }

  /**
   * @param intimacao intimacao or {@code null} for none
   */
  public TipoRepresentanteProcessual setIntimacao(java.lang.Boolean intimacao) {
    this.intimacao = intimacao;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNome() {
    return nome;
  }

  /**
   * @param nome nome or {@code null} for none
   */
  public TipoRepresentanteProcessual setNome(java.lang.String nome) {
    this.nome = nome;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNumeroDocumentoPrincipal() {
    return numeroDocumentoPrincipal;
  }

  /**
   * @param numeroDocumentoPrincipal numeroDocumentoPrincipal or {@code null} for none
   */
  public TipoRepresentanteProcessual setNumeroDocumentoPrincipal(java.lang.String numeroDocumentoPrincipal) {
    this.numeroDocumentoPrincipal = numeroDocumentoPrincipal;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTipoRepresentante() {
    return tipoRepresentante;
  }

  /**
   * @param tipoRepresentante tipoRepresentante or {@code null} for none
   */
  public TipoRepresentanteProcessual setTipoRepresentante(java.lang.String tipoRepresentante) {
    this.tipoRepresentante = tipoRepresentante;
    return this;
  }

  @Override
  public TipoRepresentanteProcessual set(String fieldName, Object value) {
    return (TipoRepresentanteProcessual) super.set(fieldName, value);
  }

  @Override
  public TipoRepresentanteProcessual clone() {
    return (TipoRepresentanteProcessual) super.clone();
  }

}
