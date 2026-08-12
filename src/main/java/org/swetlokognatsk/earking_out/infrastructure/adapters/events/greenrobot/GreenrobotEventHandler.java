package org.swetlokognatsk.earking_out.infrastructure.adapters.events.greenrobot;

import org.greenrobot.eventbus.Subscribe;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.DomainEventHandler;

// TODO is there a way to not specify separate DE generic here, but still use it in handle? to void redundant copy-past (event is already in DH)
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
