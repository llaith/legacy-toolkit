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
package org.llaith.toolkit.core.status.impl;

import org.llaith.toolkit.core.status.ProgressToken;
import org.llaith.toolkit.core.status.StatusLevel;

/**
 *
 */
public class ProgressTokenImpl implements ProgressToken {

    private final StatusLogger logger;
    private final Context context;
    private final Object id;
    private final StatusLevel level;

    public ProgressTokenImpl(final StatusLogger logger, final Context context,
                             final Object id, final StatusLevel level) {
        this.logger = logger;
        this.context = context;
        this.id = id;
        this.level = level;
    }

    @Override
    public ProgressToken update(final String heading, final int total, final int count, final String message) {

        this.logger.reportProgress(
                new ElapsedContext(this.context),
                this.id,
                this.level,
                heading,
                total,
                count,
                message);


        return this;
    }

}
