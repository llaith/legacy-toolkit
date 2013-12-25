package org.llaith.toolkit.core.registry.builder;

import org.llaith.toolkit.core.registry.RegistryToken;

/**
 *
 */
public class Registration<T> {

    private final RegistryToken<? extends T> id;
    private final T value;

    public Registration(RegistryToken<? extends T> id, T value) {
        this.id = id;
        this.value = value;
    }

    public RegistryToken<? extends T> getId() {
        return id;
    }

    public T getValue() {
        return value;
    }

}
