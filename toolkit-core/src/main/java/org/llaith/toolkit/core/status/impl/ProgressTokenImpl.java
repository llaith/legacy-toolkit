package org.llaith.toolkit.core.status.impl;

import org.llaith.toolkit.core.status.ProgressToken;
import org.llaith.toolkit.core.status.StatusLevel;

/**
 *
 */
public class ProgressTokenImpl implements ProgressToken {

    private final StatusLogger logger;
    private final Context context;
    private final Object id;
    private final StatusLevel level;

    public ProgressTokenImpl(final StatusLogger logger, final Context context,
                             final Object id, final StatusLevel level) {
        this.logger = logger;
        this.context = context;
        this.id = id;
        this.level = level;
    }

    @Override
    public ProgressToken update(final String heading, final int total, final int count, final String message) {

        this.logger.reportProgress(
                new ElapsedContext(this.context),
                this.id,
                this.level,
                heading,
                total,
                count,
                message);


        return this;
    }

}
