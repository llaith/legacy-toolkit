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
package org.llaith.toolkit.common.util.lang;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Class;
import java.lang.Throwable;

public class ExceptionUtil {

    public static <T extends Throwable> T findCause(final Class<T> klass, final Throwable t) {

        Throwable srch = t;

        while (srch != null) {
            if (klass.getName().equals(srch.getClass().getName())) return klass.cast(srch);
            srch = srch.getCause();
        }

        return null;

    }

    public static <T extends Throwable> T popStackTrace(final int trim, final T throwable) {

        final StackTraceElement[] originalTrace = throwable.getStackTrace();
        final StackTraceElement[] replacementTrace = new StackTraceElement[originalTrace.length - trim];

        System.arraycopy(originalTrace,trim,replacementTrace,0,replacementTrace.length);

        throwable.setStackTrace(replacementTrace);

        return throwable;

    }

    public static String stackTraceToString(final Throwable t) {
        StringWriter out = new StringWriter();
        t.printStackTrace(new PrintWriter(out));
        return out.toString();
    }

}
