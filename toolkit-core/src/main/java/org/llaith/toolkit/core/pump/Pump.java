package org.llaith.toolkit.core.pump;

import org.llaith.toolkit.common.lang.TypedAutoCloseable;

/**
 * If you pump durablesources with a non-durablepump, it's ok, they'll just act
 * like non-durable sources.
 */
public interface Pump<T> extends TypedAutoCloseable<RuntimeException> {

    void drain(Source<T> source, Sink<T> sink) throws RuntimeException;

}
