package org.llaith.toolkit.core.stage.builder;

import com.google.common.base.Supplier;
import org.llaith.toolkit.core.stage.Flow;
import org.llaith.toolkit.core.stage.Stage;
import org.llaith.toolkit.core.status.StatusControl;
import org.llaith.toolkit.common.guard.Guard;

/**
 *
 */
public class FlowBuilder implements Supplier<Flow> {

    public static AssignProgressControl forNewFlow() {
        return new AssignProgressControl(new Setup());
    }

    public static class AssignProgressControl {
        private final Setup setup;
        public AssignProgressControl(Setup setup) {
            this.setup = setup;
        }
        public AssignSeries usingStatusControl(final Supplier<StatusControl> progress) {
            this.setup.progress = Guard.notNull(progress);
            return new AssignSeries(this.setup);
        }
    }

    public static class AssignSeries {
        private final Setup setup;
        public AssignSeries(Setup setup) {
            this.setup = setup;
        }
        public FlowBuilder withRoot(final Supplier<Stage> root) {
            this.setup.root = Guard.notNull(root);
            return new FlowBuilder(this.setup);
        }
    }

    private static class Setup {
        private Supplier<StatusControl> progress;
        private Supplier<Stage> root;
    }

    private final Setup setup;

    public FlowBuilder(final Setup setup) {
        this.setup = setup;
    }

    @Override
    public Flow get() {
        return new Flow(
                this.setup.progress,
                this.setup.root);
    }

}
