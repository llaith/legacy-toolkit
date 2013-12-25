package org.llaith.toolkit.common.guava;

import com.google.common.base.Supplier;

/**
 * Returns a pre-existing instance.
 */
public class SharedInstanceSupplier<T> implements Supplier<T> {

    private final T instance;

    public SharedInstanceSupplier(final T instance) {
        this.instance = instance;
    }

    @Override
    public T get() {
        return this.instance;
    }

}
