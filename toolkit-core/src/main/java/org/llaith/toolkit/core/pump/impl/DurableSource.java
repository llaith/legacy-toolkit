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
import org.llaith.toolkit.common.exception.ext.UncheckedException;
import org.llaith.toolkit.core.pump.Chunk;
import org.llaith.toolkit.core.pump.Source;

/**
 * Warning, it's conceivable this will get stuck in a loop. You may need to either
 * use the EnumeratedPump, which calls the hasMore() on an enumerated source or
 * the error handler passed in should have a max errors setting after which it throws
 * the exception.
 */
public class DurableSource<T> implements Source<T> {

    private final Source<T> delegate;
    private final ExceptionHandler handler;
    private final int maxErrors;

    private int errorCount = 0;

    public DurableSource(final ExceptionHandler handler, final Source<T> delegate, final int maxErrors) {
        this.handler = handler;
        this.delegate = delegate;
        this.maxErrors = maxErrors;
    }

    @Override
    public Chunk<T> get() throws RuntimeException {
        while (true) {
            try {
                final Chunk<T> ret = this.delegate.get();
                this.errorCount = 0;
                return ret;
            } catch (Exception e) {
                this.errorCount++;
                if (this.errorCount >= this.maxErrors) throw new UncheckedException("Too many sequential exceptions.",e);
                this.handler.onException(e);
            }
        }
    }

    @Override
    public void close() throws RuntimeException {
        this.delegate.close();
    }

}
