package org.llaith.toolkit.core.status.impl;

import org.llaith.toolkit.core.memo.Memo;
import org.llaith.toolkit.core.status.ExceptionToken;
import org.llaith.toolkit.core.status.StatusLevel;

/**
 *
 */
public class ExceptionTokenImpl implements ExceptionToken {

    private final StatusLogger logger;
    private final Context context;
    private final Object id;
    private final StatusLevel level;

    public ExceptionTokenImpl(final StatusLogger logger, final Context context, final Object id, final StatusLevel level) {
        this.logger = logger;
        this.context = context;
        this.id = id;
        this.level = level;
    }

    @Override
    public ExceptionToken update(final Memo memo, final Exception exception) {

        if (memo != null) {
            this.logger.reportMessage(
                    new ElapsedContext(this.context),
                    this.id,
                    this.level,
                    memo);
        }

        if (exception != null) {
            this.logger.reportException(
                    new ElapsedContext(this.context),
                    this.id,
                    this.level,
                    exception);
        }

        return this;

    }

}
