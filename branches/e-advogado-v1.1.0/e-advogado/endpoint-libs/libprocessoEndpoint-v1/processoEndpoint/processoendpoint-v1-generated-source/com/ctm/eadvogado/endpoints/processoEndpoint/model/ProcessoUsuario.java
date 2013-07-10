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
 * on 2013-07-10 at 21:10:54 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.endpoints.processoEndpoint.model;

/**
 * Model definition for ProcessoUsuario.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class ProcessoUsuario extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long idTribunal;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String npu;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String tipoJuizo;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getIdTribunal() {
    return idTribunal;
  }

  /**
   * @param idTribunal idTribunal or {@code null} for none
   */
  public ProcessoUsuario setIdTribunal(java.lang.Long idTribunal) {
    this.idTribunal = idTribunal;
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
  public ProcessoUsuario setNpu(java.lang.String npu) {
    this.npu = npu;
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
  public ProcessoUsuario setTipoJuizo(java.lang.String tipoJuizo) {
    this.tipoJuizo = tipoJuizo;
    return this;
  }

  @Override
  public ProcessoUsuario set(String fieldName, Object value) {
    return (ProcessoUsuario) super.set(fieldName, value);
  }

  @Override
  public ProcessoUsuario clone() {
    return (ProcessoUsuario) super.clone();
  }

}
