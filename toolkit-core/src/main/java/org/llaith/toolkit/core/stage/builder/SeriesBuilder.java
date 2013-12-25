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

