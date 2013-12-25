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

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates a common pattern in code. Used where you might want a simple
 * Registry, but want a mutable version instead. May want to wrap in a
 * ThreadLocal in practice.
 */
public class AbstractBaseRegistry<V> implements Registry<V> {

    protected final Map<RegistryToken<?>,V> index = new HashMap<>();

    protected AbstractBaseRegistry() {
        super();
    }

    protected AbstractBaseRegistry(final Map<RegistryToken<?>,V> values) {
        if (values != null) this.index.putAll(values);
    }

    @Override
    public <X extends V> boolean contains(RegistryToken<X> key) {
        return this.index.containsKey(Guard.notNull(key));
    }

    @Override
    public <X extends V> X get(RegistryToken<X> key) throws RegistryException {
        if (!this.contains(key)) throw new RegistryException("Registry does not contain a value for the key: "+key);
        return key.castTarget(this.index.get(key));
    }

}
