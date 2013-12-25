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
package org.llaith.toolkit.common.exception.ext;


import org.llaith.toolkit.common.exception.BaseException;

/**
 * Used to turn a checked exception into a runtime one. If looking for the more
 * general unwrap routines that used to be ni ExceptionUtils, they aint here
 * either. Those were deleted in favour of Guavas propagate exception utils.
 *
 * Design: we do not wrap() an exception and pass a msg because it may not
 * be used, which makes debugging weird. Choose either to create an UncheckedException
 * with a distinct message and use the ctors natively, or just to wrap() a
 * checked exception and if so, pass that without any more input.
 */
public class UncheckedException extends RuntimeException implements BaseException {

    public static RuntimeException wrap(final Throwable t) {
        if (t instanceof Error) throw (Error)t;
        if (t instanceof RuntimeException) return (RuntimeException)t;
        return new UncheckedException("Wrapped: "+t.getMessage(),t);
    }

    public UncheckedException(final String msg) {
        super(msg);
    }

    /**
     * We won't wrap ourselves again. If we do, we loose the message from those
     * and replace it with this. That's desired.
     */
    public UncheckedException(final String msg, final Throwable t) {
        super(msg,t);

        Throwable srch = t;
        while (srch instanceof UncheckedException) {
            srch = srch.getCause();
            if (srch == null) break;
        }

        if (srch != null) super.initCause(srch);
        else super.initCause(t);

    }

    // Use UncheckedException.wrap() instead.
    private UncheckedException(final Throwable t) {
        super(t);
    }

}
