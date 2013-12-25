package org.llaith.toolkit.core.pump;

import org.llaith.toolkit.common.lang.TypedAutoCloseable;

/**
 *
 */
public interface EnumeratedPump<T> extends TypedAutoCloseable<RuntimeException> {

    void drain(EnumeratedSource<T> source, Sink<T> sink) throws RuntimeException;

}

