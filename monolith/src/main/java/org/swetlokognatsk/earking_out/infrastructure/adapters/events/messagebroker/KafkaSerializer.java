package org.swetlokognatsk.earking_out.infrastructure.adapters.events.messagebroker;

import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.events.DomainEventJsonSerializer;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

// TODO why to use spring boot docker compose extension at all then if running app via docker compose directly is much simpler?
@Component
public class KafkaSerializer implements Serializer<DomainEvent> {

    private final DomainEventJsonSerializer eventSerializer;
    private final ObjectMapper objectMapper;

    {
        objectMapper = JsonMapper.builder()
                .build();
    }

    public KafkaSerializer() {
        this.eventSerializer = DI.get(DomainEventJsonSerializer.class);
    }

    public KafkaSerializer(final DomainEventJsonSerializer eventSerializer) {
        this.eventSerializer = eventSerializer;
    }

    public byte[] serialize(final String topic, final DomainEvent event) {
        var serializedDomainEvent = eventSerializer.serializeDomainEvent(event);
        serializedDomainEvent = wrapWithType(event.getClass(), serializedDomainEvent);
        return serializedDomainEvent.getBytes();
    }

    /**
     * Converts
     * <pre>
     * {
     *     "userId": 1,
     *     "timestamp": 2026-10-22T14:34:04.147064557
     * }
     * </pre>
     * to
     * <pre>
     * {
     *     "type": "SessionStartedEvent",
     *     "payload": {
     *         "userId": 1,
     *         "timestamp": 2026-10-22T14:34:04.147064557
     *     }
     * }
     * </pre>
     */
    private String wrapWithType(Class<?> eventClass, final String serializedDomainEvent) {
        var type = eventClass.getSimpleName();
        var wrappedDomainEvent = new DomainEventWrapper(type, serializedDomainEvent);
        return objectMapper.writeValueAsString(wrappedDomainEvent);
    }

    private record DomainEventWrapper(String type, @JsonRawValue String payload) {
    }

}
