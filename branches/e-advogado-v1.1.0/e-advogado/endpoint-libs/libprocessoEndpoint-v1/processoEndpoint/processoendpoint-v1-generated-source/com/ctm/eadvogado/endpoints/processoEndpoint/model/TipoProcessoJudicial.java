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
 * Model definition for TipoProcessoJudicial.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class TipoProcessoJudicial extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private TipoCabecalhoProcesso dadosBasicos;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<TipoDocumento> documento;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<TipoMovimentoProcessual> movimento;

  /**
   * @return value or {@code null} for none
   */
  public TipoCabecalhoProcesso getDadosBasicos() {
    return dadosBasicos;
  }

  /**
   * @param dadosBasicos dadosBasicos or {@code null} for none
   */
  public TipoProcessoJudicial setDadosBasicos(TipoCabecalhoProcesso dadosBasicos) {
    this.dadosBasicos = dadosBasicos;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<TipoDocumento> getDocumento() {
    return documento;
  }

  /**
   * @param documento documento or {@code null} for none
   */
  public TipoProcessoJudicial setDocumento(java.util.List<TipoDocumento> documento) {
    this.documento = documento;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<TipoMovimentoProcessual> getMovimento() {
    return movimento;
  }

  /**
   * @param movimento movimento or {@code null} for none
   */
  public TipoProcessoJudicial setMovimento(java.util.List<TipoMovimentoProcessual> movimento) {
    this.movimento = movimento;
    return this;
  }

  @Override
  public TipoProcessoJudicial set(String fieldName, Object value) {
    return (TipoProcessoJudicial) super.set(fieldName, value);
  }

  @Override
  public TipoProcessoJudicial clone() {
    return (TipoProcessoJudicial) super.clone();
  }

}
