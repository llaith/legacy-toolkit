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

import org.llaith.toolkit.core.pump.Chunk;
import org.llaith.toolkit.core.pump.Source;
import org.llaith.toolkit.core.pump.Stream;
import org.llaith.toolkit.common.guard.Guard;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts streamed results into chunks for processing. If you had more complex
 * cases than just counting the max number then you should provide a different
 * implementation.
 */
public class BatchStreamSource<T> implements Source<T> {

    private final Stream<T> stream;
    private final int batchSize;

    public BatchStreamSource(final Stream<T> stream, final int batchSize) {
        this.stream = Guard.notNull(stream);
        this.batchSize = batchSize;
    }

    @Override
    public Chunk<T> get() {

        final List<T> list = new ArrayList<>();

        int count = 0;

        T t;
        do {
            t = this.stream.read();

            if (t != null) {
                count++;
                list.add(t);

                if ( (this.batchSize > 0) && (count == this.batchSize) ) {
                    break;
                }
            }

        } while (t != null);

        // streams are simpler than chunks and a null return is translated into
        // a null stream, not an empty chunk.
        if (list.isEmpty()) return null;

        return new Chunk<>(list);

    }

    @Override
    public void close() throws RuntimeException {
        this.stream.close();
    }

}
