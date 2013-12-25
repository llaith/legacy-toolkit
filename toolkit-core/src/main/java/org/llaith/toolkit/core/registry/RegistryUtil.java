/*******************************************************************************

  * Copyright (c) 2005 - 2013 Nos Doughty
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *     http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.

 ******************************************************************************/
package org.llaith.toolkit.core.registry;

import org.llaith.toolkit.common.guard.Guard;

/**
 * Some utils around Indexes, starting with a synchronized wrapper implemented in
 * the style of the Collections framework.
 */
public class RegistryUtil {

    private RegistryUtil(){}

    /**
     *
     */
    public static <V> MutableRegistry<V> synchronizedRegistry(final MutableRegistry<V> index) {
        return new SynchronizedRegistry<>(index);
    }

    /**
     *
     */
    public static <V> MutableRegistry<V> unmodifiedRegistry(final MutableRegistry<V> index) {
        return new UnmodifiableRegistry<>(index);
    }

    /**
     * Makes a mutable-registry synchronized. Doesn't make sense for an immutable one.
     * Remember, this does not lock on the underlying registry, so multiple instances
     * of this will only lock on themselves.
     */
    private static class SynchronizedRegistry<V> implements MutableRegistry<V> {

        final MutableRegistry<V> registry;
        final Object mutex;

        SynchronizedRegistry(MutableRegistry<V> registry) {
            this.registry = Guard.notNull(registry);
            mutex = this;
        }

        @Override
        public <X extends V> X put(final RegistryToken<X> key, final X value) {
            synchronized (this.mutex) {return this.registry.put(key,value);}
        }

        @Override
        public <X extends V> X remove(RegistryToken<X> key) {
            synchronized (this.mutex) {return this.registry.remove(key);}
        }

        @Override
        public <X extends V> boolean contains(RegistryToken<X> key) {
            synchronized (this.mutex) {return this.registry.contains(key);}
        }

        @Override
        public <X extends V> X get(RegistryToken<X> key) throws RegistryException {
            synchronized (this.mutex) {return this.registry.get(key);}
        }
    }

    /**
     * Brute force approach, sometimes we want to make an existing mutable
     * registry immutable in practice. You may want to pass a synchronized wrapped
     * implementation in if references to it are elsewhere.
     */
    private static class UnmodifiableRegistry<V> implements MutableRegistry<V> {

        final MutableRegistry<V> registry;
        final Object mutex;

        UnmodifiableRegistry(final MutableRegistry<V> registry) {
            this.registry = Guard.notNull(registry);
            mutex = this;
        }

        @Override
        public <X extends V> X put(final RegistryToken<X> key, final X value) {
            throw new UnsupportedOperationException("Original MutableRegistry has been wrapped by an UnmodifiableRegistry");
        }

        @Override
        public <X extends V> X remove(RegistryToken<X> key) {
            throw new UnsupportedOperationException("Original MutableRegistry has been wrapped by an UnmodifiableRegistry");
        }

        @Override
        public <X extends V> boolean contains(RegistryToken<X> key) {
            return this.registry.contains(key);
        }

        @Override
        public <X extends V> X get(RegistryToken<X> key) throws RegistryException {
            return this.registry.get(key);
        }

    }

}
