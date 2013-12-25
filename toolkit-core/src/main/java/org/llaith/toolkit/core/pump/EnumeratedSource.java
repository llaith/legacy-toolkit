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

/**
 * As discovered the hard way, when an exception occurs on a read, the value
 * returned will be null (obviously) which will cause the stream to end if
 * iterated until not null. I wanted to keep both options, and explored a few
 * ways of doing it using exceptions, but it was ugly because it passed dtoField
 * parameters for the exception up to the user, and so I ultimately preferred
 * this approach. This approach is modelled on enumerations and is simple to
 * reason about.
 *
 * Note that an EnumeratedSource is fundamentally different from a Sink because
 * an error on the source appears as there is no more work to do. An Enumerated-
 * Source is required for a EnumeratedPump for that reason, but any Sink impl will
 * be made 'durable' also if used with a EnumeratedPump.
 */
public interface EnumeratedSource<T> extends Source<T> {

    boolean hasMore() throws RuntimeException;

}
