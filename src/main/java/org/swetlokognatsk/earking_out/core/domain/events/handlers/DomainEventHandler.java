package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;

public interface DomainEventHandler<DE extends DomainEvent> {

    void handle(final DE domainevent);
}
