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

import org.llaith.toolkit.common.exception.ext.UncheckedException;
import org.llaith.toolkit.core.stage.Stage;
import org.llaith.toolkit.core.status.StatusToken;

/**
 * This is a stage delegate, not a nested stage, it does not announce itself through
 * the status tracking.
 */
public class StageControl implements Stage {

    private final StageListener listener;
    private final Stage delegate;

    public StageControl(StageListener listener, final Stage delegate) {
        this.listener = listener;
        this.delegate = delegate;
    }

    @Override
    public void execute(StatusToken status) {
        this.listener.onStart(this,status);
        try {
            this.delegate.execute(status);
            this.listener.onComplete(this,status);
        } catch (Exception e) {
            try {
                this.listener.onFailure(this,status);
            } catch (Exception e1) {
                e.addSuppressed(e1);
            }
            throw UncheckedException.wrap(e);
        }
    }

}
