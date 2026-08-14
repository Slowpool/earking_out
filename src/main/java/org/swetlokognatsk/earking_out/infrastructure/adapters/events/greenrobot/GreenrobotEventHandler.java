package org.swetlokognatsk.earking_out.infrastructure.adapters.events.greenrobot;

import org.greenrobot.eventbus.Subscribe;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.DomainEventHandler;

public abstract class GreenrobotEventHandler<DE extends DomainEvent, DH extends DomainEventHandler<DE>> {
    protected final DH domainHandler;

    public GreenrobotEventHandler(final DH domainHandler) {
        this.domainHandler = domainHandler;
    }

    @Subscribe
    public void handle(final DE e) {
        domainHandler.handle(e);
    }
}
