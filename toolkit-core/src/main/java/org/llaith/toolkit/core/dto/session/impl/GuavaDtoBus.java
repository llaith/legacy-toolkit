package org.llaith.toolkit.core.dto.session.impl;

import com.google.common.eventbus.EventBus;
import org.llaith.toolkit.core.dto.DtoObject;
import org.llaith.toolkit.core.dto.session.DtoBus;
import org.llaith.toolkit.core.dto.session.DtoRefreshEvent;

/**
 * An implementation of the DtoBus using Guava's EventBus.
 * The registering DtoObjects need to have their listening
 * methods marked with the com.google.common.eventbus.Subscribe
 * annotation.
 */
public class GuavaDtoBus implements DtoBus {

    private final EventBus eventBus = new EventBus();

    @Override
    public void register(final DtoObject<?> self) {
        this.eventBus.register(self);
    }

    @Override
    public void unregister(final DtoObject<?> self) {
        this.eventBus.unregister(self);
    }

    @Override
    public void post(final DtoRefreshEvent<?> event) {
        this.eventBus.post(event);
    }

}
