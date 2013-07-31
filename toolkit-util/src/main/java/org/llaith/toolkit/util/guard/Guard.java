/*
 * Copyright (c) 2013 Nos Doughty
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
 */
package org.llaith.toolkit.util.guard;

/**
 *
 */
public class Guard {

    public static <T> T expect(final T t, final String msg) {
        if (t == null) throw new NullPointerException(msg);
        return t;
    }

    public static <T> T expectArg(final T t, final String msg) {
        if (t == null) throw new IllegalArgumentException(msg);
        return t;
    }

    public static <T> T expectParam(final String name, final T t) {
        return expectArg(t,String.format("Parameter '%s' is expected.",name));
    }

    public static <T,E extends Exception> T expect(final T t, final E e) throws E {
        if (t == null) throw e;
        return t;
    }

    public static <T> T[] expectSize(final T[] arr, final int size, final String msg) {
        if ( (arr != null) && (arr.length) != size) throw new IllegalArgumentException(msg);
        return arr;
    }

    public static String expectSystemProperty(final String key) {
        final String value = System.getProperty(key);
        if (value == null) throw new IllegalArgumentException("A value was expected for system-property: "+key);
        return value;
    }

}
