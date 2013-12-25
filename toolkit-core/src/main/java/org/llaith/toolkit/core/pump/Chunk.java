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

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.llaith.toolkit.common.guard.Guard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Consider the fp 'cons' (lazy sequence) structure. Could the processors be
 * more interesting based on that?
 */
public class Chunk<T> implements Iterable<T> {

    public static <X> boolean notEmpty(final Chunk<X> chunk) {
        return !isEmpty(chunk);
    }

    public static <X> boolean isEmpty(final Chunk<X> chunk) {
        return chunk == null || chunk.isEmpty();
    }

    private final List<T> list = new ArrayList<>();

    public Chunk() {
        super(); // an empty chunk
    }

    public Chunk(final List<T> list) {
        this.list.addAll(Guard.notNull(list));
    }

    public int getSize() {
        return this.list.size();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return this.list.iterator();
    }

    public Chunk<T> apply(final Predicate<T> predicate) {
        final List<T> list = new ArrayList<>();
        for (T t : this) {
            if (predicate.apply(t)) list.add(t);
        }
        return new Chunk<>(list);
    }

    public <X> Chunk<X> apply(final Function<T,X> function) {
        final List<X> list = new ArrayList<>();
        for (T t : this) {
            list.add(function.apply(t));
        }
        return new Chunk<>(list);
    }

}

