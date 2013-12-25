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
package org.llaith.toolkit.common.exception.handler;

import org.llaith.toolkit.common.exception.ExceptionHandler;
import org.llaith.toolkit.common.exception.handler.MaxErrorsException.MaxType;
import org.llaith.toolkit.common.guard.Guard;

/**
 * This wraps another ExceptionHandler and let's that handle the errors util it hits it's
 * max count, then it throws. Note that the underlying exception handler may still throw
 * an error anyway.
 */
public class MaxCountExceptionHandler implements ExceptionHandler {

    private static final int MAX_DEFAULT = 3;

    private final int maxRepeatedErrors;
    private final ExceptionHandler handler;

    private int currentRepeatedErrors = 0;

    public MaxCountExceptionHandler(final ExceptionHandler handler) {
        this(MAX_DEFAULT,handler);
    }

    public MaxCountExceptionHandler(final int maxRepeatedErrors, final ExceptionHandler handler) {
        this.maxRepeatedErrors = maxRepeatedErrors;
        this.handler = Guard.notNull(handler);
    }

    public void markFailed(final Exception e) {
    }

    @Override
    public void onException(final Exception e) {
        this.currentRepeatedErrors++;
        if (this.currentRepeatedErrors > this.maxRepeatedErrors) throw new MaxErrorsException(
                MaxType.REPEATED,
                this.maxRepeatedErrors);
        this.handler.onException(e);
    }

}
