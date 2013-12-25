package org.llaith.toolkit.core.status.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 *
 */
public class ContextSnapshot {

    private final Object context;
    private final Date timeStarted;
    private final long timeElapsed;

    public ContextSnapshot(final Object context, final Date timeStarted, final long timeElapsed) {
        this.context = context;
        this.timeStarted = timeStarted;
        this.timeElapsed = timeElapsed;
    }

    public Object context() {
        return context;
    }

    public Date timeStarted() {
        return timeStarted;
    }

    public long timeElapsed(TimeUnit timeUnit) {
        return timeUnit.convert(this.timeElapsed,NANOSECONDS);
    }

}
