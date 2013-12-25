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
package org.llaith.toolkit.core.status.impl;

import org.llaith.toolkit.core.status.StatusControl;
import org.llaith.toolkit.core.status.StatusToken;

/**
 * A better version of this would be based around:
 *      http://metrics.codahale.com
 */
public class StatusControlImpl implements StatusControl {

    private final Context stack;
    private final StatusLogger statusLogger;

    public StatusControlImpl(final StatusLogger statusLogger) {
        this(new Context(),statusLogger);
    }

    protected StatusControlImpl(final Context stack, final StatusLogger statusLogger) {
        this.stack = stack;
        this.statusLogger = statusLogger;
    }

    @Override
    public StatusToken startNested(final Object context) {
        this.stack.push(new StatusTokenImpl(
                this.stack,
                this.statusLogger,
                context));
        this.statusLogger.reportStart(new ElapsedContext(this.stack),"Started.");
        return this.stack.peek();
    }

    @Override
    public void markNestedOk(final StatusToken token) {
        this.markStageCompletion(token,true);
    }

    @Override
    public void markNestedFailed(final StatusToken token) {
        this.markStageCompletion(token,false);
    }

    @Override
    public void markStageCompletion(final StatusToken token, final boolean success) {
        if (token == this) throw new IllegalStateException("A token cannot close itself!");
        if (success) this.statusLogger.reportSuccess(new ElapsedContext(this.stack),"Success.");
        else this.statusLogger.reportFailure(new ElapsedContext(this.stack),"Failure.");
        this.stack.popTo(token);
    }

}
