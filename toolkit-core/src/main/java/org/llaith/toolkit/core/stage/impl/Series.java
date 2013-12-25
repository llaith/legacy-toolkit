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
package org.llaith.toolkit.core.stage.impl;

import com.google.common.base.Supplier;
import org.llaith.toolkit.common.ident.Identified;
import org.llaith.toolkit.core.stage.Stage;
import org.llaith.toolkit.core.stage.builder.SeriesBuilder;
import org.llaith.toolkit.core.stage.listener.CompoundStageListener;
import org.llaith.toolkit.core.status.StatusToken;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Series implements Stage, Identified {

    public static SeriesBuilder.Start startsAs(final String ident) {
        return new SeriesBuilder.Start(new SeriesBuilder(ident));
    }

    public static SeriesBuilder.Start continuesFrom(SeriesBuilder builder) {
        return new SeriesBuilder.Start(builder);
    }

    private final String identifier;
    private final CompoundStageListener stageListener;
    private final List<Supplier<Stage>> stageFactories = new ArrayList<>();

    public Series(final String identifier, final List<Supplier<Stage>> stageFactories) {
        this(identifier,stageFactories,new ArrayList<StageListener>());
    }

    protected Series(final String identifier, final List<Supplier<Stage>> stageFactories, final List<StageListener> listeners) {
        this.identifier = identifier;
        this.stageFactories.addAll(stageFactories);
        this.stageListener = new CompoundStageListener(listeners);
    }

    public Series withStageListeners(final List<StageListener> listeners) {
        return new Series(this.identifier,this.stageFactories,listeners);
    }

    @Override
    public void execute(final StatusToken status) {
        // can always wrap in individual StageControls if specific handling wanted
        for (Supplier<Stage> stage : this.stageFactories) {
            this.execute(
                    status,
                    stage.get());
        }
    }

    private Stage execute(final StatusToken token, final Stage stage) {
        final StatusToken sub = token.startNested(stage);

        try {
            this.stageListener.onStart(this,sub);
            stage.execute(sub);
            this.stageListener.onComplete(this,sub);
            token.markNestedOk(sub);
        } catch (RuntimeException e) {
            token.markNestedFailed(sub);
            this.stageListener.onFailure(this,sub);
            throw e;
        }

        return stage;
    }

    @Override
    public String identifier() {
        return this.identifier;
    }

}
