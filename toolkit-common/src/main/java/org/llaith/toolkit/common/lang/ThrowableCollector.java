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

import com.google.common.base.Supplier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThrowableCollector {

    public static Throwable unwrap(Class<? extends Throwable> hunt, final Throwable t) {

        final ThrowableCollector collector = new ThrowableCollector();

        Throwable curr = t;
        while (true) {
            if (curr == null) return null;
            if (!(hunt.isAssignableFrom(curr.getClass()))) {
                return collector.copyTo(curr);
            }
            collector.collectFrom(curr);
            curr = curr.getCause();
        }

    }

    private final List<Throwable> suppressed = new ArrayList<>();
    private Throwable first;

    public ThrowableCollector addThrowable(final Throwable t) {
        this.suppressed.add(t);
        if (this.first == null) this.first = t;
        return this;
    }

    public <X extends Throwable> X putThrowable(final X t) {
        this.addThrowable(t);
        return t;
    }

    public boolean hasSuppressedThrowables() {
        return !this.suppressed.isEmpty();
    }

    public void throwIf() {
        if (!this.hasSuppressedThrowables()) return;

        if (this.first instanceof RuntimeException) throw copyTo((RuntimeException)this.first);

        throw copyTo(new RuntimeException("There are suppressed throwables"));
    }

    public <X extends Throwable> void throwIfWith(final Supplier<X> throwableSupplier) throws X {
        if (!this.hasSuppressedThrowables()) return;

        throw copyTo(throwableSupplier.get());
    }

    public <X extends Throwable> X copyTo(final X throwable) {
        for (final Throwable st : this.suppressed) {
            // simplifies 'this.first' handling and stops cycles generally
            if (st != throwable) throwable.addSuppressed(st);
        }
        return throwable;
    }

    public void collectFrom(final Throwable throwable) {
        this.suppressed.addAll(Arrays.asList(throwable.getSuppressed()));
    }

}
