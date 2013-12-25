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
package org.llaith.toolkit.common.etc;

import java.util.concurrent.Callable;

/**
 * From here:
 *      https://github.com/spullara/java-future-jdk8/blob/master/src/main/java/spullara/util/Lazy.java
 * TODO: Verify it works as expected.
 *
 * >>>>>>>
 *
 * Value isn't set until you ask for it and is only
 * calculated once.
 *
 * See
 * http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
 *
 * The section:
 *
 * "Fixing Double-Checked Locking using Volatile"
 *
 * This is also the same pattern that "lazy val" in Scala uses to
 * ensure once-and-only-once delayed initialization.
 */
public class Lazy<T> {
    private volatile boolean set;
    private final Callable<T> callable;
    private T value;

    public static <T> Lazy<T> lazy(Callable<T> callable) {
        return new Lazy<>(callable);
    }

    private Lazy(Callable<T> callable) {
        this.callable = callable;
    }

    public T get() {
        // This access of set requires a memory barrier
        if (!set) {
            // Now we synchronize to have only a single executor
            synchronized (this) {
                // Check again to make sure another thread didn't beat us to the lock
                if (!set) {
                    // We got this.
                    try {
                        // Evaluate the passed lambda
                        value = callable.call();
                        set = true;
                    } catch (Exception e) {
                        throw new RuntimeException("Lazy initialization failure", e);
                    }
                }
            }
        }
        return value;
    }

}
