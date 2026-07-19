package org.swetlokognatsk.earking_out.core.ports.events;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;

public interface EventPublisher {
    public void publish(final DomainEvent event);
}
