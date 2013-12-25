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
package org.llaith.toolkit.common.pattern.impl;

import org.llaith.toolkit.common.exception.ext.UncheckedException;
import org.llaith.toolkit.common.pattern.Activity;
import org.llaith.toolkit.common.lang.ThrowableCollector;

import java.util.Stack;

/**
 *
 */
public class ActivityTracker implements Activity {

    private final Stack<Activity> deactivated = new Stack<>();
    private final Stack<Activity> activated = new Stack<>();

    public <X> X track(final X x) {
        if (x instanceof Activity) {
            if (((Activity)x).isActive()) throw new IllegalArgumentException("Instance is already activated.");
            this.deactivated.push((Activity) x);
        }
        return x;
    }

    public <X, Y extends Iterable<X>> Y trackAll(final Y all) {
        for (X x : all) {
            this.track(x);
        }
        return all;
    }

    @Override
    public boolean isActive() {
        return !this.activated.isEmpty();
    }

    @Override
    public void activate() {
        if (this.isActive()) throw new UncheckedException("Instance is already activated.");
        // fail on first exception
        try {
            while (!this.deactivated.isEmpty()) {
                final Activity act = this.deactivated.pop();
                act.activate(); // contract(acquire->record): if it doesn't complete then it doesn't need close.
                this.activated.push(act);
            }
        } catch (Exception e) {
            throw UncheckedException.wrap(e);
        }
    }

    @Override
    public void deactivate() {
        if (!this.isActive()) throw new UncheckedException("Instance is already deactivated.");
        // fail on last exception
        final ThrowableCollector collector = new ThrowableCollector();

        while (!this.activated.isEmpty()) {
            try {
                final Activity act = this.activated.pop();
                act.deactivate();
                this.deactivated.push(act); // contract(record->release): we only try once to close.
            } catch (Exception e) {
                collector.addThrowable(e);
            }
        }

        collector.throwIf();

    }

}
