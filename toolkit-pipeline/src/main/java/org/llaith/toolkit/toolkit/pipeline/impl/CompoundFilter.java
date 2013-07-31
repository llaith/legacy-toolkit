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

import org.llaith.toolkit.toolkit.pipeline.Filter;
import org.llaith.toolkit.toolkit.pipeline.FilterException;
import org.llaith.toolkit.util.guard.Guard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Returns acceptance if all filters accept.
 */
public class CompoundFilter<T> extends AbstractPipelineCompound implements Filter<T> {

    private final List<Filter<T>> filters = new ArrayList<>();

    @SafeVarargs
    public CompoundFilter(final Filter<T>... filters) {
        this(Arrays.asList(filters));
    }

    public CompoundFilter(final Collection<Filter<T>> filters) {
        for (final Filter<T> s : Guard.expect(filters,"Expected a value for 'filters'.")) {
            this.filters.add(this.track(s));
        }
    }


    @Override
    public boolean accept(T t) throws FilterException {
        for (final Filter<T> filter : this.filters) {
            if (!filter.accept(t)) return false;
        }
        return true;
    }
}
