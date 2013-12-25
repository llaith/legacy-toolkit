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
package org.llaith.toolkit.common.util.reflection;

import org.llaith.toolkit.common.guard.Guard;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * There are more of these in other projects to go in.
 */
public class GenericUtil {

    public static Class<?> concreteElementTypeOfCollection(final Type collectionType) {
        Guard.notNull(collectionType);

        if (!(collectionType instanceof ParameterizedType)) return null;

        final ParameterizedType pt = (ParameterizedType) collectionType;
        if ((pt.getActualTypeArguments() == null) || (pt.getActualTypeArguments().length < 1)) return null;
        final Type arg = pt.getActualTypeArguments()[0];

        if (!(arg instanceof Class)) return null;
        return (Class<?>) arg;
    }

}
