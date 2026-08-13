package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;

public abstract class DomainEventHandler<DE extends DomainEvent> {

    public abstract void handle(final DE domainevent);
}
