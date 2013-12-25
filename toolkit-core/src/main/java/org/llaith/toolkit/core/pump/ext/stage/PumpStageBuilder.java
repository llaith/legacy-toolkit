package org.llaith.toolkit.core.pump.ext.stage;

import com.google.common.base.Supplier;
import org.llaith.toolkit.core.pump.Pump;
import org.llaith.toolkit.core.pump.Sink;
import org.llaith.toolkit.core.pump.Source;
import org.llaith.toolkit.core.stage.Stage;
import org.llaith.toolkit.common.guard.Guard;

/**
 *
 */
public class PumpStageBuilder {

    private static class Params<T> {
        private Supplier<Source<T>> sourceFactory;
        private Supplier<Sink<T>> sinkFactory;
        private Supplier<Pump<T>> pumpFactory;
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
        public Builder2<T> from(final Supplier<Source<T>> sourceFactory) {
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
        public BuilderOptions<T> by(final Supplier<Pump<T>> pumpFactory) {
            this.params.pumpFactory = Guard.notNull(pumpFactory);
            return new BuilderOptions<>(this.params);
        }
    }


    public static class BuilderOptions<T> extends AbstractBuilder<T> implements Supplier<Stage> {
        public BuilderOptions(Params<T> params) {
            super(params);
        }
        public Stage get() {
            return new PumpStage<>(
                    this.params.sourceFactory,
                    this.params.sinkFactory,
                    this.params.pumpFactory);
        }
    }

}

