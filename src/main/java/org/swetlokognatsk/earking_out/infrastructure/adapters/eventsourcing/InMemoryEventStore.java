package org.swetlokognatsk.earking_out.infrastructure.adapters.eventsourcing;

import java.util.HashSet;
import java.util.Set;

import org.swetlokognatsk.earking_out.core.ports.events.DomainEventJsonSerializer;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.infrastructure.eventsourcing.EventStream;

public class InMemoryEventStore implements EventStore {

    private final Set<EventStream<?>> eventStreams = new HashSet<>();;

    private final DomainEventJsonSerializer domainEventJsonSerializer;

    public InMemoryEventStore(final DomainEventJsonSerializer domainEventJsonSerializer) {
        this.domainEventJsonSerializer = domainEventJsonSerializer;
    }

    // // TODO use it
    // public void append(final EventStream<?> eventStream) throws EventSavingException {
    public void append(final EventStream<?> eventStream) {
        eventStreams.add(eventStream);
    }
}
