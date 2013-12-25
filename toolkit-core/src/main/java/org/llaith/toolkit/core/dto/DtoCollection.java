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


import org.llaith.toolkit.common.meta.MetadataContainer;
import org.llaith.toolkit.common.meta.MetadataDelegate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 *
 * Why not a TransactionalList? A List cannot practically track it's additions
 * and deletions, a set can. Sets and Maps are 'pure' collections. A list, in
 * this context, can be thought of as an ordered view of a set. A view which is
 * recreated on each change that is derived from the set of currentValues plus a
 * Comparator.
 *
 * Equivalence: It's equivalent if it's current currentValues are equal. It's the
 * responsibility of code that knows it is DtoObject to check it's
 * isStale() etc. In all ways possible, it should act as though it is the
 * 'current' Set<T>.
 *
 * It is a deliberate restriction that the DtoCollections only take DtoObjects
 * rather than anything. It's to ensure that 'isStale()' etc messages are passed
 * down properly. It would be ok for collections of primitives, but not of any
 * other objects. For that reason, it's safer to keep it to DtoObject's, and have
 * primitive wrapping dtos. It's should be very rare in any case, as these are
 * designed to present database entities and they will have and id as well as
 * some data fields in any case.
 *
 * Design: Inspired by the idea that we only need collections and a lua-style
 * 'table' object which can be either a map or an 'object'. Originally these
 * were based on Sets and Maps, but that had to change because the interfaces
 * are unsafe for the TransactionalAspect. Particularly concepts like 'retain'
 * are meaningless, and maps really act like two sets together with undefined
 * semantics for the tracking the changes of the keys.
 *
 * NEW: in future, model Map as Collection of DtoEntry<String,V extends Dto<?>.
 * Dto's as keys is far more complex, and it's not the way the DTOs should be used.
 *
 * Also description that the Collection is unordered.
 *
 */
public class DtoCollection<T extends DtoObject<T>> implements Dto<DtoCollection<T>>, MetadataContainer, Iterable<T> {

    private final MetadataDelegate metadatas = new MetadataDelegate();

    private final Set<T> current = new HashSet<>();
    private final Set<T> original = new HashSet<>();

    private final Set<T> dirtyDeletions = new HashSet<>();
    private final Set<T> dirtyAdditions = new HashSet<>();
    private final Set<T> staleDeletions = new HashSet<>();
    private final Set<T> staleAdditions = new HashSet<>();

    public DtoCollection() {
        super();
    }

    public DtoCollection(final Collection<T> collection) {
        this.current.addAll(collection);
        this.original.addAll(collection);
    }

    public int size() {
        return this.current.size();
    }

    public boolean isEmpty() {
        return this.current.isEmpty();
    }

    public boolean contains(final T t) {
        return this.current.contains(t);
    }

    public DtoCollection<T> add(final T t) {

        this.current.add(t);

        if (this.dirtyDeletions.contains(t)) this.dirtyDeletions.remove(t);
        else this.dirtyAdditions.add(t);

        return this;
    }

    public DtoCollection<T> addAll(final Collection<? extends T> it) {

        for (T t : it) {
            this.add(t);
        }

        return this;

    }

    public DtoCollection<T> remove(final T t) {

        this.current.remove(t);

        if (this.dirtyAdditions.contains(t)) this.dirtyAdditions.remove(t);
        else this.dirtyDeletions.add(t);

        return this;
    }

    public DtoCollection<T> removeAll(final Collection<? extends T> it) {

        for (T t : it) {
            this.remove(t);
        }

        return this;
    }

    public DtoCollection<T> clear() {

        this.removeAll(new HashSet<>(this.current));

        return this;
    }

    public DtoCollection<T> set(Collection<? extends T> it) {
        // the clear/addAll do all necessary tracking.
        this.clear();

        this.addAll(it);

        return this;
    }

    public void reset(final Collection<? extends T> it) {

        this.staleAdditions.clear();
        for (final T item : it) {
            if (!this.original.contains(item)) {
                this.original.add(item);
                if (this.dirtyAdditions.contains(item)) this.dirtyAdditions.remove(item);
                else this.staleAdditions.add(item);
            }
        }

        this.staleDeletions.clear();
        for (final T item : this.original) {
            if (!it.contains(item)) {
                this.original.remove(item);
                if (this.dirtyDeletions.contains(item)) this.dirtyDeletions.remove(item);
                else this.staleDeletions.add(item);
            }
        }

        this.current.clear();
        this.current.addAll(this.original);

    }

    public Set<T> getAll() {
        return new HashSet<>(current);
    }

    public Set<T> getDirtyDeletions() {
        return new HashSet<>(dirtyDeletions);
    }

    public Set<T> getDirtyAdditions() {
        return new HashSet<>(dirtyAdditions);
    }

    public Set<T> getStaleDeletions() {
        return new HashSet<>(staleDeletions);
    }

    public Set<T> getStaleAdditions() {
        return new HashSet<>(staleAdditions);
    }

    @Override
    public boolean isDirty() {

        if (this.hasDirtyCollections()) return true;

        for (final T t : this.current) {
            if (t.isDirty()) return true;
        }

        return false;
    }

    @Override
    public boolean isStale() {

        if (this.hasStaleCollections()) return true;

        for (final T t: this.current) {
            if (t.isStale()) return true;
        }

        return false;
    }

    @Override
    public boolean isConflicted() {
        return this.isDirty() && this.isStale();
    }

    @Override
    public DtoCollection<T> acceptChanges() {
        for (final T t : this.current) {
            t.acceptChanges();
        }

        this.original.clear();
        this.original.addAll(this.current);

        this.clearFlags();

        return this;
    }

    @Override
    public DtoCollection<T> cancelChanges() {

        for (final T t : this.current) {
            t.cancelChanges();
        }

        this.current.clear();
        this.current.addAll(this.original);

        this.clearFlags();

        return this;
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
    public Iterator<T> iterator() {
        return this.current.iterator();
    }

    @Override
    public final boolean equals(final Object o) {
        return super.equals(o);
    }

    @Override
    public final int hashCode() {
        return super.hashCode();
    }

    private boolean hasDirtyCollections() {
        return (this.dirtyAdditions.size() + this.dirtyDeletions.size()) > 0;
    }

    private boolean hasStaleCollections() {
        return (this.staleAdditions.size() + this.staleDeletions.size()) > 0;
    }

    private void clearFlags() {
        this.dirtyAdditions.clear();
        this.dirtyDeletions.clear();
        this.staleAdditions.clear();
        this.staleDeletions.clear();
    }

}
