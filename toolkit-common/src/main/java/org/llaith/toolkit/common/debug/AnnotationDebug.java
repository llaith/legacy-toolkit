package org.llaith.toolkit.common.debug;

import org.llaith.toolkit.common.util.reflection.InstanceUtil;

import java.lang.Class;import java.lang.System;import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 09/09/11
 * Time: 18:35
 */
public class AnnotationDebug {

    public static void dumpFieldAnnotations( final Class<?> klass) {
        for (final Field f : klass.getDeclaredFields()) {
            System.out.println("\t" + f.getName());
            for (final Annotation ann : f.getAnnotations()) {
                System.out.println("\t\t" + InstanceUtil.getReflectionName(ann.annotationType()));
            }
        }
    }
}
