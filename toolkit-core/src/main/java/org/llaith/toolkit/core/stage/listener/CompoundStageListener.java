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
package org.llaith.toolkit.core.stage.listener;

import org.llaith.toolkit.common.lang.ThrowableCollector;
import org.llaith.toolkit.core.stage.impl.StageListener;
import org.llaith.toolkit.core.status.StatusToken;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CompoundStageListener implements StageListener {

    private final List<StageListener> listeners = new ArrayList<>();

    public CompoundStageListener() {
        super();
    }

    public CompoundStageListener(final List<StageListener> listeners) {
        this.listeners.addAll(listeners);
    }

    public void addListener(final StageListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(final StageListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void onStart(final Object source, StatusToken status) {

        final ThrowableCollector collector = new ThrowableCollector();

        for (final StageListener it : this.listeners) {
            try {
                it.onStart(source,status);
            } catch (Throwable e) {
                collector.addThrowable(e);
            }
        }

        collector.throwIf();

    }

    @Override
    public void onComplete(final Object source, StatusToken status) {

        final ThrowableCollector collector = new ThrowableCollector();

        for (final StageListener it : this.listeners) {
            try {
                it.onComplete(source,status);
            } catch (Throwable e) {
                collector.addThrowable(e);
            }
        }

        collector.throwIf();

    }

    @Override
    public void onFailure(final Object source, StatusToken status) {

        final ThrowableCollector collector = new ThrowableCollector();

        for (final StageListener it : this.listeners) {
            try {
                it.onFailure(source,status);
            } catch (Throwable e1) {
                collector.addThrowable(e1);
            }
        }

    }

}
