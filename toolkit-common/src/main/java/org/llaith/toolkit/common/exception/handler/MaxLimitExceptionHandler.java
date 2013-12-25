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
package org.llaith.toolkit.common.exception.handler;

import org.llaith.toolkit.common.exception.ExceptionHandler;
import org.llaith.toolkit.common.exception.handler.MaxErrorsException.MaxType;

/**
 * Designed to be *shared* among many wrapped error handlers to implement a general max-errors functionality
 * across sources and sinks.
 */
public class MaxLimitExceptionHandler implements ExceptionHandler {

    private final int max;
    private final ExceptionHandler handler;

    private int current = 0;

    public MaxLimitExceptionHandler(final int max, final ExceptionHandler handler) {
        this.max = max;
        this.handler = handler;
    }

    @Override
    public void onException(Exception e) {
        this.current++;
        if (current > max) throw new MaxErrorsException(MaxType.TOTAL,this.max);
        this.handler.onException(e);
    }

}
