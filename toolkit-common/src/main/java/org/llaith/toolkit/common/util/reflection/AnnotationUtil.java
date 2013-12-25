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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.Class;import java.lang.Object;import java.lang.Override;import java.lang.String;import java.lang.SuppressWarnings;import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 19/10/11
 * Time: 12:15
 */
public class AnnotationUtil {

    public static boolean hasAnnotationOf(final Class<?> klass, final Collection<Class<? extends Annotation>> wanted) {
        return hasAnnotationOf(klass.getAnnotations(),wanted);
    }

    public static boolean hasAnnotationOf(final Field field, final Collection<Class<? extends Annotation>> wanted) {
        return hasAnnotationOf(field.getAnnotations(),wanted);
    }

    public static boolean hasAnnotationOf(final Annotation[] found, final Collection<Class<? extends Annotation>> wanted) {
        if ((found == null) || (found.length < 1)) return false;
        if ((wanted == null) || (wanted.size() < 1)) return false;

        final Set<String> set = annotationNames(found).keySet();
        set.retainAll(InstanceUtil.classNamesFrom(wanted));
        return !set.isEmpty();
    }


    public static List<Annotation> excludeAnnotations(@Nullable final Annotation[] found, @Nullable final Set<Class<? extends Annotation>> ignored) {
        if ((found == null) || (found.length < 1)) return new ArrayList<>();
        if ((ignored == null) || (ignored.size() < 1)) return new ArrayList<>();

        final Map<String, Annotation> map = annotationNames(found);
        map.keySet().removeAll(InstanceUtil.classNamesFrom(ignored));

        final List<Annotation> ret = new ArrayList<>();

        for (final Map.Entry<String, Annotation> entry : map.entrySet()) {
            ret.add(entry.getValue());
        }

        return ret;
    }


    public static Map<String, Annotation> annotationNames(final Annotation[] found) {
        final Map<String, Annotation> ret = new HashMap<>();
        for (final Annotation a : found) {
            // NOTE: getClass doesn't work for annotations! Also, retainAll on Classes donesn't work (classloaders?)
            ret.put(InstanceUtil.getReflectionName(a.annotationType()),a);
        }
        return ret;
    }


    public static Set<Annotation> checkAnnotationsPresent(final Class<?> klass, final Collection<Class<? extends Annotation>> wanted) {
        final Set<Class<? extends Annotation>> have = collectAnnotationTypes(klass);
        final Set<Annotation> found = new HashSet<>();
        for (final Class<? extends Annotation> ann : wanted) {
            if (have.contains(ann)) found.add(klass.getAnnotation(ann));
        }
        return found;
    }


    public static Set<Class<? extends Annotation>> collectAnnotationTypes(final Class<?> klass) {
        final Set<Class<? extends Annotation>> found = new HashSet<>();
        for (final Annotation ann : klass.getAnnotations()) {
            found.add(ann.annotationType());
        }
        return found;
    }


    public static Set<Annotation> checkAnnotationsPresent(@Nonnull final Field field, final Collection<Class<? extends Annotation>> wanted) {
        final Set<Class<? extends Annotation>> have = collectAnnotationTypes(field);
        final Set<Annotation> found = new HashSet<>();
        for (final Class<? extends Annotation> ann : wanted) {
            if (have.contains(ann)) found.add(field.getAnnotation(ann));
        }
        return found;
    }


    public static Set<Class<? extends Annotation>> collectAnnotationTypes(final Field klass) {
        final Set<Class<? extends Annotation>> found = new HashSet<>();
        for (final Annotation ann : klass.getAnnotations()) {
            found.add(ann.annotationType());
        }
        return found;
    }


    public static Set<Annotation> checkAnnotationsPresent(final Method method, final Collection<Class<? extends Annotation>> wanted) {
        final Set<Class<? extends Annotation>> have = collectAnnotationTypes(method);
        final Set<Annotation> found = new HashSet<>();
        for (final Class<? extends Annotation> ann : wanted) {
            if (have.contains(ann)) found.add(method.getAnnotation(ann));
        }
        return found;
    }


    public static Set<Class<? extends Annotation>> collectAnnotationTypes(final Method method) {
        final Set<Class<? extends Annotation>> found = new HashSet<>();
        for (final Annotation ann : method.getAnnotations()) {
            found.add(ann.annotationType());
        }
        return found;
    }


    public static Map<String,Object> extractDefaultValues(final Class<? extends Annotation> klass) {

        final Map<String,Object> map = new HashMap<>();

        for (final Method m : klass.getMethods()) {
            map.put(m.getName(),m.getDefaultValue());
        }

        return map;

    }

    public static <X extends Annotation> X newAnnotation(final Class<X> klass) {
        return newAnnotation(klass,new HashMap<String, Object>());
    }


    public static <X extends Annotation> X newAnnotation(final Class<X> klass, final Object val) {
        final Map<String, Object> values = new HashMap<>();
        values.put("value",val);
        return newAnnotation(klass,values);
    }


    @SuppressWarnings("unchecked")
    // BLOG THIS
    public static <X extends Annotation> X newAnnotation(final Class<X> klass, final Map<String, Object> vals) {

        final Map<String, Object> map = extractDefaultValues(klass);
        map.putAll(vals);

        return (X) Proxy.newProxyInstance(
                klass.getClassLoader(),
                new Class<?>[]{klass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(final Object proxy, final Method method, final Object[] args) {
                        if ("annotationType".equals(method.getName())) return klass;
                        else return map.get(method.getName());
                    }
                });

    }

}
