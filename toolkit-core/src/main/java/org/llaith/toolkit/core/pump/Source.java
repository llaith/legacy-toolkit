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
package org.llaith.toolkit.core.pump;

import org.llaith.toolkit.common.lang.TypedAutoCloseable;

/**
 * It's part of the design, used for more complex active sources, that a source
 * may have no data when 'polled', but not be 'exhausted'. There are two choices,
 * and have both have pros and cons. We could either allow sources to return
 * nulls when the source is exhausted, or we could require a specific
 * SourceExhaustedException. Initially, I liked the exception option because
 * who likes nulls. But after much thought, given that exceptions have expensive
 * stack tractes, and that nulls are already part of java, I decided on the java
 * approach.
 *
 * So here is contract:
 *      Has Data: return a chunk with the data.
 *      Has Pending Data: return an empty chunk.
 *      Is finished: return a null (ouch). A guard can turn this into a
 *          SourceExhaustedException, but it has to be a runtime one.
 *
 * For simplicity in other impls, a Source must be able to withstand being
 * queried repeatedly once exhausted (it should continue to return null without
 * throwing an exception).
 *
 * We'll try not to do that though.
 */
public interface Source<T> extends TypedAutoCloseable<RuntimeException> {

    Chunk<T> get() throws RuntimeException;

}
