/*
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
 */
package org.llaith.toolkit.common.exception;

import net.vidageek.mirror.dsl.Mirror;
import org.llaith.toolkit.common.util.lang.ExceptionUtil;

/**
 * http://code.google.com/p/guava-libraries/wiki/ReflectionExplained
 */
public class ThrowableFactory<T extends Throwable> {

    private static final int DEFAULT_LEVEL = 8;

    private final Class<T> throwableClass;

    public ThrowableFactory(final Class<T> throwableClass) {
        this.throwableClass = throwableClass;
    }

    public T throwableFor(final String msg) {
        return throwableFor(DEFAULT_LEVEL+1,msg);
    }

    public T throwableFor(final int level, final String msg) {
        return ExceptionUtil.popStackTrace(DEFAULT_LEVEL + level, new Mirror()
                .on(this.throwableClass)
                .invoke()
                .constructor()
                .withArgs(msg));
    }

    public T throwableFor(final Throwable t) {
        return throwableFor(DEFAULT_LEVEL+1,t);
    }

    public T throwableFor(final int level, final Throwable t) {
        return ExceptionUtil.popStackTrace(DEFAULT_LEVEL + level, new Mirror()
                .on(this.throwableClass)
                .invoke()
                .constructor()
                .withArgs(t));
    }

    public T throwableFor(final String msg, final Throwable t) {
        return throwableFor(DEFAULT_LEVEL+1,msg,t);
    }

    public T throwableFor(final int level, final String msg, final Throwable t) {
        return ExceptionUtil.popStackTrace(DEFAULT_LEVEL + level, new Mirror()
                .on(this.throwableClass)
                .invoke()
                .constructor()
                .withArgs(msg, t));
    }

}
