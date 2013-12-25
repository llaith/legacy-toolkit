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
