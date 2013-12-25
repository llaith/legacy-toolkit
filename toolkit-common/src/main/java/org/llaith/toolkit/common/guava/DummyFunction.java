package org.llaith.toolkit.common.guava;

import com.google.common.base.Function;

/**
 *
 */
public class DummyFunction<T> implements Function<T,T> {

    @Override
    public T apply(final T input) {
        return input;
    }

}
