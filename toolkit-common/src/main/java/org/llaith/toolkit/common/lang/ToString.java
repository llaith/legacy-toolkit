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
