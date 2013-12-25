/*
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
 */
package org.llaith.toolkit.core.pump.impl;

import com.google.common.base.Function;
import org.llaith.toolkit.core.pump.Chunk;
import org.llaith.toolkit.core.pump.Sink;
import org.llaith.toolkit.common.guard.Guard;

/**
 *
 */
public class TransformSink<I, O> implements Sink<I> {

    private final Sink<O> sink;
    private final Function<I, O> function;

    public TransformSink(final Sink<O> sink, final Function<I,O> function) {
        this.sink = Guard.notNull(sink);
        this.function = Guard.notNull(function);
    }

    @Override
    public void put(final Chunk<I> chunk) {
        if (chunk != null) this.sink.put(chunk.apply(this.function));
    }

    @Override
    public void close() throws RuntimeException {
        this.sink.close();
    }

}
