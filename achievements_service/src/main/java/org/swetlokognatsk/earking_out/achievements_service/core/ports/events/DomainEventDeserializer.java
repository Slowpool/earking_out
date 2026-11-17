package org.swetlokognatsk.earking_out.achievements_service.core.ports.events;

import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvent;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvents;

public interface DomainEventDeserializer {
    DomainEvent deserializeDomainEvent(String serializedDomainEvent);
    DomainEvents deserializeDomainEvents(String serializedDomainEvents);
}
