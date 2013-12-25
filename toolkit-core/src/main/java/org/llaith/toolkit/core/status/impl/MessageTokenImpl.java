package org.llaith.toolkit.core.status.impl;

import org.llaith.toolkit.core.memo.Memo;
import org.llaith.toolkit.core.status.MessageToken;
import org.llaith.toolkit.core.status.StatusLevel;

/**
 *
 */
public class MessageTokenImpl implements MessageToken {

    private final StatusLogger logger;
    private final Context context;
    private final Object id;
    private final StatusLevel level;

    public MessageTokenImpl(final StatusLogger logger, final Context context, final Object id, final StatusLevel level) {
        this.logger = logger;
        this.context = context;
        this.id = id;
        this.level = level;
    }

    @Override
    public MessageToken update(final Memo memo) {

        if (memo != null) {
            this.logger.reportMessage(
                    new ElapsedContext(this.context),
                    this.id,
                    this.level,
                    memo);
        }

        return this;

    }

}
