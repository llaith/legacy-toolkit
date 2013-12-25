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
