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
