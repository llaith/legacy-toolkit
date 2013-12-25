package org.llaith.toolkit.core.pump.ext.repository;

import org.llaith.toolkit.core.pump.Chunk;
import org.llaith.toolkit.core.pump.Sink;
import org.llaith.toolkit.core.repository.Repository;

/**
 *
 */
public class RepositorySink<T> implements Sink<T> {

    private final Repository repository;

    public RepositorySink(final Repository repository) {
        this.repository = repository;
    }

    @Override
    public void put(final Chunk<T> chunk) throws RuntimeException {
        if (chunk != null) {
            this.repository.addAll(chunk);
        }
    }

    @Override
    public void close() throws RuntimeException {
        // blank
    }

}
