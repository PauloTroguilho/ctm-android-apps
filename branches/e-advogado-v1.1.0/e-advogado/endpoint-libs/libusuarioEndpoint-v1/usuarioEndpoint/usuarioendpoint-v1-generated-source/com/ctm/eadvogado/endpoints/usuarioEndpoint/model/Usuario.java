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
 * on 2013-07-13 at 18:21:59 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.endpoints.usuarioEndpoint.model;

/**
 * Model definition for Usuario.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Usuario extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key advogado;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String email;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key key;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Key> papeis;

  static {
    // hack to force ProGuard to consider Key used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(Key.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Key> processos;

  static {
    // hack to force ProGuard to consider Key used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(Key.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long saldo;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String senha;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String tipoConta;

  /**
   * @return value or {@code null} for none
   */
  public Key getAdvogado() {
    return advogado;
  }

  /**
   * @param advogado advogado or {@code null} for none
   */
  public Usuario setAdvogado(Key advogado) {
    this.advogado = advogado;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEmail() {
    return email;
  }

  /**
   * @param email email or {@code null} for none
   */
  public Usuario setEmail(java.lang.String email) {
    this.email = email;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Key getKey() {
    return key;
  }

  /**
   * @param key key or {@code null} for none
   */
  public Usuario setKey(Key key) {
    this.key = key;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Key> getPapeis() {
    return papeis;
  }

  /**
   * @param papeis papeis or {@code null} for none
   */
  public Usuario setPapeis(java.util.List<Key> papeis) {
    this.papeis = papeis;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Key> getProcessos() {
    return processos;
  }

  /**
   * @param processos processos or {@code null} for none
   */
  public Usuario setProcessos(java.util.List<Key> processos) {
    this.processos = processos;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getSaldo() {
    return saldo;
  }

  /**
   * @param saldo saldo or {@code null} for none
   */
  public Usuario setSaldo(java.lang.Long saldo) {
    this.saldo = saldo;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSenha() {
    return senha;
  }

  /**
   * @param senha senha or {@code null} for none
   */
  public Usuario setSenha(java.lang.String senha) {
    this.senha = senha;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTipoConta() {
    return tipoConta;
  }

  /**
   * @param tipoConta tipoConta or {@code null} for none
   */
  public Usuario setTipoConta(java.lang.String tipoConta) {
    this.tipoConta = tipoConta;
    return this;
  }

  @Override
  public Usuario set(String fieldName, Object value) {
    return (Usuario) super.set(fieldName, value);
  }

  @Override
  public Usuario clone() {
    return (Usuario) super.clone();
  }

}
