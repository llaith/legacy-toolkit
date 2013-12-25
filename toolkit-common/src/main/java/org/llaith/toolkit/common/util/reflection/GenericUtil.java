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
