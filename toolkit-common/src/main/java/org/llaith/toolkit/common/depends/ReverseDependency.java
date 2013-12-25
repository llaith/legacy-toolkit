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
package org.llaith.toolkit.common.depends;

import org.llaith.toolkit.common.guard.Guard;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <S>
 */
public final class ReverseDependency<S> {

    private final Dependency<S> dependency;

    private final List<S> reverseDependants = new ArrayList<>();

    public ReverseDependency(final Dependency<S> dependency) {
        this.dependency = Guard.notNull(dependency);
    }

    public Dependency<S> dependency() {
        return this.dependency;
    }

    public List<S> reverseDependants() {
        return this.reverseDependants;
    }

}
