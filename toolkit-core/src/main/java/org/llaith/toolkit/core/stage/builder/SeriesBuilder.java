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
package org.llaith.toolkit.core.stage.builder;

import com.google.common.base.Supplier;
import org.llaith.toolkit.core.stage.Stage;
import org.llaith.toolkit.core.stage.impl.Series;
import org.llaith.toolkit.core.stage.impl.StageListener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SeriesBuilder implements Supplier<Stage> {

    public static class Start {
        public final SeriesBuilder builder;

        public Start(SeriesBuilder builder) {
            this.builder = builder;
        }

        public Start using(final StageListener listener) {
            this.builder.listeners.add(listener);
            return this;
        }

        public SeriesBuilder firstDo(final Supplier<Stage> stage) {
            this.builder.stages.add(stage);
            return this.builder;
        }

        public Conditional firstConditionallyDo(final Supplier<Stage> stage) {
            return new Conditional(this.builder,stage);
        }

    }

    public static class Conditional {

        public final SeriesBuilder builder;
        public final List<Supplier<Stage>> stages = new ArrayList<>();

        public Conditional(final SeriesBuilder builder, final Supplier<Stage> stage) {
            this.builder = builder;
            this.stages.add(stage);
        }

        public Conditional andThenDo(final Supplier<Stage> stage) {
            this.stages.add(stage);
            return new Conditional(this.builder,stage);
        }

        public SeriesBuilder onlyWhen(final boolean trigger) {
            if (trigger) this.builder.stages.addAll(this.stages);
            this.builder.currentConditional = null;
            return this.builder;
        }

    }

    private final String ident;
    private final List<StageListener> listeners = new ArrayList<>();
    private final List<Supplier<Stage>> stages = new ArrayList<>();

    private Conditional currentConditional;

    public SeriesBuilder(final String ident) {
        this.ident = ident;
    }

    public SeriesBuilder thenDo(final Supplier<Stage> stage) {
        this.stages.add(stage);
        return this;
    }

    public Conditional thenConditionallyDo(final Supplier<Stage> stage) {
        if (this.currentConditional != null) throw new RuntimeException(String.format(
                "Misconfigured SeriesBuilder. Unapplied conditional with the following stages: %s.",
                this.currentConditional.stages));

        this.currentConditional = new Conditional(this,stage);

        return this.currentConditional;
    }

    @Override
    public Series get() {

        if (this.currentConditional != null) throw new RuntimeException(String.format(
                "Misconfigured SeriesBuilder. Unapplied conditional with the following stages: %s.",
                this.currentConditional.stages));

        return new Series(
                this.ident,
                this.stages).withStageListeners(this.listeners);

    }

}

