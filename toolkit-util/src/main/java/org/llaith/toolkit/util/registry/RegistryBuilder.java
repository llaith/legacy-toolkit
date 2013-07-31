/*
 * Copyright (c) 2013 Nos Doughty
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
 */
package org.llaith.toolkit.util.registry;

import org.llaith.toolkit.util.misc.Factory;
import org.llaith.toolkit.util.misc.FactoryException;
import org.llaith.toolkit.util.misc.Id;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class RegistryBuilder {

    private Map<Id<?>,Object> map = new HashMap<>();

    public class RegistryEntry<X,Y extends X> {
        private final Id<X> id;
        RegistryEntry(final Id<X> id) {
            this.id = id;
        }
        public void to(Y value) {
            map.put(id,value);
        }
        public void fromFactory(Factory<Y> value) throws FactoryException {
            map.put(id,value.newInstance());
        }
    }

    public <X,Y extends X> RegistryEntry<X,Y> register(Id<X> id) {
        return new RegistryEntry<>(id);
    }

    public <X,Y extends X> RegistryEntry<X,Y> register(Class<X> idClass) {
        return new RegistryEntry<>(Id.newId(idClass));
    }

    public Registry buildRegistry() {
        return new Registry(map);
    }

}
