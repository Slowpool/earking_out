package org.swetlokognatsk.earking_out.infrastructure.adapters.events;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.*;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.DomainEvent;
import org.swetlokognatsk.earking_out.achievements_service.core.domain.model.UserId;
import org.swetlokognatsk.earking_out.achievements_service.core.ports.events.DomainEventDeserializer;
import org.swetlokognatsk.earking_out.achievements_service.infrastructure.adapters.events.JacksonDeserializer;

public class JacksonDeserializerTest {

    private DomainEventDeserializer deserializer;

    void assertHasProperty(final DomainEvent event, final String property, final Object expectedValue) {
        var propertyValue = event.get(property);
        assertEquals(expectedValue, propertyValue);
    }

    void assertHasProperty(final DomainEvent event, final String property) {
        assertNotNull(event.get(property));
    }

    @BeforeEach
    void setup() {
        deserializer = new JacksonDeserializer();
    }

    @Test
    void nullSerializedDomainEvent() {
        String serializedDomainEvent = null;

        try {
            deserializer.deserializeDomainEvent(serializedDomainEvent);
            fail();
        } catch (NullPointerException e) {
        }
    }

    @Test
    void emptyString() {
        String serializedDomainEvent = "";

        try {
            deserializer.deserializeDomainEvent(serializedDomainEvent);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    void invalidJsonString() {
        String serializedDomainEvent = "abcdefg";

        try {
            deserializer.deserializeDomainEvent(serializedDomainEvent);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    void nullProperty() {
        String serializedDomainEvent = """
                {
                    "type": "SomeEventType",
                    "payload": {
                        "userId": {"id": 1937},
                        "timestamp": "2026-11-16T10:48:46.118501009",
                        "null": null
                    }
                }
                """;

        var event = deserializer.deserializeDomainEvent(serializedDomainEvent);

        assertHasProperty(event, "null", null);
    }

    @Test
    void userId() {
        String serializedDomainEvent = """
                {
                    "type": "SomeEventType",
                    "payload": {
                        "userId": {"id": 1937},
                        "timestamp": "2026-11-16T10:48:46.118501009"
                    }
                }
                """;

        var event = deserializer.deserializeDomainEvent(serializedDomainEvent);

        assertEquals(new UserId(1937), event.userId());
    }

    @Test
    void timestamp() {
        String serializedDomainEvent = """
                {
                    "type": "SomeEventType",
                    "payload": {
                        "userId": {"id": 1937},
                        "timestamp": "2026-11-16T10:48:46.118501009"
                    }
                }
                """;

        var event = deserializer.deserializeDomainEvent(serializedDomainEvent);

        var dateTime = LocalDateTime.parse("2026-11-16T10:48:46.118501009");
        assertEquals(dateTime, event.timestamp());
    }

    @Test
    void booleanProperty() {
        String serializedDomainEvent = """
                {
                    "type": "SomeEventType",
                    "payload": {
                        "userId": {"id": 1937},
                        "timestamp": "2026-11-16T10:48:46.118501009",
                        "boolean": true
                    }
                }
                """;

        var event = deserializer.deserializeDomainEvent(serializedDomainEvent);

        assertHasProperty(event, "boolean", true);
    }

    @Test
    void intProperty() {
        String serializedDomainEvent = """
                {
                    "type": "SomeEventType",
                    "payload": {
                        "userId": {"id": 1937},
                        "timestamp": "2026-11-16T10:48:46.118501009",
                        "int": 1937
                    }
                }
                """;

        var event = deserializer.deserializeDomainEvent(serializedDomainEvent);

        assertHasProperty(event, "int", 1937);
    }

    @Test
    void stringProperty() {
        String serializedDomainEvent = """
                {
                    "type": "SomeEventType",
                    "payload": {
                        "userId": {"id": 1937},
                        "timestamp": "2026-11-16T10:48:46.118501009",
                        "string": "someString"
                    }
                }
                """;

        var event = deserializer.deserializeDomainEvent(serializedDomainEvent);

        assertHasProperty(event, "string", "someString");
    }

    // skip it yet, because it's not needed, so it fails
    @Test
    void arrayProperty() {
        String serializedDomainEvent = """
                {
                    "type": "SomeEventType",
                    "payload": {
                        "userId": {"id": 1937},
                        "timestamp": "2026-11-16T10:48:46.118501009",
                        "array": [1, 2, 3]
                    }
                }
                """;

        var event = deserializer.deserializeDomainEvent(serializedDomainEvent);

        assertHasProperty(event, "array", new int[] { 1, 2, 3 });
    }

    @Test
    void objectProperty() {
        String serializedDomainEvent = """
                {
                    "type": "SomeEventType",
                    "payload": {
                        "userId": {"id": 1937},
                        "timestamp": "2026-11-16T10:48:46.118501009",
                        "object": {
                            "property1": "propertyValue1",
                            "property2": "propertyValue2"
                        }
                    }
                }
                """;

        var event = deserializer.deserializeDomainEvent(serializedDomainEvent);

        assertHasProperty(event, "object");
        var object = (Map<String, Object>) event.get("object");
        assertEquals(object.get("property1"), "propertyValue1");
        assertEquals(object.get("property2"), "propertyValue2");
    }
}
