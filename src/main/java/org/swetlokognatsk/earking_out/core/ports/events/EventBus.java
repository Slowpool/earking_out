package org.swetlokognatsk.earking_out.core.ports.events;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.DomainEventHandler;

public interface EventBus {
    <DE extends DomainEvent> void subscribe(Class<DE> eventClass, DomainEventHandler<DE> domainEventHandler);
}
