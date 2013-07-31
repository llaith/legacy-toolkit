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
package org.llaith.toolkit.util.misc;


import java.util.UUID;


/**
 *
 */
public class Id<T> {

    public static <X>Id<X> randomId(final Class<X> klass) {
        return new Id<>(klass,UUID.randomUUID());
    }

    public static <X>Id<X> newId(final Class<X> klass) {
        return new Id<>(klass);
    }

    public static <X>Id<X> newId(final Class<X> klass, final String ident) {
        return new Id<>(klass,ident);
    }

    public static <X>Id<X> newId(final Class<X> klass, final UUID uuid) {
        return new Id<>(klass,uuid);
    }

    private final Class<T> targetClass;
    private final String name;

    protected Id(final Class<T> targetClass) {
        this(targetClass,targetClass.getCanonicalName());
    }

    protected Id(final Class<T> targetClass, final UUID uuid) {
        this(targetClass,uuid.toString());
    }

    protected Id(final Class<T> targetClass, final String name) {
        super();
        this.targetClass = targetClass;
        this.name = name;
    }

    public Class<T> targetClass() {
        return this.targetClass;
    }

    public String name() {
        return this.name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Id<?> that = (Id<?>)o;

        if (this.targetClass != null ? !this.targetClass.equals(that.targetClass) : that.targetClass != null) return false;
        if (this.name != null ? !this.name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = this.targetClass != null ? this.targetClass.hashCode() : 0;
        result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "com.llaith.util.misc.Id{" +
                "targetClass=" + this.targetClass +
                ", uuid=" + this.name +
                '}';
    }

}
