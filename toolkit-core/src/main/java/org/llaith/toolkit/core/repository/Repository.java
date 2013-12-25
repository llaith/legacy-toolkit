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
package org.llaith.toolkit.core.repository;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

/**
 * Guava's FluentIterable now has almost all the functionality that this used
 * to have. The part that remains is the usage patterns of: (a) passing around
 * a heterogeneous collection and turning it typed only when iterating,
 * (b) snapshotting the collection while iterating and being able to add and
 * remove elements from the original collection while iterating, and (c)
 * supporting chained parent-child repositories.
 *
 */
public interface Repository extends Iterable<Object> {

    <X> X put(X object);

    Repository add(Object object);

    Repository addAll(Iterable<?> objects);

    Repository remove(Object object);

    Repository removeAll(Iterable<?> objects);

    <X> FluentIterable<X> select(Class<X> klass); // takes a snapshot

    <X> FluentIterable<X> select(final Class<X> klass, final Predicate<X> predicate);

}
