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
 * (build: 2013-08-21 15:27:30 UTC)
 * on 2013-08-28 at 02:04:23 UTC 
 * Modify at your own risk.
 */

package com.ctm.eadvogado.endpoints.compraEndpoint.model;

/**
 * Model definition for WrappedBoolean.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the . For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class WrappedBoolean extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean value;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getValue() {
    return value;
  }

  /**
   * @param value value or {@code null} for none
   */
  public WrappedBoolean setValue(java.lang.Boolean value) {
    this.value = value;
    return this;
  }

  @Override
  public WrappedBoolean set(String fieldName, Object value) {
    return (WrappedBoolean) super.set(fieldName, value);
  }

  @Override
  public WrappedBoolean clone() {
    return (WrappedBoolean) super.clone();
  }

}
