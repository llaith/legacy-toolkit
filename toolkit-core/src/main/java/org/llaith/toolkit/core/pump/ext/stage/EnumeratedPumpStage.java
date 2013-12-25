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
package org.llaith.toolkit.core.pump.ext.stage;

import com.google.common.base.Supplier;
import org.llaith.toolkit.common.exception.ExceptionHandler;
import org.llaith.toolkit.common.exception.ext.UncheckedException;
import org.llaith.toolkit.core.pump.EnumeratedPump;
import org.llaith.toolkit.core.pump.EnumeratedSource;
import org.llaith.toolkit.core.pump.Sink;
import org.llaith.toolkit.core.stage.Stage;
import org.llaith.toolkit.core.status.StatusToken;
import org.llaith.toolkit.common.guard.Guard;

/**
 *
 */
public class EnumeratedPumpStage<T> implements Stage {

    private final Supplier<EnumeratedSource<T>> sourceFactory;
    private final Supplier<Sink<T>> sinkFactory;
    private final Supplier<EnumeratedPump<T>> pumpFactory;

    public EnumeratedPumpStage(final Supplier<EnumeratedSource<T>> sourceFactory, final Supplier<Sink<T>> sinkFactory,
                               final Supplier<EnumeratedPump<T>> pumpFactory) {
        this.sourceFactory = Guard.notNull(sourceFactory);
        this.sinkFactory = Guard.notNull(sinkFactory);
        this.pumpFactory = Guard.notNull(pumpFactory);
    }

    @Override
    public void execute(final StatusToken status) {
        try (
                final EnumeratedSource<T> source = sourceFactory.get();
                final Sink<T> sink = sinkFactory.get();
                final EnumeratedPump<T> pump = pumpFactory.get()) {

            pump.drain(source,sink);

        } catch (Exception e) {
            throw UncheckedException.wrap(e);
        }
    }

}
