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

/**
 *
 */
public class PackageUtil {

    public static boolean hasPackage(final String type) {
        return (type.indexOf('.') > 0);
    }

    public static String packageFromName(final String type) {
        return !hasPackage(type) ? "" : type.substring(0,type.lastIndexOf('.'));
    }

    // this will break for canonical names
    public static String unqualifyClassName(final String type) {
        return !hasPackage(type) ? type : type.substring(type.lastIndexOf('.') + 1);
    }


    public static String unqualifyClass(final Class<?> klass) {
        if (klass.isAnonymousClass()) return unqualifyClassName(klass.getName());
        return klass.getSimpleName();
    }

    public static String outerClassNameFor(final Class<?> klass) {
        final String fullName = Guard.notNull(klass.getName());

        final int pos = fullName.indexOf("$");
        if (pos >= 0) {
            return fullName.substring(0,pos);
        }

        return fullName;
    }

}
