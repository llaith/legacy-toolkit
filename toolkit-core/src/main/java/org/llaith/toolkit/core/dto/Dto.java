/*******************************************************************************

  * Copyright (c) 2005 - 2013 Nos Doughty
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *     http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.

 ******************************************************************************/
package org.llaith.toolkit.core.dto;

/**
 * An interface that means the object in question supports change tracking when
 * used with the rest of the Dto package.
 * <p/>
 * Note: It's important that implementing interfaces do not use equivalence
 * (the.equals())) as that breaks stuff. Each individual Dto instance is equal
 * only to itself (because of the change tracking) and they communicate to other
 * instances that 'match' themselves via binding and notifications. The
 * DtoCollection add/remove tracking also relies on this. The existing
 * implementations enforce this, but extenders will have to bear that in mind.
 */
public interface Dto<T extends Dto<T>> {

    /**
     * A dirty object is one with pending changes to the current values
     *
     * @return true if the object is dirty, false otherwise.
     */
    boolean isDirty();

    /**
     * A stale object is one with updated original values.
     *
     * @return true if the object is dirty, false otherwise.
     */
    boolean isStale();

    /**
     * A conflicted object is one that is both (isDirty() == true) and
     * (isStale == true), meaning it has been updated from both sides and
     * the current dirty value has been overwritten by the new original
     * value and the conflict flag set.
     *
     * @return true if isStale() && isDirty()
     */
    boolean isConflicted();

    /**
     * Accept any changed current values. They will be copied to the original
     * values, and any flags isDirty(), isStale(), isConflicted() flags reset.
     * If used with db entities, this should therefore be done after the new
     * record has been successfully saved.
     *
     * @return self instance
     */
    T acceptChanges();

    /**
     * Cancel any changed current values. They will be replaced by the objects
     * original values, and any flags isDirty(), isStale(), isConflicted() flags
     * will be reset.
     *
     * @return self instances
     */
    T cancelChanges();

}
