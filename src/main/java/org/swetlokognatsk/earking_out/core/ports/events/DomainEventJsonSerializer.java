package org.swetlokognatsk.earking_out.core.ports.events;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;

public interface DomainEventJsonSerializer {
    String serializeDomainEvent(final DomainEvent event);
}
