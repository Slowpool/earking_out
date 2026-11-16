package org.swetlokognatsk.earking_out.achievements_service.core.ports.events;

import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvent;

public interface DomainEventDeserializer {
    DomainEvent deserializeDomainEvent(String serializedDomainEvent);
}
