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
package org.llaith.toolkit.common.pattern.impl;

import org.llaith.toolkit.common.pattern.ResourceLoan;

/**
 * A very simple partial implementation for resources which merely pass
 * on a pre-set target rather than retrieving one itself.
 */
public abstract class AbstractResourceLoan<T> implements ResourceLoan<T> {

    private final T target;

    protected AbstractResourceLoan(T target) {
        this.target = target;
    }

    @Override
    public T target() {
        return this.target;
    }

}
