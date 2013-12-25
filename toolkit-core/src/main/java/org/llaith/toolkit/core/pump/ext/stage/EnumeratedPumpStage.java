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
