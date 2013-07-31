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
package org.llaith.toolkit.util.meta;

import org.llaith.toolkit.util.guard.Guard;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class MetadataHolderDelegate implements MetadataHolder {

    private final Map<String,Object> metadatas = new HashMap<>();

    public boolean containsMetadata( final Class<?> metadataClass) {
        return metadatas.containsKey(metadataClass.getName());
    }

    public <X> X getMetadata( final Class<X> metadataClass) {
        if (Annotation.class.isAssignableFrom(metadataClass)) return metadataClass.cast(this.metadatas.get(metadataClass.getName()));
        if (Proxy.class.isAssignableFrom(metadataClass)) throw new IllegalArgumentException("Cannot work with Proxy classes.");
        return metadataClass.cast(this.metadatas.get(metadataClass.getName()));
    }

    public void setMetadata( final Object metadata) {
        if (metadata instanceof Annotation) {
            this.metadatas.put( ((Annotation)metadata).annotationType().getName(),Guard.expectParam("metadata",metadata));
        }
        // This should be able to work. Annotation is an interface, so we can cast. Without this, I can't create Proxy annotations as metadata!
        // else if (Proxy.class.isAssignableFrom(metadata.getClass())) throw new IllegalArgumentException("Cannot work with java.lang.reflect.Proxy classes.");
        else this.metadatas.put(metadata.getClass().getName(),Guard.expectParam("metadata",metadata));
    }

    public void removeMetadata( final Class<?> metadataClass) {
        this.metadatas.remove(metadataClass.getName());
    }

}
