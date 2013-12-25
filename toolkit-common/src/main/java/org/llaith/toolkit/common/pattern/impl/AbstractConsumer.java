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

import org.llaith.toolkit.common.pattern.Consumer;
import org.llaith.toolkit.common.guard.Guard;

/**
 * Idea from JDK 8 Consumer.
 */
public abstract class AbstractConsumer<T> implements Consumer<T> {

    @Override
    public abstract void accept(final T t);

    public Consumer<T> andThen(final Consumer<? super T> after) {
        Guard.notNull(after);
        return new Consumer<T>() {
            @Override
            public void accept(final T t) {
                AbstractConsumer.this.accept(t);
                after.accept(t);
            }
        };
    }

}
