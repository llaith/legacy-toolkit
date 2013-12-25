package org.llaith.toolkit.core.pump.impl;

import org.llaith.toolkit.common.exception.ExceptionHandler;
import org.llaith.toolkit.common.exception.ext.UncheckedException;
import org.llaith.toolkit.core.pump.Chunk;
import org.llaith.toolkit.core.pump.Source;

/**
 * Warning, it's conceivable this will get stuck in a loop. You may need to either
 * use the EnumeratedPump, which calls the hasMore() on an enumerated source or
 * the error handler passed in should have a max errors setting after which it throws
 * the exception.
 */
public class DurableSource<T> implements Source<T> {

    private final Source<T> delegate;
    private final ExceptionHandler handler;
    private final int maxErrors;

    private int errorCount = 0;

    public DurableSource(final ExceptionHandler handler, final Source<T> delegate, final int maxErrors) {
        this.handler = handler;
        this.delegate = delegate;
        this.maxErrors = maxErrors;
    }

    @Override
    public Chunk<T> get() throws RuntimeException {
        while (true) {
            try {
                final Chunk<T> ret = this.delegate.get();
                this.errorCount = 0;
                return ret;
            } catch (Exception e) {
                this.errorCount++;
                if (this.errorCount >= this.maxErrors) throw new UncheckedException("Too many sequential exceptions.",e);
                this.handler.onException(e);
            }
        }
    }

    @Override
    public void close() throws RuntimeException {
        this.delegate.close();
    }

}
