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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 03/04/12
 * Time: 11:59
 */
public class ClassStructureUtil {

    public static Set<Class<?>> findSuperTypes(final Class<?> klass) {
        return findSuperInterfaces(findSuperClasses(Guard.notNull(klass)));
    }

    public static Set<Class<?>> findSuperClasses(final Class<?> klass) {
        final Set<Class<?>> s = new HashSet<>();
        Class<?> c = klass;
        while (c != null) {
            s.add(c);
            c = c.getSuperclass();
        }
        return s;
    }

    public static Set<Class<?>> findSuperInterfaces(final Set<Class<?>> set) {
        final Stack<Class<?>> in = new Stack<>();
        in.addAll(set);
        final Set<Class<?>> out = new HashSet<>();
        while (!in.empty()) {
            final Class<?> c = in.pop();
            out.add(c);
            if (c.getInterfaces() != null) Collections.addAll(in,c.getInterfaces());
        }
        return out;
    }

}
