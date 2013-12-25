package org.llaith.toolkit.common.util.reflection;

import org.llaith.toolkit.common.exception.ext.UncheckedException;
import org.llaith.toolkit.common.guard.Guard;

import java.lang.reflect.Field;

/**
 * Use the Fest-reflect or perhaps the vidageek.TypeMirror in preference to this, but sometimes
 * you want to set properties as objects and that's what this is for.
 */
public class PropertyFieldUtil {

    public static void fieldSet(final Object target, final String fieldName, final Object value) {

        Class<?> klass = Guard.notNull(target).getClass();

        while (klass != null) {
            try {
                final Field field = klass.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target,value);
                return;
            } catch (NoSuchFieldException e) {
                klass = klass.getSuperclass();
            } catch (Exception e) {
                throw new UncheckedException(String.format(
                        "Cannot set field value: %s on field named: %s in object of dtoField: %s.",
                        value,
                        fieldName,
                        klass.getName()
                ),e);
            }
        }

        throw new UncheckedException(String.format(
                "Cannot find field named: %s in object of dtoField: %s.",
                fieldName,
                target.getClass().getName()));

    }

    @SuppressWarnings("unchecked")
    public static <E> E fieldGet(final Object target, final String fieldName) {

        Class<?> klass = Guard.notNull(target.getClass());

        while (klass != null) {
            try {
                final Field field = klass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return (E)field.get(target);
            } catch (NoSuchFieldException e) {
                klass = klass.getSuperclass();
            } catch (Exception e) {
                throw new UncheckedException(String.format(
                        "Cannot get value from field named: %s in object of dtoField: %s.",
                        fieldName,
                        klass.getName()
                ),e);
            }
        }

        throw new UncheckedException(String.format(
                "Cannot find field named: %s in object of dtoField: %s.",
                fieldName,
                target.getClass().getName()));

    }

}
