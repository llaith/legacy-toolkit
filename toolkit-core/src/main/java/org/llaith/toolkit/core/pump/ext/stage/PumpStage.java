package org.llaith.toolkit.core.pump.ext.stage;

import com.google.common.base.Supplier;
import org.llaith.toolkit.common.exception.ext.UncheckedException;
import org.llaith.toolkit.core.pump.Pump;
import org.llaith.toolkit.core.pump.Sink;
import org.llaith.toolkit.core.pump.Source;
import org.llaith.toolkit.core.stage.Stage;
import org.llaith.toolkit.core.status.StatusToken;
import org.llaith.toolkit.common.guard.Guard;

/**
 *
 */
public class PumpStage<T> implements Stage {

    private final Supplier<Source<T>> sourceFactory;
    private final Supplier<Sink<T>> sinkFactory;
    private final Supplier<Pump<T>> pumpFactory;

    public PumpStage(final Supplier<Source<T>> sourceFactory, final Supplier<Sink<T>> sinkFactory, final Supplier<Pump<T>> pumpFactory) {
        this.sourceFactory = Guard.notNull(sourceFactory);
        this.sinkFactory = Guard.notNull(sinkFactory);
        this.pumpFactory = Guard.notNull(pumpFactory);
    }

    @Override
    public void execute(final StatusToken status) {
        try (
                final Source<T> source = sourceFactory.get();
                final Sink<T> sink = sinkFactory.get();
                final Pump<T> pump = pumpFactory.get()) {

            pump.drain(source,sink);

        } catch (Exception e) {
            throw UncheckedException.wrap(e);
        }
    }

}
