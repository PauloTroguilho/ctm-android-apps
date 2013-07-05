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
 * on 2013-07-04 at 08:19:18 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.processoendpoint.model;

/**
 * Model definition for Key.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Key extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String appId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean complete;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String kind;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String namespace;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key parent;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAppId() {
    return appId;
  }

  /**
   * @param appId appId or {@code null} for none
   */
  public Key setAppId(java.lang.String appId) {
    this.appId = appId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getComplete() {
    return complete;
  }

  /**
   * @param complete complete or {@code null} for none
   */
  public Key setComplete(java.lang.Boolean complete) {
    this.complete = complete;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Key setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getKind() {
    return kind;
  }

  /**
   * @param kind kind or {@code null} for none
   */
  public Key setKind(java.lang.String kind) {
    this.kind = kind;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param name name or {@code null} for none
   */
  public Key setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getNamespace() {
    return namespace;
  }

  /**
   * @param namespace namespace or {@code null} for none
   */
  public Key setNamespace(java.lang.String namespace) {
    this.namespace = namespace;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Key getParent() {
    return parent;
  }

  /**
   * @param parent parent or {@code null} for none
   */
  public Key setParent(Key parent) {
    this.parent = parent;
    return this;
  }

  @Override
  public Key set(String fieldName, Object value) {
    return (Key) super.set(fieldName, value);
  }

  @Override
  public Key clone() {
    return (Key) super.clone();
  }

}
