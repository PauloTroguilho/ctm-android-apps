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
 * (build: 2013-08-26 17:13:51 UTC)
 * on 2013-09-02 at 01:48:27 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.endpoints.processoEndpoint.model;

/**
 * Model definition for TipoPessoa.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the processoEndpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class TipoPessoa extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String cidadeNatural;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String dataNascimento;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String dataObito;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<TipoDocumentoIdentificacao> documento;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<TipoEndereco> endereco;

  static {
    // hack to force ProGuard to consider TipoEndereco used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(TipoEndereco.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String estadoNatural;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nacionalidade;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nome;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nomeGenitor;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nomeGenitora;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String numeroDocumentoPrincipal;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> outroNome;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<TipoRelacionamentoPessoal> pessoaRelacionada;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private TipoPessoa pessoaVinculada;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String sexo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String tipoPessoa;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCidadeNatural() {
    return cidadeNatural;
  }

  /**
   * @param cidadeNatural cidadeNatural or {@code null} for none
   */
  public TipoPessoa setCidadeNatural(java.lang.String cidadeNatural) {
    this.cidadeNatural = cidadeNatural;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDataNascimento() {
    return dataNascimento;
  }

  /**
   * @param dataNascimento dataNascimento or {@code null} for none
   */
  public TipoPessoa setDataNascimento(java.lang.String dataNascimento) {
    this.dataNascimento = dataNascimento;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDataObito() {
    return dataObito;
  }

  /**
   * @param dataObito dataObito or {@code null} for none
   */
  public TipoPessoa setDataObito(java.lang.String dataObito) {
    this.dataObito = dataObito;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<TipoDocumentoIdentificacao> getDocumento() {
    return documento;
  }

  /**
   * @param documento documento or {@code null} for none
   */
  public TipoPessoa setDocumento(java.util.List<TipoDocumentoIdentificacao> documento) {
    this.documento = documento;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<TipoEndereco> getEndereco() {
    return endereco;
  }

  /**
   * @param endereco endereco or {@code null} for none
   */
  public TipoPessoa setEndereco(java.util.List<TipoEndereco> endereco) {
    this.endereco = endereco;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEstadoNatural() {
    return estadoNatural;
  }

  /**
   * @param estadoNatural estadoNatural or {@code null} for none
   */
  public TipoPessoa setEstadoNatural(java.lang.String estadoNatural) {
    this.estadoNatural = estadoNatural;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNacionalidade() {
    return nacionalidade;
  }

  /**
   * @param nacionalidade nacionalidade or {@code null} for none
   */
  public TipoPessoa setNacionalidade(java.lang.String nacionalidade) {
    this.nacionalidade = nacionalidade;
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
  public TipoPessoa setNome(java.lang.String nome) {
    this.nome = nome;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNomeGenitor() {
    return nomeGenitor;
  }

  /**
   * @param nomeGenitor nomeGenitor or {@code null} for none
   */
  public TipoPessoa setNomeGenitor(java.lang.String nomeGenitor) {
    this.nomeGenitor = nomeGenitor;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNomeGenitora() {
    return nomeGenitora;
  }

  /**
   * @param nomeGenitora nomeGenitora or {@code null} for none
   */
  public TipoPessoa setNomeGenitora(java.lang.String nomeGenitora) {
    this.nomeGenitora = nomeGenitora;
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
  public TipoPessoa setNumeroDocumentoPrincipal(java.lang.String numeroDocumentoPrincipal) {
    this.numeroDocumentoPrincipal = numeroDocumentoPrincipal;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getOutroNome() {
    return outroNome;
  }

  /**
   * @param outroNome outroNome or {@code null} for none
   */
  public TipoPessoa setOutroNome(java.util.List<java.lang.String> outroNome) {
    this.outroNome = outroNome;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<TipoRelacionamentoPessoal> getPessoaRelacionada() {
    return pessoaRelacionada;
  }

  /**
   * @param pessoaRelacionada pessoaRelacionada or {@code null} for none
   */
  public TipoPessoa setPessoaRelacionada(java.util.List<TipoRelacionamentoPessoal> pessoaRelacionada) {
    this.pessoaRelacionada = pessoaRelacionada;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public TipoPessoa getPessoaVinculada() {
    return pessoaVinculada;
  }

  /**
   * @param pessoaVinculada pessoaVinculada or {@code null} for none
   */
  public TipoPessoa setPessoaVinculada(TipoPessoa pessoaVinculada) {
    this.pessoaVinculada = pessoaVinculada;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSexo() {
    return sexo;
  }

  /**
   * @param sexo sexo or {@code null} for none
   */
  public TipoPessoa setSexo(java.lang.String sexo) {
    this.sexo = sexo;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTipoPessoa() {
    return tipoPessoa;
  }

  /**
   * @param tipoPessoa tipoPessoa or {@code null} for none
   */
  public TipoPessoa setTipoPessoa(java.lang.String tipoPessoa) {
    this.tipoPessoa = tipoPessoa;
    return this;
  }

  @Override
  public TipoPessoa set(String fieldName, Object value) {
    return (TipoPessoa) super.set(fieldName, value);
  }

  @Override
  public TipoPessoa clone() {
    return (TipoPessoa) super.clone();
  }

}
