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
 * on 2013-07-27 at 04:31:50 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.endpoints.processoEndpoint.model;

/**
 * Model definition for TipoDocumentoIdentificacao.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class TipoDocumentoIdentificacao extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String codigoDocumento;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String emissorDocumento;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nome;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String tipoDocumento;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCodigoDocumento() {
    return codigoDocumento;
  }

  /**
   * @param codigoDocumento codigoDocumento or {@code null} for none
   */
  public TipoDocumentoIdentificacao setCodigoDocumento(java.lang.String codigoDocumento) {
    this.codigoDocumento = codigoDocumento;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEmissorDocumento() {
    return emissorDocumento;
  }

  /**
   * @param emissorDocumento emissorDocumento or {@code null} for none
   */
  public TipoDocumentoIdentificacao setEmissorDocumento(java.lang.String emissorDocumento) {
    this.emissorDocumento = emissorDocumento;
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
  public TipoDocumentoIdentificacao setNome(java.lang.String nome) {
    this.nome = nome;
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
  public TipoDocumentoIdentificacao setTipoDocumento(java.lang.String tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
    return this;
  }

  @Override
  public TipoDocumentoIdentificacao set(String fieldName, Object value) {
    return (TipoDocumentoIdentificacao) super.set(fieldName, value);
  }

  @Override
  public TipoDocumentoIdentificacao clone() {
    return (TipoDocumentoIdentificacao) super.clone();
  }

}
