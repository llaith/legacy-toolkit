package org.llaith.toolkit.core.registry;

import java.util.Map;

/**
 * Remember, cannot subclass an immutable class or a subclass may add mutations.
 */
public final class BaseImmutableRegistry<V> extends AbstractBaseRegistry<V> implements ImmutableRegistry<V> {

    public BaseImmutableRegistry(final Map<RegistryToken<?>,V> values) {
        super(values);
    }

}
