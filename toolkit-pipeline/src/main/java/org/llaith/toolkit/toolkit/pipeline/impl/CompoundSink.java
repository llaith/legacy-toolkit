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

import org.llaith.toolkit.toolkit.pipeline.Sink;
import org.llaith.toolkit.toolkit.pipeline.SinkException;
import org.llaith.toolkit.util.guard.Guard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Applies all transformations.
 */
public class CompoundSink<T> extends AbstractPipelineCompound implements Sink<T> {

    private final List<Sink<T>> sinks = new ArrayList<>();

    @SafeVarargs
    public CompoundSink(final Sink<T>... sinks) {
        this(Arrays.asList(sinks));
    }

    public CompoundSink(final Collection<Sink<T>> sinks) {
        for (final Sink<T> s : Guard.expect(sinks,"Expected a value for 'sinks'.")) {
            this.sinks.add(this.track(s));
        }
    }

    @Override
    public void write(T t) throws SinkException {
        for (final Sink<T> sink : this.sinks) {
            sink.write(t);
        }
    }
}
