package org.llaith.toolkit.core.pump.impl;

import org.llaith.toolkit.common.exception.ExceptionHandler;
import org.llaith.toolkit.core.pump.Chunk;
import org.llaith.toolkit.core.pump.Sink;

/**
 *
 */
public class DurableSink<T> implements Sink<T> {

    private final Sink<T> delegate;
    private final ExceptionHandler handler;

    public DurableSink(final ExceptionHandler handler, final Sink<T> delegate) {
        this.handler = handler;
        this.delegate = delegate;
    }

    @Override
    public void put(final Chunk<T> chunk) throws RuntimeException {
        try {
            this.delegate.put(chunk);
        } catch (Exception e) {
            this.handler.onException(e);
        }
    }

    @Override
    public void close() throws RuntimeException {
        this.delegate.close();
    }

}
