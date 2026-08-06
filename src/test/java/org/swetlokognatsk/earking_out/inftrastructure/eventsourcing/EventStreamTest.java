package org.swetlokognatsk.earking_out.inftrastructure.eventsourcing;

import org.junit.*;
import org.swetlokognatsk.earking_out.inftrastructure.eventsourcing.EventStream;

import static org.junit.Assert.assertArrayEquals;

import java.util.LinkedList;
import java.util.List;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEventsFactory;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

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
