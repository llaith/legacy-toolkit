package org.llaith.toolkit.core.dto.session;

import org.llaith.toolkit.core.dto.DtoObject;

/**
 * An event which represents a request to synchronise another DtoObject
 * from the source's state.
 *
 * @param <T> the type of the event source
 */
public class DtoRefreshEvent<T extends DtoObject<T>> {

    private final T source;

    public DtoRefreshEvent(final T source) {
        this.source = source;
    }

    /**
     * Retrieves the source object that is to be used as the master
     * for the synchronization.
     *
     * @return the source object
     */
    public T source() {
        return source;
    }

}
