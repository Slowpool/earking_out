package org.swetlokognatsk.earking_out.inftrastructure.adapters.events;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.ports.events.DomainEventJsonSerializer;
import tools.jackson.databind.ObjectMapper;

public final class JacksonDomainEventJsonSerializer implements DomainEventJsonSerializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JacksonDomainEventJsonSerializer() {
    }

    public String serializeDomainEvent(final DomainEvent event) {
        return objectMapper.writeValueAsString(event);
    }

}
