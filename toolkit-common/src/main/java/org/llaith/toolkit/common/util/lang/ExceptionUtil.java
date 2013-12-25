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
