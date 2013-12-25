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
package org.llaith.toolkit.core.pump.impl;

import org.llaith.toolkit.core.pump.Chunk;
import org.llaith.toolkit.core.pump.Sink;
import org.llaith.toolkit.common.lang.AutoCloser;
import org.llaith.toolkit.common.guard.Guard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Applies all transformations.
 */
public class EverySink<T> implements Sink<T> {

    private final List<Sink<T>> sinks = new ArrayList<>();

    private final AutoCloser closer = new AutoCloser();

    @SafeVarargs
    public EverySink(final Sink<T>... sinks) {
        this(Arrays.asList(sinks));
    }

    public EverySink(final Collection<Sink<T>> sinks) {
        this.sinks.addAll(this.closer.trackAll(Guard.notNull(sinks)));
    }

    @Override
    public void put(final Chunk<T> chunk) {
        if (chunk != null) {
            for (final Sink<T> sink : this.sinks) {
                sink.put(chunk);
            }
        }
    }

    @Override
    public void close() {
        this.closer.close();
    }

}
