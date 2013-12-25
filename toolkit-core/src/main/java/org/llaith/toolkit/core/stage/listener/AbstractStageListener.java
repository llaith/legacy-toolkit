package org.llaith.toolkit.core.stage.listener;

import org.llaith.toolkit.core.stage.impl.StageListener;
import org.llaith.toolkit.core.status.StatusToken;

/**
 *
 */
public abstract class AbstractStageListener implements StageListener {

    @Override
    public void onStart(Object source, StatusToken status) {
        // nothing
    }

    @Override
    public void onComplete(Object source, StatusToken status) {
        // nothing
    }

    @Override
    public void onFailure(Object source, StatusToken status) {
        // nothing
    }

}
