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
 * on 2013-07-01 at 02:34:53 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.tribunalendpoint.model;

/**
 * Model definition for Tribunal.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Tribunal extends com.google.api.client.json.GenericJson {

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
  private java.lang.String pje1gEndpoint;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String pje2gEndpoint;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Key> processos;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String sigla;

  /**
   * @return value or {@code null} for none
   */
  public Key getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Tribunal setId(Key id) {
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
  public Tribunal setNome(java.lang.String nome) {
    this.nome = nome;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPje1gEndpoint() {
    return pje1gEndpoint;
  }

  /**
   * @param pje1gEndpoint pje1gEndpoint or {@code null} for none
   */
  public Tribunal setPje1gEndpoint(java.lang.String pje1gEndpoint) {
    this.pje1gEndpoint = pje1gEndpoint;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPje2gEndpoint() {
    return pje2gEndpoint;
  }

  /**
   * @param pje2gEndpoint pje2gEndpoint or {@code null} for none
   */
  public Tribunal setPje2gEndpoint(java.lang.String pje2gEndpoint) {
    this.pje2gEndpoint = pje2gEndpoint;
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
  public Tribunal setProcessos(java.util.List<Key> processos) {
    this.processos = processos;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSigla() {
    return sigla;
  }

  /**
   * @param sigla sigla or {@code null} for none
   */
  public Tribunal setSigla(java.lang.String sigla) {
    this.sigla = sigla;
    return this;
  }

  @Override
  public Tribunal set(String fieldName, Object value) {
    return (Tribunal) super.set(fieldName, value);
  }

  @Override
  public Tribunal clone() {
    return (Tribunal) super.clone();
  }

}
