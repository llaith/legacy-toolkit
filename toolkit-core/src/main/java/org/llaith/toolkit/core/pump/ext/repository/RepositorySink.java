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
package org.llaith.toolkit.core.pump.ext.repository;

import org.llaith.toolkit.core.pump.Chunk;
import org.llaith.toolkit.core.pump.Sink;
import org.llaith.toolkit.core.repository.Repository;

/**
 *
 */
public class RepositorySink<T> implements Sink<T> {

    private final Repository repository;

    public RepositorySink(final Repository repository) {
        this.repository = repository;
    }

    @Override
    public void put(final Chunk<T> chunk) throws RuntimeException {
        if (chunk != null) {
            this.repository.addAll(chunk);
        }
    }

    @Override
    public void close() throws RuntimeException {
        // blank
    }

}
