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
import org.llaith.toolkit.core.pump.Source;
import org.llaith.toolkit.common.lang.AutoCloser;
import org.llaith.toolkit.common.guard.Guard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Returns first result from multiple sources. Note this isn't smart, it will
 * repeatedly query closed sources. We are relying on the fact that they must
 * be designed to withstand that for it to not be a problem. A more efficient
 * impl would move them to an expired set and stop querying them.
 */
public class AnySource<T> implements Source<T> {

    private final List<Source<T>> sources = new ArrayList<>();

    private final AutoCloser closer = new AutoCloser();

    @SafeVarargs
    public AnySource(final Source<T>... sources) {
        this(Arrays.asList(sources));
    }

    public AnySource(final Collection<Source<T>> sources) {
        this.sources.addAll(this.closer.trackAll(Guard.notNull(sources)));
    }

    @Override
    public Chunk<T> get() {
        for (final Source<T> source : this.sources) {
            final Chunk<T> t = source.get();
            if (t != null) return t;
        }
        return null;
    }

    @Override
    public void close() throws RuntimeException {
        this.closer.close();
    }

}
