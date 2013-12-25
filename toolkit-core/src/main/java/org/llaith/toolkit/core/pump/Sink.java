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
 * Will always 'flush' at end of chunk. A stream can be maintained as a chunk of
 * one. The rule is we will try not to pass empty chunks down, so don't expect
 * them. Also the chunk that gets passed down may not be the one that was
 * originally returned by a source, so don't rely on that (no tricks with
 * subclasses of chunks!).
 *
 * For simplicity in other impls, a Snik must be able to withstand being
 * passed empty chunks, but these should be ignored, not used for extra flushes
 * or other significant parts of the lifecycle of a Sink.
 *
 * As with Sources, we'll try not to do that though (wastes cpu).
 */
public interface Sink<T> extends TypedAutoCloseable<RuntimeException> {

    void put(Chunk<T> chunk) throws RuntimeException;

}
