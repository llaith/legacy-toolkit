/*
 * Copyright (c) 2013 Nos Doughty
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
 */
package org.llaith.toolkit.toolkit.pipeline.impl;

import org.llaith.toolkit.toolkit.pipeline.Source;
import org.llaith.toolkit.toolkit.pipeline.SourceException;
import org.llaith.toolkit.util.guard.Guard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Returns first result from multiple sources.
 */
public class CompoundSource<T> extends AbstractPipelineCompound implements Source<T> {

    private final List<Source<T>> sources = new ArrayList<>();

    @SafeVarargs
    public CompoundSource(final Source<T>... sources) {
        this(Arrays.asList(sources));
    }

    public CompoundSource(final Collection<Source<T>> sources) {
        for (final Source<T> s : Guard.expect(sources,"Expected a value for 'sources'.")) {
            this.sources.add(this.track(s));
        }
    }

    @Override
    public T read() throws SourceException {
        for (final Source<T> source : this.sources) {
            final T t = source.read();
            if (t != null) return t;
        }
        return null;
    }

}
