/*
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
 */
package org.llaith.toolkit.core.dto.session;


import com.google.common.base.Supplier;
import org.llaith.toolkit.core.dto.DtoObject;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * <P>
 * The DtoSession is used to place built (identified) dto instances to be synchronised with each other.
 * The synchronization occurs on the acceptChanges/cancelChanges invocation, which means it is good practice
 * to call cancelChanges even if you plan to discard the dto, although this is not strictly necessary.
 * </P>
 * <pre>
 *     SomeDto dto1 = session.addDto(dtoBuilder.build());
 *     SomeDto dto2 = session.addDto(dtoBuilder.build());
 *
 *     dto1.setSomeField(value);
 *
 *     Asset.Equals(dto1.getSomeField(),dto2.getSomeField());
 * </pre>
 */
public class DtoSession {

    private final Supplier<DtoBus> busFactory;

    private final HashMap<DtoObject<?>.Identity,Set<DtoObject<?>>> index = new HashMap<>();
    private final HashMap<DtoObject<?>.Identity,DtoBus> buses = new HashMap<>();

    private final Set<DtoObject<?>> root = new HashSet<>();

    private final Deque<Set<DtoObject<?>>> stack;
    private final Deque<Object> bookmarks;

    /**
     * Constructs a new DtoSession.
     *
     * @param busFactory a Supplier for creating new EventBus instances for
     *                   each identified Dto.
     */
    public DtoSession(final Supplier<DtoBus> busFactory) {
        this.busFactory = busFactory;

        this.stack = new LinkedList<>();
        this.bookmarks = new LinkedList<>();

        this.stack.add(this.root);
    }

    /**
     * Pushes another layer for DtoObject instances onto the stack. It is presumed
     * that each layer represents some sort of 'view' of an application. It is
     * more convenient to discard an entire layer of DtoObjects en-mass then to
     * worry about discarding and un-registering them one by one.
     *
     * @param bookmark the identifier used as the bookmark in order to pop
     *                 back to that point when finished.
     */
    public Object push(final Object bookmark) {
        this.bookmarks.push(bookmark);

        this.stack.push(new HashSet<DtoObject<?>>());

        return bookmark;
    }

    /**
     * Rewind (pop) the stack back to the given bookmark, un-registering all DtoObjects
     * stored in the popped layers from their EventBuses. If the bookmark cannot be found,
     * the stack is unwound all the way and a new black layer is reset onto the bottom.
     *
     * @param bookmark the identifier that marks the point at which we want to
     *                 rewind the stack to.
     */
    public Object pop(final Object bookmark) {
        while (true) {
            if ((this.bookmarks.isEmpty()) || (this.bookmarks.peek().equals(bookmark))) break;
            this.bookmarks.pop();
            this.stack.pop().clear();
        }

        if (this.stack.size() < 1) this.stack.add(this.root); // maybe clear it? or ditch the idea?

        return bookmark;
    }

    /**
     * Add a DtoObject to the session. A reference to the DtoObject will be stored in the
     * current layer and the DtoObject will be registered against the correct EventBus. If
     * it is the first dto with a given Identity, a new EventBus will be created for it.
     *
     * @param dto the DtoObject to add to the current layer.
     * @param <X> The type of the DtoObject
     * @return the dto object that was passed in (for chaining).
     */
    public <X extends DtoObject<X>> X addDto(final X dto) {

        if (dto.isNew()) throw new IllegalStateException("Cannot add new instances to a session.");

        this.addDtoToIndex(dto);

        this.addDtoToBus(dto);

        this.stack.peek().add(dto);

        return dto;
    }

    /**
     * Remove a DtoObject from the session. A reference to the DtoObject will be stored in
     * the current layer and the DtoObject will be registered against the correct EventBus.
     * If it is the last dto with a given Identity, the EventBus will be cleaned up.
     *
     * @param dto tje DtoBJect to remove from the current layer.
     * @param <X> The type of the DtoObject
     * @return the dto object that was passed in (for chaining).
     */
    public <X extends DtoObject<X>> X removeDto(final X dto) {

        this.removeDtoFromIndex(dto);

        this.removeDtoFromBus(dto);

        this.stack.peek().remove(dto);

        return dto;
    }

    /**
     * Clears all the current layers dtos.
     */
    public void clear() {
        this.stack.peek().clear();
    }

    private void addDtoToIndex(final DtoObject<?> dto) {
        if (!this.index.containsKey(dto.identity())) this.index.put(
                dto.identity(),
                new HashSet<DtoObject<?>>());

        this.index.get(dto.identity()).add(dto);
    }

    private void removeDtoFromIndex(final DtoObject<?> dto) {
        if (this.index.containsKey(dto.identity())) {
            final Set<DtoObject<?>> dtos = this.index.get(dto.identity());
            dtos.remove(dto);
            if (dtos.size() < 1) this.index.remove(dto.identity());
        }
    }

    private void addDtoToBus(final DtoObject<?> dto) {
        if (!this.buses.containsKey(dto.identity())) this.buses.put(
                dto.identity(),
                this.busFactory.get());

        dto.register(this.buses.get(dto.identity()));
    }

    private void removeDtoFromBus(final DtoObject<?> dto) {
        if (this.buses.containsKey(dto.identity())) {

            dto.unregister(this.buses.get(dto.identity()));

            if (!this.index.containsKey(dto.identity())) this.buses.remove(dto.identity());
        }
    }

}
