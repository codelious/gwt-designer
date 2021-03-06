/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.google.gwt.core.ext;

import java.util.SortedSet;

/**
 * Default immutable implementation of SelectionProperty that receives its
 * values in its constructor.
 */
public class DefaultSelectionProperty implements SelectionProperty {

  private final String currentValue;
  private final String fallbackValue;
  private final String name;
  private final SortedSet<String> possibleValues;

  /**
   * Construct a selection property.
   *  
   * @param currentValue current value of this property, must not be null
   * @param fallbackValue the fallback value to use, must not be null
   * @param name the name of this property, must not be null
   * @param possibleValues the set of possible values, must not be null and
   *     will be returned to callers, so a copy should be passed into this
   *     ctor if the caller will use this set later
   */
  public DefaultSelectionProperty(String currentValue, String fallbackValue,
      String name, SortedSet<String> possibleValues) {
    assert currentValue != null;
    assert fallbackValue != null;
    assert name != null;
    assert possibleValues != null;
    this.currentValue = currentValue;
    this.fallbackValue = fallbackValue;
    this.name = name;
    this.possibleValues = possibleValues;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    DefaultSelectionProperty other = (DefaultSelectionProperty) obj;
    return currentValue.equals(other.currentValue)
        && fallbackValue.equals(other.fallbackValue)
        && name.equals(other.name)
        && possibleValues.equals(other.possibleValues);
  }

  public String getCurrentValue() {
    return currentValue;
  }

  public String getFallbackValue() {
    return fallbackValue;
  }

  public String getName() {
    return name;
  }

  public SortedSet<String> getPossibleValues() {
    return possibleValues;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + currentValue.hashCode();
    result = prime * result + fallbackValue.hashCode();
    result = prime * result + name.hashCode();
    result = prime * result + possibleValues.hashCode();
    return result;
  }
  
  @Override
  public String toString() {
    return "SelectionProp " + name + ": " + currentValue + " of "
        + possibleValues.toString() + ", fallback =" + fallbackValue;
  }
}
