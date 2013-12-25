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
package org.llaith.toolkit.core.status.impl;

import org.llaith.toolkit.core.status.StatusToken;

import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class ElapsedContext implements Iterable<ElapsedSnapshot> {

    private final Stack<ElapsedSnapshot> stack;

    public ElapsedContext(final Context stack) {
        this.stack = captureElapsedTime(stack);
    }

    private Stack<ElapsedSnapshot> captureElapsedTime(final Context stack) {

        final Stack<ElapsedSnapshot> ret = new Stack<>();

        for (final StatusToken token : stack) {
            ret.add(new ElapsedSnapshot(
                    token.context(),
                    token.timeStarted(),
                    token.timeElapsed(TimeUnit.NANOSECONDS)));
        }

        return ret;

    }

    public ElapsedSnapshot top() {
        return this.stack.peek();
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public Iterator<ElapsedSnapshot> iterator() {
        return this.stack.iterator();
    }

}
