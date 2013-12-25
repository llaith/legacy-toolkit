package org.llaith.toolkit.core.registry;

/**
 * Marker interface. Exists to stop being able to pass a
 * mutable implementation to a receiver that is expecting
 * an immutable one.
 */
public interface ImmutableRegistry<V> extends Registry<V> {

    // nothing needed

}
