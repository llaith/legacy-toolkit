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
package org.llaith.toolkit.core.stage.listener;

import org.llaith.toolkit.core.stage.impl.StageListener;
import org.llaith.toolkit.core.status.StatusToken;

/**
 *
 */
public abstract class AbstractStageListener implements StageListener {

    @Override
    public void onStart(Object source, StatusToken status) {
        // nothing
    }

    @Override
    public void onComplete(Object source, StatusToken status) {
        // nothing
    }

    @Override
    public void onFailure(Object source, StatusToken status) {
        // nothing
    }

}
