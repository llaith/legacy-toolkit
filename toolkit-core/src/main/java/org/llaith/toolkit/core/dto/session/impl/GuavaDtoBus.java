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
package org.llaith.toolkit.core.dto.session.impl;

import com.google.common.eventbus.EventBus;
import org.llaith.toolkit.core.dto.DtoObject;
import org.llaith.toolkit.core.dto.session.DtoBus;
import org.llaith.toolkit.core.dto.session.DtoRefreshEvent;

/**
 * An implementation of the DtoBus using Guava's EventBus.
 * The registering DtoObjects need to have their listening
 * methods marked with the com.google.common.eventbus.Subscribe
 * annotation.
 */
public class GuavaDtoBus implements DtoBus {

    private final EventBus eventBus = new EventBus();

    @Override
    public void register(final DtoObject<?> self) {
        this.eventBus.register(self);
    }

    @Override
    public void unregister(final DtoObject<?> self) {
        this.eventBus.unregister(self);
    }

    @Override
    public void post(final DtoRefreshEvent<?> event) {
        this.eventBus.post(event);
    }

}
