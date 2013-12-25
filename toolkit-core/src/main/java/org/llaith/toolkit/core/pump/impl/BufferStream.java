package org.llaith.toolkit.core.pump.impl;

import org.llaith.toolkit.core.pump.Stream;
import org.llaith.toolkit.common.guard.Guard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;

/**
 * Very simple implementation of a buffer.
 */
public class BufferStream<T> implements Stream<T> {

    private static final Logger Log = LoggerFactory.getLogger(BufferStream.class);

    private final Queue<T> queue;

    public BufferStream(final Queue<T> queue) {
        this.queue = Guard.notNull(queue);
    }

    @Override
    public T read() {
        return this.queue.poll();
    }

    @Override
    public void close() throws RuntimeException {
        if (!this.queue.isEmpty()) Log.warn(
                "BufferStream is closing with "+
                this.queue.size()+
                " elements in the queued.");
    }

}
