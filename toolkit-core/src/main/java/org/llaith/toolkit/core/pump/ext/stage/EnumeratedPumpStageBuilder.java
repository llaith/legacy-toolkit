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
import org.llaith.toolkit.core.pump.EnumeratedPump;
import org.llaith.toolkit.core.pump.EnumeratedSource;
import org.llaith.toolkit.core.pump.Sink;
import org.llaith.toolkit.core.stage.Stage;
import org.llaith.toolkit.common.guard.Guard;

/**
 *
 */
public class EnumeratedPumpStageBuilder {

    private static class Params<T> {
        private Supplier<EnumeratedSource<T>> sourceFactory;
        private Supplier<Sink<T>> sinkFactory;
        private Supplier<EnumeratedPump<T>> pumpFactory;
    }

    public static <T> Builder1<T> configuredAs() {
        return new Builder1<>(new Params<T>());
    }

    public static abstract class AbstractBuilder<T> {
        protected final Params<T> params;
        public AbstractBuilder(final Params<T> params) {
            this.params = Guard.notNull(params);
        }
    }

    public static class Builder1<T> extends AbstractBuilder<T> {
        public Builder1(Params<T> params) {
            super(params);
        }
        public Builder2<T> from(final Supplier<EnumeratedSource<T>> sourceFactory) {
            this.params.sourceFactory = Guard.notNull(sourceFactory);
            return new Builder2<>(this.params);
        }
    }

    public static class Builder2<T> extends AbstractBuilder<T> {
        public Builder2(Params<T> params) {
            super(params);
        }
        public Builder3<T> to(final Supplier<Sink<T>> sinkFactory) {
            this.params.sinkFactory = Guard.notNull(sinkFactory);
            return new Builder3<>(this.params);
        }
    }

    public static class Builder3<T> extends AbstractBuilder<T> {
        public Builder3(Params<T> params) {
            super(params);
        }
        public BuilderOptions<T> by(final Supplier<EnumeratedPump<T>> pumpFactory) {
            this.params.pumpFactory = Guard.notNull(pumpFactory);
            return new BuilderOptions<>(this.params);
        }
    }

    public static class BuilderOptions<T> extends AbstractBuilder<T> implements Supplier<Stage> {
        public BuilderOptions(Params<T> params) {
            super(params);
        }
        public Stage get() {
            return new EnumeratedPumpStage<>(
                    this.params.sourceFactory,
                    this.params.sinkFactory,
                    this.params.pumpFactory);
        }
    }

}

