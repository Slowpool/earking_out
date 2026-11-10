package org.swetlokognatsk.earking_out.core.ports.events;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;

public interface EventPublisher {
    void publish(final DomainEvent event);

    default void publish(final Iterable<DomainEvent> events) {
        for(var event : events) {
            publish(event);
        }
    }
}
