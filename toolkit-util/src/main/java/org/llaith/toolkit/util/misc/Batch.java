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
package org.llaith.toolkit.util.misc;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Batch<T> {

    public interface FlushHandler<T> {
        void onFlush(Iterable<T> t);
    }

    private final int batchSize;
    private final FlushHandler<T> handler;

    private final List<T> records = new ArrayList<>();

    public Batch(final int batchSize, final FlushHandler<T> handler) {
        this.batchSize = batchSize;
        this.handler = handler;
    }

    public void start() {
        if (!this.records.isEmpty()) throw new RuntimeException("Batch still has a batch in progress");
        this.records.clear();
    }

    public void addRecord(final T t) {
        this.records.add(t);
        if (this.records.size() > this.batchSize) this.flush();
    }

    public void stop() {
        if (!this.records.isEmpty()) this.flush();
    }

    private void flush() {
        this.handler.onFlush(this.records);
        this.records.clear();
    }

}
