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
package org.llaith.toolkit.common.lang;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.lang.reflect.Field;

/**
 * Consider using Guava's Objects.toStringHelper instead. This is the very lazy
 * option, but has some value in cases where the trade-off (performance vs
 * forgetting to change the tostring) is worth it.
 */
public class ToString {

    public static String byReflection(final Object o) {
        // This is just easier and performance doesn't matter
        return (new ReflectionToStringBuilder(o) {
            protected boolean accept(Field f) {
                return super.accept(f) && !f.isAnnotationPresent(ToStringIgnore.class);
            }
        }).toString();
    }


}
