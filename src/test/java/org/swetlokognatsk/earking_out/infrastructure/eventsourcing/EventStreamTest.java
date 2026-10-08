package org.swetlokognatsk.earking_out.infrastructure.eventsourcing;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.LinkedList;
import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEventsFactory;
import org.swetlokognatsk.earking_out.core.domain.events.EventStream;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

@SpringBootTest
public final class EventStreamTest {

    @Test
    public void iteratorTest() {
        var eventsFactory = DI.get(DomainEventsFactory.class);
        var domainEvents = new DomainEvent[] { eventsFactory.createSessionStartedEvent(null, null), eventsFactory.createUserTriedToGuessPuzzleEvent(null, 0, null, 0, false) };
        var eventStream = new EventStream<>(null, domainEvents);

        var iteratedEvents = new LinkedList<DomainEvent>();
        for (var event : eventStream) {
            iteratedEvents.add(event);
        }

        assertArrayEquals(domainEvents, iteratedEvents.toArray(DomainEvent[]::new));
    }
}
