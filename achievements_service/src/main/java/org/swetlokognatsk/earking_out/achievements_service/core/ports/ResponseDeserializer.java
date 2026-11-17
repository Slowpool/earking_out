package org.swetlokognatsk.earking_out.achievements_service.core.ports;

import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvents;

public interface ResponseDeserializer {
    DomainEvents deserializeDomainEventsResponse(String responseBody);
}
