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
package org.llaith.toolkit.core.pump.impl;

import org.llaith.toolkit.core.pump.Stream;

import java.util.Iterator;

/**
 *
 */
public class IterableStream<T> implements Stream<T> {

    private final Iterable<T> view;

    private Iterator<T> iterator;

    public IterableStream(final Iterable<T> view) {
        this.view = view;
    }

    @Override
    public T read() throws RuntimeException {
        if (this.iterator == null) this.iterator = view.iterator();
        return iterator.next();
    }

    @Override
    public void close() throws RuntimeException {
        this.iterator = null;
    }

}
