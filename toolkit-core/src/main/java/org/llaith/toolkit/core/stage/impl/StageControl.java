package org.llaith.toolkit.core.stage.impl;

import org.llaith.toolkit.common.exception.ext.UncheckedException;
import org.llaith.toolkit.core.stage.Stage;
import org.llaith.toolkit.core.status.StatusToken;

/**
 * This is a stage delegate, not a nested stage, it does not announce itself through
 * the status tracking.
 */
public class StageControl implements Stage {

    private final StageListener listener;
    private final Stage delegate;

    public StageControl(StageListener listener, final Stage delegate) {
        this.listener = listener;
        this.delegate = delegate;
    }

    @Override
    public void execute(StatusToken status) {
        this.listener.onStart(this,status);
        try {
            this.delegate.execute(status);
            this.listener.onComplete(this,status);
        } catch (Exception e) {
            try {
                this.listener.onFailure(this,status);
            } catch (Exception e1) {
                e.addSuppressed(e1);
            }
            throw UncheckedException.wrap(e);
        }
    }

}
