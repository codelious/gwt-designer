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
package com.google.gwt.core.ext.linker;

import com.google.gwt.core.ext.Linker;

import java.io.Serializable;

/**
 * A base type for all artifacts relating to the link process. In order to
 * ensure stable output between runs of the compiler, Artifact types must
 * implement a stable comparison between instances of a relevant base type (the
 * exact comparison order is irrelevant).
 * 
 * @param <C> The type of Artifact interface that the Artifact can be compared
 *          to.
 */
public abstract class Artifact<C extends Artifact<C>> implements
    Comparable<Artifact<?>>, Serializable {
  private final String linkerName;
  private transient Class<? extends Linker> linker;

  /**
   * Constructor.
   * 
   * @param linker the type of Linker that instantiated the Artifact.
   */
  protected Artifact(Class<? extends Linker> linker) {
    assert linker != null;
    this.linkerName = linker.getName();
    this.linker = linker;
  }

  public final int compareTo(Artifact<?> o) {
    if (getComparableArtifactType().equals(o.getComparableArtifactType())) {
      return compareToComparableArtifact(getComparableArtifactType().cast(o));
    } else {
      return getComparableArtifactType().getName().compareTo(
          o.getComparableArtifactType().getName());
    }
  }

  /**
   * Delegates to {@link #compareTo(Artifact)}.
   */
  @Override
  public final boolean equals(Object obj) {
    if (obj instanceof Artifact) {
      return compareTo((Artifact<?>) obj) == 0;
    } else {
      return false;
    }
  }

  /**
   * Returns the Linker that created the Artifact.
   */
  public final Class<? extends Linker> getLinker() {
    // linker is null when deserialized.
    if (linker == null) {
      try {
        Class<?> clazz = Class.forName(linkerName, false,
            Thread.currentThread().getContextClassLoader());
        linker = clazz.asSubclass(Linker.class);
      } catch (ClassNotFoundException e) {
        // The class may not be available.
        linker = Linker.class;
      }
    }
    return linker;
  }

  /**
   * The class which is returned from {@link #getComparableArtifactType()} must
   * declare a final implementation which returns the same hash code for objects
   * for which {@link #compareToComparableArtifact(Artifact)} returns 0.
   */
  @Override
  public abstract int hashCode();

  @Override
  public String toString() {
    return getClass().getName() + " created by " + getLinker().getName();
  }

  /**
   * Performs comparison with an artifact of a compatible base type. Objects
   * which compare to 0 are assumed equal, and must return the same
   * {@link #hashCode()}.
   */
  protected abstract int compareToComparableArtifact(C o);

  /**
   * Returns the base type to use for comparisons between Artifacts. All
   * concrete implementations of this methods must be final.
   */
  protected abstract Class<C> getComparableArtifactType();
}
