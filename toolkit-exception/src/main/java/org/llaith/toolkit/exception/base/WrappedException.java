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
package org.llaith.toolkit.exception.base;


/**
 *
 */
public class WrappedException extends BaseRuntimeException {

    public static RuntimeException wrap(Throwable t) {
        if (t instanceof Error) throw (Error)t;
        if (t instanceof RuntimeException) return (RuntimeException)t;
        return new WrappedException(t);
    }

    public static Throwable unwrap(Throwable t) {
        Throwable curr = t;
        while (true) {
            if (curr == null) return null;
            if (!(curr instanceof WrappedException)) return curr;
            curr = curr.getCause();
        }
    }

    public static <T extends Throwable> T unwrapTo(Class<T> klass, Throwable t) {
        Throwable curr = t;
        while (true) {
            if (curr == null) return null;
            if (klass.isAssignableFrom(curr.getClass())) return klass.cast(curr);
            curr = curr.getCause();
        }
    }

    public static <T extends Throwable> void rethrowBase(Class<T> klass, Throwable t) throws T {
        Throwable curr = t;
        while (true) {
            if (curr == null) break;
            if (klass.isAssignableFrom(curr.getClass())) throw klass.cast(curr);
            curr = curr.getCause();
        }
    }

    public static <T extends Throwable> void rethrowIfWrapping(Class<T> klass, Throwable t) throws WrappedException {
        Throwable curr = t;
        while (true) {
            if (curr == null) break;
            if (klass.isAssignableFrom(curr.getClass())) throw WrappedException.wrap(klass.cast(curr));
            curr = curr.getCause();
        }
    }

    private WrappedException(Throwable t) {
        // private so we avoid direct creation and possible double wrapped exceptions.
        super("[Wrapped] "+t.getMessage(),t);
    }

}
