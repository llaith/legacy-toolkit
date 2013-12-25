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
package org.llaith.toolkit.core.fault.ext;

import org.llaith.toolkit.core.fault.FaultLocation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 *
 */
public class FaultLocationFactory {

    public static FaultLocation forFileLocation(final String filename) {
        return new FaultLocation(Arrays.asList(filename));
    }

    public static FaultLocation forFileLocation(final String filename, final int linenumber) {
        return new FaultLocation(Arrays.asList(filename,":"+linenumber));
    }

    public static FaultLocation forClass(final Class<?> type) {
        return forClass(type.getName());
    }

    public static FaultLocation forClass(final String className) {
        return new FaultLocation(Arrays.asList(className));
    }

    public static FaultLocation forField(final Field field) {
        return forField(field.getDeclaringClass().getName(),field.getName());
    }

    public static FaultLocation forField(final String className, final String fieldName) {
        return new FaultLocation(Arrays.asList(className,fieldName));
    }

    public static FaultLocation forMethod(final Method method) {
        return new FaultLocation(Arrays.asList(method.getDeclaringClass().getName(),method.getName()));
    }

    public static FaultLocation forMethod(final String className, final String methodName) {
        return new FaultLocation(Arrays.asList(className,methodName+"()"));
    }

}
