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
package org.llaith.toolkit.core.registry.builder;

import com.google.common.base.Supplier;
import org.llaith.toolkit.core.registry.RegistryToken;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class RegistryBuilder<T> {

    public class RegistryEntry {

        private final RegistryToken<? extends T> id;

        public RegistryEntry(final RegistryToken<? extends T> id) {
            this.id = id;
        }

        public RegistryBuilder<T> to(final T value) {
            RegistryBuilder.this.map.put(this.id,value);
            return RegistryBuilder.this;
        }

        public RegistryBuilder<T> from(final Supplier<T> factory) {
            RegistryBuilder.this.map.put(this.id,factory.get());
            return RegistryBuilder.this;
        }

    }

    private final Map<RegistryToken<? extends T>,T> map = new HashMap<>();

    public RegistryBuilder<T> register(final Registration<T> registration) {
        this.map.put(registration.getId(),registration.getValue());
        return this;
    }

    public RegistryEntry register(final RegistryToken<? extends T> id) {
        return new RegistryEntry(id);
    }

    public RegistryEntry register(final Class<? extends T> idClass) {
        return new RegistryEntry(new RegistryToken<>(idClass));
    }

    public final Map<RegistryToken<? extends T>,T> asMap() {
        return this.map;
    }

}
