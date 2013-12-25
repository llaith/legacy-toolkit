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
package org.llaith.toolkit.common.lang;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Not an ARM replacement. If wanting an ARM replacement, remember to use Guava's Closer.
 */
public class AutoCloser implements AutoCloseable {

    private final Deque<AutoCloseable> stack = new ArrayDeque<>();

    public <X> X track(final X o) {
        if (o instanceof AutoCloseable) this.stack.push((AutoCloseable)o);
        return o;
    }

    public <X,Y extends Iterable<X>> Y trackAll(final Y all) {
        for (X x : all) {
            this.track(x);
        }
        return all;
    }

    @Override
    public void close() throws RuntimeException {

        // fail on last exception
        final ThrowableCollector collector = new ThrowableCollector();

        while (!stack.isEmpty()) {
            AutoCloseable closeable = stack.pop();
            try {
                closeable.close();
            } catch (Throwable e) {
                collector.addThrowable(e);
            }
        }

        collector.throwIf();

    }

}

