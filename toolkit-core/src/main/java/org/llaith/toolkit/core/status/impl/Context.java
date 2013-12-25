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
package org.llaith.toolkit.core.status.impl;

import org.llaith.toolkit.core.status.StatusToken;

import java.util.Iterator;
import java.util.Stack;

/**
 *
 */
public class Context implements Iterable<StatusToken> {

    private final Stack<StatusToken> stack = new Stack<>();

    public Context() {
        super();
    }

    public Context(Context stack) {
        // take a copy of the stack
        this.stack.addAll(stack.stack);
    }

    public StatusToken peek() {
        return this.stack.peek();
    }

    public StatusToken push(final StatusToken token) {
        return this.stack.push(token);
    }

    public StatusToken popTo(final StatusToken token) {
        if (this.stack.peek().equals(token)) {
            return this.stack.pop();
        }
        throw new IllegalStateException("The head of the stack is not the requested token. Missing or duplicate pops() detected.");
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public Iterator<StatusToken> iterator() {
        return this.stack.iterator();
    }

}
