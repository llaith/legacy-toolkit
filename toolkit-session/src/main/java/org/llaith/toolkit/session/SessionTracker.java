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
package org.llaith.toolkit.session;

import org.llaith.toolkit.exception.SuppressedThrowableCollector;

import java.util.Stack;

/**
 *
 */
public class SessionTracker implements Session {

    private final Stack<Session> closed = new Stack<>();
    private final Stack<Session> open = new Stack<>();

    public <X> X track(X o) {
        if (o instanceof Session) this.closed.push((Session) o);
        return o;
    }

    public boolean isOpen() {
        return !this.open.isEmpty();
    }

    @Override
    public void open() throws SessionOpenException {
        // fail on first exception
        try {
            while (!this.closed.isEmpty()) {
                final Session aware = this.closed.pop();
                aware.open(); // contract(acquire->record): if it doesn't complete then it doesn't need close.
                this.open.push(aware);
            }
        } catch (Throwable t) {
            throw new SessionOpenException("Failed to acquire resources",t);
        }
    }

    @Override
    public void close() throws ResourceCloseException {
        // fail on last exception
        final SuppressedThrowableCollector collector = new SuppressedThrowableCollector();

        while (!this.open.isEmpty()) {
            try {
                final Session aware = this.open.pop();
                this.closed.push(aware); // contract(record->release): we only try once to close.
                aware.close();
            } catch (Throwable e) {
                collector.addThrowable(e);
            }
        }

        collector.throwIf(new ResourceCloseException("There are multiple resource release failures."));
    }

}
