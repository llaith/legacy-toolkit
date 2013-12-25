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


public class Dependency<T> {

    private final T target;

    private final List<T> dependencies = new ArrayList<>();

    public Dependency(final T target) {
        this.target = Guard.notNull(target);
    }

    public Dependency(final T target, final T[] dependencies) {
        this(target, Guard.toListOrEmpty(dependencies));
    }

    public Dependency(final T target, final List<T> dependencies) {
        this.target = Guard.notNull(target);

        this.dependencies.addAll(Guard.notNull(dependencies));
    }

    public T target() {
        return this.target;
    }

    public List<T> dependencies() {
        return this.dependencies;
    }

    @Override
    public String toString() {
        return this.target+" <- "+this.dependencies;
    }

}
