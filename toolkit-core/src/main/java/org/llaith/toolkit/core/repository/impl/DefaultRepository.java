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
package org.llaith.toolkit.core.repository.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import org.llaith.toolkit.core.repository.Repository;
import org.llaith.toolkit.common.guard.Guard;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 */
public class DefaultRepository implements Repository {

    private final Repository parentRepository;

    private final Set<Object> values = new HashSet<>();

    public DefaultRepository() {
        this((Repository)null);
    }

    public DefaultRepository(final Collection<?> values) {
        this((Repository)null);
        this.addAll(values);
    }

    public DefaultRepository(final Repository parentRepository) {
        super();
        this.parentRepository = parentRepository;
    }

    @Override
    public <X> X put(X value) {
        this.values.add(value);
        return value;
    }

    @Override
    public final Repository add(final Object value) {
        this.values.add(value);
        return this;
    }


    @Override
    public Repository addAll(final Iterable<?> values) {
        for (final Object value : values) {
            this.add(value);
        }
        return this;
    }

    @Override
    public Repository remove(final Object object) {
        this.values.remove(object);
        return this;
    }

    @Override
    public Repository removeAll(final Iterable<?> objects) {
        for (final Object value : values) {
            this.remove(value);
        }
        return this;
    }

    @Override
    public <X> FluentIterable<X> select(final Class<X> klass) {
        return FluentIterable.from(new ImmutableSet.Builder<X>()
                .addAll(FluentIterable.from(Guard.toEmpty(this.parentRepository)).filter(klass))
                .addAll(FluentIterable.from(Guard.toEmpty(this.values)).filter(klass))
                .build());
    }

    @Override
    public <X> FluentIterable<X> select(final Class<X> klass, final Predicate<X> predicate) {
        return FluentIterable.from(new ImmutableSet.Builder<X>()
                .addAll(FluentIterable.from(Guard.toEmpty(this.parentRepository)).filter(klass).filter(predicate))
                .addAll(FluentIterable.from(Guard.toEmpty(this.values)).filter(klass).filter(predicate))
                .build());
    }

    @Override
    public Iterator<Object> iterator() {
        return this.select(Object.class).iterator();
    }

}
