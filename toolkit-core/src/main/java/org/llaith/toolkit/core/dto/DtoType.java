package org.llaith.toolkit.core.dto;


import org.llaith.toolkit.common.exception.ext.UncheckedException;
import org.llaith.toolkit.common.guard.Guard;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class DtoType<T extends DtoObject<T>> {

    private final Class<T> instanceClass;
    private final Object domain;

    private final Map<String,DtoField<?>> index;

    public DtoType(final Class<T> instanceClass, final Collection<? extends DtoField<?>> fields) {
        this(instanceClass.getName(),instanceClass,fields);
    }

    public DtoType(final Object domain, final Class<T> instanceClass, final Collection<? extends DtoField<?>> fields) {
        this.domain = Guard.notNull(domain); // use UUID.randomUUID() if wanting a unique (anon) domain
        this.instanceClass = Guard.notNull(instanceClass);

        this.index = indexNames(fields);
    }

    private Map<String,DtoField<?>> indexNames(final Iterable<? extends DtoField<?>> fields) {
        final Map<String,DtoField<?>> index = new HashMap<>();
        for (final DtoField<?> f : fields) {
            index.put(f.id(),f);
        }
        return index;
    }

    public Class<T> instanceClass() {
        return this.instanceClass;
    }

    public T newInstance() {
        try {
            return this.instanceClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw UncheckedException.wrap(e);
        }
    }

    public Object domain() {
        return this.domain;
    }

    public void add(final DtoField<Object> field) {
        this.index.put(field.id(),field);
    }

    public void addAll(final Iterable<DtoField<Object>> fields) {
        this.index.putAll(indexNames(fields));
    }

    public boolean has(final String name) {
        return this.index.containsKey(name);
    }

    public DtoField<?> get(final String name) {
        return this.index.get(name);
    }

    public Map<String,DtoField<?>> index() {
        return new HashMap<>(this.index);
    }

    public Collection<DtoField<?>> fields() {
        return this.index.values();
    }

    public Collection<String> names() {
        return this.index.keySet();
    }

    @Override
    public final int hashCode() {
        // don't override
        return super.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        // don't override
        return super.equals(obj);
    }

}
