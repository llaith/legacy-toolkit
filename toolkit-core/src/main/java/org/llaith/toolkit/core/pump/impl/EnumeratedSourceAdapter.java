package org.llaith.toolkit.core.pump.impl;

import org.llaith.toolkit.core.pump.Chunk;
import org.llaith.toolkit.core.pump.EnumeratedSource;
import org.llaith.toolkit.core.pump.Source;

/**
 *
 */
public class EnumeratedSourceAdapter<T> implements EnumeratedSource<T> {

    private final Source<T> source;

    private boolean moreAvailable = true;

    public EnumeratedSourceAdapter(Source<T> source) {
        this.source = source;
    }

    @Override
    public boolean hasMore() throws RuntimeException {
        return this.moreAvailable;
    }

    @Override
    public Chunk<T> get() throws RuntimeException {
        Chunk<T> next = null;
        try {
            next = this.source.get();
        } catch (RuntimeException e) {
            this.moreAvailable = false;
        }
        return next;
    }

    @Override
    public void close() throws RuntimeException {
        this.source.close();
    }

}
