package org.llaith.toolkit.common.pattern.impl;

import org.llaith.toolkit.common.pattern.Activity;

/**
 * Very simple implementation of a buffer.
 */
public abstract class AbstractActivity implements Activity {

    private boolean active = false;

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void activate() {
        this.active = true;
    }

    @Override
    public void deactivate() {
        this.active = false;
    }

}
