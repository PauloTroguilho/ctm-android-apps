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
 * on 2013-07-01 at 02:36:04 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.advogadoendpoint.model;

/**
 * Model definition for Advogado.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Advogado extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String email;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String nome;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String numeroOab;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Key> processos;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String senha;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEmail() {
    return email;
  }

  /**
   * @param email email or {@code null} for none
   */
  public Advogado setEmail(java.lang.String email) {
    this.email = email;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Key getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Advogado setId(Key id) {
    this.id = id;
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
  public Advogado setNome(java.lang.String nome) {
    this.nome = nome;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNumeroOab() {
    return numeroOab;
  }

  /**
   * @param numeroOab numeroOab or {@code null} for none
   */
  public Advogado setNumeroOab(java.lang.String numeroOab) {
    this.numeroOab = numeroOab;
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
  public Advogado setProcessos(java.util.List<Key> processos) {
    this.processos = processos;
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
  public Advogado setSenha(java.lang.String senha) {
    this.senha = senha;
    return this;
  }

  @Override
  public Advogado set(String fieldName, Object value) {
    return (Advogado) super.set(fieldName, value);
  }

  @Override
  public Advogado clone() {
    return (Advogado) super.clone();
  }

}
