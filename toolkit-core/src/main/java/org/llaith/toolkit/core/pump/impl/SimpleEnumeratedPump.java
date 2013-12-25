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

import com.google.common.base.Supplier;
import org.llaith.toolkit.common.exception.ExceptionHandler;
import org.llaith.toolkit.core.pump.Chunk;
import org.llaith.toolkit.core.pump.EnumeratedPump;
import org.llaith.toolkit.core.pump.EnumeratedSource;
import org.llaith.toolkit.core.pump.Sink;
import org.llaith.toolkit.common.guard.Guard;

/**
 * Very simple non-active (Data)Pump implementation. No pauses when no data but
 * not exhausted, making it unsuitable for using with the Queue source.
 *
 * Note the source and sink should be closed, use with a command pattern to do
 * it automatically.
 */
public class SimpleEnumeratedPump<T> implements EnumeratedPump<T> {

    public static <X extends Exception> Supplier<EnumeratedPump<X>> newSupplier() {
        return new Supplier<EnumeratedPump<X>>() {
            @Override
            public EnumeratedPump<X> get() {
                return new SimpleEnumeratedPump<>();
            }
        };
    }

    public void drain(EnumeratedSource<T> source, Sink<T> sink) throws RuntimeException {

        Guard.notNull(source);
        Guard.notNull(sink);

        Chunk<T> chunk;
        do {
            chunk = source.get();
            if (Chunk.notEmpty(chunk)) sink.put(chunk);
        } while (source.hasMore());

    }

    @Override
    public void close() throws RuntimeException {
        // nothing to do
    }

}
