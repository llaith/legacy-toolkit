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
package org.llaith.toolkit.core.dto.session;

import org.llaith.toolkit.core.dto.DtoObject;

/**
 * An event which represents a request to synchronise another DtoObject
 * from the source's state.
 *
 * @param <T> the type of the event source
 */
public class DtoRefreshEvent<T extends DtoObject<T>> {

    private final T source;

    public DtoRefreshEvent(final T source) {
        this.source = source;
    }

    /**
     * Retrieves the source object that is to be used as the master
     * for the synchronization.
     *
     * @return the source object
     */
    public T source() {
        return source;
    }

}
