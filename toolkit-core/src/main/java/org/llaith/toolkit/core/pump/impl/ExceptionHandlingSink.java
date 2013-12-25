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
package org.llaith.toolkit.core.pump.impl;

import org.llaith.toolkit.common.exception.ExceptionHandler;
import org.llaith.toolkit.core.pump.Chunk;
import org.llaith.toolkit.core.pump.Sink;
import org.llaith.toolkit.common.guard.Guard;

/**
 *
 */
public class ExceptionHandlingSink<T> implements Sink<T> {

    private final Sink<T> sink;
    private final ExceptionHandler handler;

    public ExceptionHandlingSink(final Sink<T> sink, final ExceptionHandler handler) {
        this.sink = Guard.notNull(sink);
        this.handler = Guard.notNull(handler);
    }

    @Override
    public void put(final Chunk<T> chunk) {
        try {
            if (chunk != null) this.sink.put(chunk);
        } catch (Exception e) {
            this.handler.onException(e);
        }
    }

    @Override
    public void close() throws RuntimeException {
        this.sink.close();
    }

}
