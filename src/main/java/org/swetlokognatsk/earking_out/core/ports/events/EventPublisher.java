package org.swetlokognatsk.earking_out.core.ports.events;

import java.util.List;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;

public interface EventPublisher {
    void publish(final DomainEvent event);

    default void publish(final List<DomainEvent> events) {
        for(var event : events) {
            publish(event);
        }
    }
}
