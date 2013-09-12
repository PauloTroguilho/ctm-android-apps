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
 * on 2013-07-15 at 15:04:29 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.endpoints.processoEndpoint.model;

/**
 * Model definition for TipoDocumento.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class TipoDocumento extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Object any;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<TipoAssinatura> assinatura;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String conteudo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String dataHora;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<TipoDocumento> documentoVinculado;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String hash;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String idDocumento;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String idDocumentoVinculado;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String mimetype;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer movimento;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer nivelSigilo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<TipoParametro> outroParametro;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String tipoDocumento;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Object getAny() {
    return any;
  }

  /**
   * @param any any or {@code null} for none
   */
  public TipoDocumento setAny(java.lang.Object any) {
    this.any = any;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<TipoAssinatura> getAssinatura() {
    return assinatura;
  }

  /**
   * @param assinatura assinatura or {@code null} for none
   */
  public TipoDocumento setAssinatura(java.util.List<TipoAssinatura> assinatura) {
    this.assinatura = assinatura;
    return this;
  }

  /**
   * @see #decodeConteudo()
   * @return value or {@code null} for none
   */
  public java.lang.String getConteudo() {
    return conteudo;
  }

  /**

   * @see #getConteudo()
   * @return Base64 decoded value or {@code null} for none
   *
   * @since 1.14
   */
  public byte[] decodeConteudo() {
    return com.google.api.client.util.Base64.decodeBase64(conteudo);
  }

  /**
   * @see #encodeConteudo()
   * @param conteudo conteudo or {@code null} for none
   */
  public TipoDocumento setConteudo(java.lang.String conteudo) {
    this.conteudo = conteudo;
    return this;
  }

  /**

   * @see #setConteudo()
   *
   * <p>
   * The value is encoded Base64 or {@code null} for none.
   * </p>
   *
   * @since 1.14
   */
  public TipoDocumento encodeConteudo(byte[] conteudo) {
    this.conteudo = com.google.api.client.util.Base64.encodeBase64URLSafeString(conteudo);
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
  public TipoDocumento setDataHora(java.lang.String dataHora) {
    this.dataHora = dataHora;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<TipoDocumento> getDocumentoVinculado() {
    return documentoVinculado;
  }

  /**
   * @param documentoVinculado documentoVinculado or {@code null} for none
   */
  public TipoDocumento setDocumentoVinculado(java.util.List<TipoDocumento> documentoVinculado) {
    this.documentoVinculado = documentoVinculado;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getHash() {
    return hash;
  }

  /**
   * @param hash hash or {@code null} for none
   */
  public TipoDocumento setHash(java.lang.String hash) {
    this.hash = hash;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getIdDocumento() {
    return idDocumento;
  }

  /**
   * @param idDocumento idDocumento or {@code null} for none
   */
  public TipoDocumento setIdDocumento(java.lang.String idDocumento) {
    this.idDocumento = idDocumento;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getIdDocumentoVinculado() {
    return idDocumentoVinculado;
  }

  /**
   * @param idDocumentoVinculado idDocumentoVinculado or {@code null} for none
   */
  public TipoDocumento setIdDocumentoVinculado(java.lang.String idDocumentoVinculado) {
    this.idDocumentoVinculado = idDocumentoVinculado;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getMimetype() {
    return mimetype;
  }

  /**
   * @param mimetype mimetype or {@code null} for none
   */
  public TipoDocumento setMimetype(java.lang.String mimetype) {
    this.mimetype = mimetype;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getMovimento() {
    return movimento;
  }

  /**
   * @param movimento movimento or {@code null} for none
   */
  public TipoDocumento setMovimento(java.lang.Integer movimento) {
    this.movimento = movimento;
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
  public TipoDocumento setNivelSigilo(java.lang.Integer nivelSigilo) {
    this.nivelSigilo = nivelSigilo;
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
  public TipoDocumento setOutroParametro(java.util.List<TipoParametro> outroParametro) {
    this.outroParametro = outroParametro;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTipoDocumento() {
    return tipoDocumento;
  }

  /**
   * @param tipoDocumento tipoDocumento or {@code null} for none
   */
  public TipoDocumento setTipoDocumento(java.lang.String tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
    return this;
  }

  @Override
  public TipoDocumento set(String fieldName, Object value) {
    return (TipoDocumento) super.set(fieldName, value);
  }

  @Override
  public TipoDocumento clone() {
    return (TipoDocumento) super.clone();
  }

}