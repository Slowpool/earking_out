package org.swetlokognatsk.earking_out.achievements_service.infrastructure.adapters.events;

import java.time.LocalDateTime;
import java.util.HashMap;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvent;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.events.DomainEvents;
import org.swetlokognatsk.earking_out.achievements_service.core.ports.ResponseDeserializer;
import org.swetlokognatsk.earking_out.achievements_service.core.ports.events.DomainEventDeserializer;
import lombok.NonNull;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

@Component
public class JacksonDeserializer implements DomainEventDeserializer, ResponseDeserializer {

    private final ObjectMapper objectMapper;

    {
        objectMapper = JsonMapper.builder()
                .build();
    }

    public DomainEvent deserializeDomainEvent(@NonNull final String serializedDomainEvent) {
        validateSerializedDomainEvent(serializedDomainEvent);

        var rootNode = objectMapper.readTree(serializedDomainEvent);
        var payloadNode = rootNode.get("payload");

        var type = rootNode.get("type")
                .asString();

        var userId = objectMapper.treeToValue(payloadNode.get("userId"), UserId.class);

        var timestampValue = payloadNode.get("timestamp")
                .asString();
        var timestamp = LocalDateTime.parse(timestampValue);

        var hashMapType = new TypeReference<HashMap<String, Object>>() {
        };
        var properties = objectMapper.convertValue(payloadNode, hashMapType);

        return new DomainEvent(type, userId, timestamp, properties);
    }

    private void validateSerializedDomainEvent(final String serializedDomainEvent) {
        if (serializedDomainEvent.isBlank()) {
            throw new IllegalArgumentException();
        }

        // yes, small, not complete validation
        if (!serializedDomainEvent.strip().startsWith("{")) {
            throw new IllegalArgumentException();
        }
    }

    public DomainEvents deserializeDomainEvents(final String serializedDomainEvents) {
        var rootNode = objectMapper.readTree(serializedDomainEvents);
        var domainEvents = new DomainEvents();
        DomainEvent domainEvent;
        for (var domainEventNode : rootNode) {
            domainEvent = deserializeDomainEvent(domainEventNode.toString());
            domainEvents.add(domainEvent);
        }
        return domainEvents;
    }

    public DomainEvents deserializeDomainEventsResponse(final String response) {
        var rootNode = objectMapper.readTree(response);
        var eventsNode = rootNode.get("domainEvents");
        return deserializeDomainEvents(eventsNode.toString());
    }
}
