package org.llaith.toolkit.core.stage;

import com.google.common.base.Supplier;
import org.llaith.toolkit.core.status.StatusControl;
import org.llaith.toolkit.core.status.StatusToken;

/**
 *
 */
public class Flow {

    private final Supplier<StatusControl> factory;
    private final Supplier<Stage> stage;

    public Flow(final Supplier<StatusControl> factory, final Supplier<Stage> stage) {
        this.factory = factory;
        this.stage = stage;
    }

    public void execute() {

        final StatusControl control = this.factory.get();
        final Stage stage = this.stage.get();

        final StatusToken token = control.startNested(stage);
        try {
            stage.execute(token);
            control.markNestedOk(token);
        } catch (RuntimeException e) {
            control.markNestedFailed(token);
            throw e;
        }

    }

}
