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

import com.google.common.collect.Iterables;
import org.llaith.toolkit.core.pump.Chunk;
import org.llaith.toolkit.core.pump.Sink;
import org.llaith.toolkit.common.guard.Guard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;

/**
 * Very simple implementation of a sink buffer.
 */
public class BufferSink<T> implements Sink<T> {

    private static final Logger Log = LoggerFactory.getLogger(BufferSink.class);

    private final Queue<T> queue;

    public BufferSink(final Queue<T> queue) {
        this.queue = Guard.notNull(queue);
    }

    @Override
    public void put(final Chunk<T> chunk) {
        if (chunk != null) Iterables.addAll(this.queue,chunk); // not offer()
    }

    @Override
    public void close() throws RuntimeException {
        if (!this.queue.isEmpty()) Log.warn(
                "BufferSink is closing with "+
                this.queue.size()+
                " elements in the queued.");
    }

}
