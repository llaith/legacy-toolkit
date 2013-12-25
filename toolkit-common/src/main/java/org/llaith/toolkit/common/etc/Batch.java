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
package org.llaith.toolkit.common.etc;

import org.llaith.toolkit.common.lang.TypedAutoCloseable;

import java.util.ArrayList;
import java.util.List;

/**
 * Primarily used for getting simply getting batches out of streams. If you know you
 * are working with batches, it's less trouble than using this class.
 */
public class Batch<T,E extends Exception> implements TypedAutoCloseable<E> {

    public interface FlushHandler<T,E extends Exception> {
        void onFlush(Iterable<T> t) throws E;
    }

    private final int batchSize;
    private final FlushHandler<T,E> handler;

    private final List<T> records = new ArrayList<>();

    public Batch(final int batchSize, final FlushHandler<T,E> handler) {
        this.batchSize = batchSize;
        this.handler = handler;
    }

    public void add(final T t) throws E {
        this.records.add(t);
        if (this.records.size() > this.batchSize) this.flush();
    }

    public void flush() throws E {
        this.handler.onFlush(this.records);
        this.records.clear();
    }

    @Override
    public void close() throws E {
        if (!this.records.isEmpty()) this.flush();
    }

}
