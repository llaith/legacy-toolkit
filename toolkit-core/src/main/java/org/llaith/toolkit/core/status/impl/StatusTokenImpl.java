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

import com.google.common.base.Stopwatch;
import org.llaith.toolkit.core.status.ExceptionToken;
import org.llaith.toolkit.core.status.MessageToken;
import org.llaith.toolkit.core.status.ProgressToken;
import org.llaith.toolkit.core.status.StatusLevel;
import org.llaith.toolkit.core.status.StatusToken;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * A better version of this would be based around:
 * http://metrics.codahale.com
 */
public class StatusTokenImpl extends StatusControlImpl implements StatusToken {

    private final Context stack;
    private final StatusLogger logger;

    private final Object context;
    private final Date timestamp;
    private final Stopwatch timer;

    public StatusTokenImpl(final Context stack,
                           final StatusLogger logger,
                           final Object context) {
        super(stack,logger);

        this.stack = stack;
        this.logger = logger;

        this.context = context;
        this.timestamp = new Date();
        this.timer = Stopwatch.createStarted();
    }

    @Override
    public Object context() {
        return this.context;
    }

    @Override
    public Date timeStarted() {
        return timestamp;
    }

    @Override
    public long timeElapsed(TimeUnit timeUnit) {
        return this.timer.elapsed(timeUnit);
    }

    @Override
    public MessageToken postMessage(final StatusLevel level) {
        return this.postMessage(
                UUID.randomUUID().toString(),
                level);
    }

    @Override
    public MessageToken postMessage(final Object id, final StatusLevel level) {
        return new MessageTokenImpl(
                this.logger,
                new Context(this.stack),
                id,
                level);
    }

    @Override
    public ExceptionToken postException(final StatusLevel level) {
        return this.postException(
                UUID.randomUUID().toString(),
                level);
    }

    @Override
    public ExceptionToken postException(final Object id, final StatusLevel level) {
        return new ExceptionTokenImpl(
                this.logger,
                new Context(this.stack),
                id,
                level);
    }

    @Override
    public ProgressToken postProgress(final StatusLevel level) {
        return this.postProgress(
                UUID.randomUUID().toString(),
                level);
    }

    @Override
    public ProgressToken postProgress(final Object id, final StatusLevel level) {
        return new ProgressTokenImpl(
                this.logger,
                new Context(this.stack),
                id,
                level);
    }

}
