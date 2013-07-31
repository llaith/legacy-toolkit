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

import org.llaith.toolkit.util.misc.Id;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Registry {

    private final Map<Id<?>,Object> instances = new HashMap<>();

    public Registry(Map<Id<?>, Object> instances) {
        this.instances.putAll(instances);
    }

    public boolean contains(Id<?> id) {
        return this.instances.containsKey(id);
    }

    public <X>X get(Id<X> id) throws RegistryException {
        if (!this.instances.containsKey(id)) throw new RegistryException("Cannot find service/instance with id: "+id.name());
        return id.targetClass().cast(this.instances.get(id));
    }

}
