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
package org.llaith.toolkit.core.dto;


import com.google.common.base.Objects;
import com.google.common.eventbus.Subscribe;
import org.llaith.toolkit.core.dto.session.DtoBus;
import org.llaith.toolkit.core.dto.session.DtoRefreshEvent;
import org.llaith.toolkit.common.guard.Guard;
import org.llaith.toolkit.common.meta.MetadataContainer;
import org.llaith.toolkit.common.meta.MetadataDelegate;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * <p/>
 * NOTE: if you want a non-param ctor (such as for jaxws) you need to make a
 * concrete-baseclass that passes it in. Eg:
 * <pre>
 *                 class SomeObject extends DtoObject {
 *                     public SomeObject() {
 *                         super(DataTypes.SOME_OBJECT);
 *                     }
 *                     // also
 *                     SomeObject getThis() {
 *                         return this;
 *                     }
 *                     ...
 *                 }
 *                 </pre>
 * NOTE ALSO: These are not value objects. Each instance of this class is not
 * equivalent. This is because of the change tracking. However we can compare
 * them with a little more work by using the Identifying field defs.
 */
public abstract class DtoObject<T extends DtoObject<T>> implements Dto<T>, MetadataContainer {

    public static class FieldInit {

        private final boolean accept;
        private final Map<String,Object> values = new HashMap<>();

        public FieldInit() {
            this(true);
        }

        public FieldInit(final boolean accept) {
            this.accept = accept;
        }

        public <X> FieldInit initField(final DtoField<X> field, X value) {
            this.values.put(field.id(),value);
            return this;
        }

        public Map<String,Object> values() {
            return values;
        }

        public boolean isAccept() {
            return accept;
        }

    }

    public class Identity {

        private final Object domain;
        private final Map<String,Object> identity;

        private Identity() {
            this.domain = DtoObject.this.dtoType().domain();
            this.identity = new HashMap<>(DtoObject.this.identityValues());
        }

        @Override
        public boolean equals(final Object o) {
            return this == o
                    || !(o == null || getClass() != o.getClass())
                    && this.domain.equals(((Identity)o).domain)
                    && identity.equals(((Identity)o).identity);
        }

        @Override
        public int hashCode() {
            int result = domain.hashCode();
            result = 31 * result + identity.hashCode();
            return result;
        }

    }

    private final MetadataDelegate metadatas = new MetadataDelegate();

    private final DtoType<T> dtoType;

    private final Map<String,DtoValue<?>> index = new HashMap<>();

    private Identity identity;

    private DtoBus bus = null;

    public DtoObject(final DtoType<T> dtoType) {
        this.dtoType = Guard.notNull(dtoType);

        this.index.putAll(this.indexNew(dtoType));
    }

    /*
     * Calling this version of the ctor creates pre-accepted instances. Cheats and
     * just slams it in, using builder to enforce correct typing, but never mind.
     */
    public DtoObject(final DtoType<T> dtoType, final FieldInit init) {
        this(dtoType);

        for (final Entry<String,Object> entry : init.values.entrySet()) {
            this.set(entry.getKey(),entry.getValue());
        }

        if (init.isAccept()) this.acceptChanges();
    }

    public abstract T getThis();

    public boolean isNew() {
        return this.identity == null;
    }

    public Identity identity() {
        return this.identity;
    }

    public DtoType<T> dtoType() {
        return this.dtoType;
    }

    public DtoValue<?> dtoValue(final String id) {
        return this.index.get(id);
    }

    public Map<String,DtoValue<?>> values() {

        final Map<String,DtoValue<?>> map = new HashMap<>();

        for (final Entry<String,DtoValue<?>> entry : DtoObject.this.index.entrySet()) {
            map.put(
                    entry.getKey(),
                    entry.getValue());
        }

        return map;
    }

    public boolean has(final String name) {
        return this.value(name).hasCurrent();
    }

    public <X> X get(final DtoField<X> field) {
        return this.value(field).currentValue();
    }

    public Object get(final String name) {
        return this.value(name).currentValue();
    }

    public <X> T set(final DtoField<X> field, final X value) {
        this.value(field).setValue(value,this.isNew());

        return this.getThis();
    }

    public T set(final String name, final Object value) {
        this.value(name).setValueAsObject(value,this.isNew());

        return this.getThis();
    }

    public T setValues(final T other) {
        return this.setValuesFrom(other,this.dtoType);
    }

    public T setValuesFrom(final DtoObject<?> other, final DtoType<?> dtoType) {
        // technically we could use index.keySet(), but requiring us to know the
        // dtotype is a deliberate check against a common mistake, and if we're
        // passing it we may as well use it.
        for (final String name : dtoType.names()) {
            this.set(name,other.get(name));
        }
        return this.getThis();
    }

    public T setValuesFrom(final Map<String,Object> map) {
        for (String id : map.keySet()) {
            this.set(id,map.get(id));
        }
        return this.getThis();
    }

    public T resetValues(final T other) {
        return this.resetValuesFrom(other,dtoType);
    }

    public T resetValuesFrom(final DtoObject<?> other, final DtoType<?> dtoType) {
        for (final String name : dtoType.names()) {
            this.reset(name,other.get(name));
        }
        return DtoObject.this.getThis();
    }

    public T resetValuesFrom(final Map<String,Object> map) {
        for (String id : map.keySet()) {
            this.reset(id,map.get(id));
        }
        return getThis();
    }

    public <X> T reset(final DtoField<X> field, final X value) {
        DtoObject.this.value(field).resetValue(value);

        return DtoObject.this.getThis();
    }

    public T reset(final String name, final Object value) {
        DtoObject.this.value(name).resetValueAsObject(value);

        return DtoObject.this.getThis();
    }

    public Map<String,Object> currentValues() {

        final Map<String,Object> map = new HashMap<>();

        for (final Entry<String,DtoValue<?>> entry : DtoObject.this.index.entrySet()) {
            map.put(
                    entry.getKey(),
                    entry.getValue().currentValue());
        }

        return map;
    }

    public Map<String,Object> originalValues() {

        final Map<String,Object> map = new HashMap<>();

        for (final Entry<String,DtoValue<?>> entry : DtoObject.this.index.entrySet()) {
            map.put(
                    entry.getKey(),
                    entry.getValue().originalValue());
        }

        return map;
    }

    public Map<String,DtoValue<?>> dirtyFields() {

        final Map<String,DtoValue<?>> map = new HashMap<>();

        for (final Entry<String,DtoValue<?>> entry : DtoObject.this.index.entrySet()) {
            if (entry.getValue().isDirty()) map.put(
                    entry.getKey(),
                    entry.getValue());
        }

        return map;
    }

    public Map<String,Object> dirtyValues() {

        final Map<String,Object> map = new HashMap<>();

        for (final Entry<String,DtoValue<?>> entry : DtoObject.this.index.entrySet()) {
            if (entry.getValue().isDirty()) map.put(
                    entry.getKey(),
                    entry.getValue().currentValue());
        }

        return map;
    }

    public Map<String,DtoValue<?>> staleFields() {

        final Map<String,DtoValue<?>> map = new HashMap<>();

        for (final Entry<String,DtoValue<?>> entry : DtoObject.this.index.entrySet()) {
            if (entry.getValue().isStale()) map.put(
                    entry.getKey(),
                    entry.getValue());
        }

        return map;
    }

    public Map<String,Object> staleValues() {

        final Map<String,Object> map = new HashMap<>();

        for (final Entry<String,DtoValue<?>> entry : DtoObject.this.index.entrySet()) {
            if (entry.getValue().isStale()) map.put(
                    entry.getKey(),
                    entry.getValue().currentValue());
        }

        return map;
    }

    public Map<String,DtoValue<?>> conflictedFields() {

        final Map<String,DtoValue<?>> map = new HashMap<>();

        for (final Entry<String,DtoValue<?>> entry : DtoObject.this.index.entrySet()) {
            if (entry.getValue().isConflicted()) map.put(
                    entry.getKey(),
                    entry.getValue());
        }

        return map;
    }

    public Map<String,Object> conflictedValues() {

        final Map<String,Object> map = new HashMap<>();

        for (final Entry<String,DtoValue<?>> entry : DtoObject.this.index.entrySet()) {
            if (entry.getValue().isConflicted()) map.put(
                    entry.getKey(),
                    entry.getValue().currentValue());
        }

        return map;
    }

    public Map<String,DtoValue<?>> identityFields() {

        final Map<String,DtoValue<?>> map = new HashMap<>();

        for (final Entry<String,DtoValue<?>> entry : DtoObject.this.index.entrySet()) {
            if (entry.getValue().dtoField().isIdentity()) map.put(
                    entry.getKey(),
                    entry.getValue());
        }

        return map;
    }

    public Map<String,Object> identityValues() {

        final Map<String,Object> map = new HashMap<>();

        for (final Entry<String,DtoValue<?>> entry : DtoObject.this.index.entrySet()) {
            if (entry.getValue().dtoField().isIdentity()) map.put(
                    entry.getKey(),
                    entry.getValue().currentValue());
        }

        return map;
    }

    @Subscribe
    public void onRefresh(final DtoRefreshEvent<T> dtoRefreshEvent) {
        // Guaranteed to be for this instance. One eventbus per *identity*.
        if (this == dtoRefreshEvent.source()) return;

        // should never happen
        if (this.isNew()) throw new IllegalStateException("Cannot refresh a new instance");

        this.resetValues(dtoRefreshEvent.source());
    }

    public void register(final DtoBus bus) {
        if (this.isNew()) throw new IllegalStateException("Cannot register a new instance to a bus.");

        if ((this.bus != null) && (this.bus != bus)) throw new IllegalStateException("Cannot register to additional bus.");

        this.bus = bus;
        this.bus.register(this);
    }

    public void unregister(final DtoBus bus) {
        if (this.bus != bus) throw new IllegalStateException("Cannot unregister from bus that is not registered to.");

        this.bus.unregister(this);
        this.bus = null;
    }

    @Override
    public boolean isDirty() {
        for (final DtoValue<?> field : this.index.values()) {
            if (field.isDirty()) return true;
        }
        return false;
    }

    @Override
    public boolean isStale() {
        for (final DtoValue<?> field : this.index.values()) {
            if (field.isStale()) return true;
        }
        return false;
    }

    @Override
    public boolean isConflicted() {
        for (final DtoValue<?> field : this.index.values()) {
            if (field.isConflicted()) return true;
        }
        return false;
    }

    @Override
    public T acceptChanges() {

        for (final DtoValue<?> field : this.index.values()) {
            field.acceptChanges();
        }

        // we could fire these on reset also, but i'm going to say that the reset
        // is too low level and we'll fire them on the reset/cancel combination.
        // This is less likely to get confusing, at the small cost of possibly doing
        // an additional resets if objects are not correctly cancelled when disposed.
        if (this.bus != null) this.bus.post(new DtoRefreshEvent<>(this.getThis()));

        if (this.isNew()) this.identity = new Identity();

        return this.getThis();
    }

    @Override
    public T cancelChanges() {

        for (final DtoValue<?> field : this.index.values()) {
            field.cancelChanges();
        }

        if (this.bus != null) this.bus.post(new DtoRefreshEvent<>(this.getThis()));

        return this.getThis();
    }

    @Override
    public <T> T addMetadata(T metadata) {
        return this.metadatas.addMetadata(metadata);
    }

    @Override
    public <T> T replaceMetadata(T metadata) {
        return this.metadatas.replaceMetadata(metadata);
    }

    @Override
    public <T> T metadata(Class<T> metadataClass) {
        return this.metadatas.metadata(metadataClass);
    }

    @Override
    public boolean hasMetadata(Class<?> metadataClass) {
        return this.metadatas.hasMetadata(metadataClass);
    }

    @Override
    public <T> T removeMetadata(Class<T> metadataClass) {
        return this.metadatas.removeMetadata(metadataClass);
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

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("values",this.index.values())
                .toString();
    }

    private Map<String,DtoValue<?>> indexNew(final DtoType<T> dtoType) {

        final Map<String,DtoValue<?>> index = new HashMap<>();

        for (final DtoField<?> dtoField : dtoType.index().values()) {
            index.put(
                    dtoField.id(),
                    new CyclicDtoValue<>(dtoField.newDtoValue()));
        }

        return index;
    }

    private DtoValue<?> value(final String name) {
        if (!this.dtoType.has(name)) throw new IllegalStateException(String.format(
                "The field named: %s is not part of the DtoObject: %s",
                name,
                this.getClass().getName()));

        return this.index.get(name);
    }

    @SuppressWarnings("unchecked")
    private <X> DtoValue<X> value(final DtoField<X> field) {
        // if using fields, *exact instance* registered is ued to avoid possible redefs.
        // if you want to avoid that, use the string methods.
        if (this.dtoType.get(field.id()) != field) throw new IllegalStateException(String.format(
                "The registered field: %s is not part of the DtoObject: %s",
                field.id(),
                this.getClass().getName()));

        // we know we can cast because it's the same instance we have!
        return (DtoValue<X>)this.index.get(field.id());
    }

}
