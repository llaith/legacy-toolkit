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
package org.llaith.toolkit.common.debug;

import java.lang.Class;
import java.lang.ClassNotFoundException;
import java.lang.String;
import java.lang.System;
import java.lang.Throwable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 02/04/12
 * Time: 19:21
 * <p/>
 * From here:
 * http://docs.oracle.com/javase/tutorial/reflect/class/classModifiers.html
 */
public class ClassDeclarationDebug {

    public static void outputClassDecl(final String className) throws ClassNotFoundException {
        outputClassDecl(Class.forName(className));
    }

    public static void outputClassDecl(final Class<?> c) {
        System.out.format("Class:%n  %s%n%n",c.getName());
        System.out.format("Modifiers:%n  %s%n%n",
                Modifier.toString(c.getModifiers()));

        System.out.format("Type Parameters:%n");
        final TypeVariable<?>[] tv = c.getTypeParameters();
        if (tv.length != 0) {
            System.out.format("  ");
            for (final TypeVariable<?> t : tv)
                System.out.format("%s ",t.getName());
            System.out.format("%n%n");
        } else {
            System.out.format("  -- No Type Parameters --%n%n");
        }

        System.out.format("Implemented Interfaces:%n");
        final Type[] intfs = c.getGenericInterfaces();
        if (intfs.length != 0) {
            for (final Type intf : intfs)
                System.out.format("  %s%n",intf.toString());
            System.out.format("%n");
        } else {
            System.out.format("  -- No Implemented Interfaces --%n%n");
        }

        System.out.format("Inheritance Path:%n");
        final List<Class<?>> l = new ArrayList<Class<?>>();
        printAncestor(c,l);
        if (l.size() != 0) {
            for (final Class<?> cl : l)
                System.out.format("  %s%n",cl.getName());
            System.out.format("%n");
        } else {
            System.out.format("  -- No Super Classes --%n%n");
        }

        System.out.format("Annotations:%n");
        final Annotation[] ann = c.getAnnotations();
        if (ann.length != 0) {
            for (final Annotation a : ann)
                System.out.format("  %s%n",a.toString());
            System.out.format("%n");
        } else {
            System.out.format("  -- No Annotations --%n%n");
        }

    }

    private static void printAncestor(final Class<?> c, final List<Class<?>> l) {
        final Class<?> ancestor = c.getSuperclass();
        if (ancestor != null) {
            l.add(ancestor);
            printAncestor(ancestor,l);
        }
    }

    public static void main(String[] args) {

        try {

            //outputClassDecl(String.class.getName());


            // local class
            class A<B extends Collection<A<B>>> extends ArrayList<A<B>> implements Collection<A<B>> {

            }

            outputClassDecl(A.class);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}

