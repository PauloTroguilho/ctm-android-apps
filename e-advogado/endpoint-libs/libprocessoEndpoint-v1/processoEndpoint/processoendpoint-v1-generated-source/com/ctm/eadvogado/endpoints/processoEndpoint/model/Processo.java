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
 * on 2013-08-14 at 02:20:04 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.endpoints.processoEndpoint.model;

/**
 * Model definition for Processo.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Processo extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key key;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String npu;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private TipoProcessoJudicial processoJudicial;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String tipoJuizo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key tribunal;

  /**
   * @return value or {@code null} for none
   */
  public Key getKey() {
    return key;
  }

  /**
   * @param key key or {@code null} for none
   */
  public Processo setKey(Key key) {
    this.key = key;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNpu() {
    return npu;
  }

  /**
   * @param npu npu or {@code null} for none
   */
  public Processo setNpu(java.lang.String npu) {
    this.npu = npu;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public TipoProcessoJudicial getProcessoJudicial() {
    return processoJudicial;
  }

  /**
   * @param processoJudicial processoJudicial or {@code null} for none
   */
  public Processo setProcessoJudicial(TipoProcessoJudicial processoJudicial) {
    this.processoJudicial = processoJudicial;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTipoJuizo() {
    return tipoJuizo;
  }

  /**
   * @param tipoJuizo tipoJuizo or {@code null} for none
   */
  public Processo setTipoJuizo(java.lang.String tipoJuizo) {
    this.tipoJuizo = tipoJuizo;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Key getTribunal() {
    return tribunal;
  }

  /**
   * @param tribunal tribunal or {@code null} for none
   */
  public Processo setTribunal(Key tribunal) {
    this.tribunal = tribunal;
    return this;
  }

  @Override
  public Processo set(String fieldName, Object value) {
    return (Processo) super.set(fieldName, value);
  }

  @Override
  public Processo clone() {
    return (Processo) super.clone();
  }

}
