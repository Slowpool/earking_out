package org.swetlokognatsk.earking_out.core.ports.eventsourcing;

import org.swetlokognatsk.earking_out.core.domain.events.EventStream;
import org.swetlokognatsk.earking_out.infrastructure.EventSavingException;

// TODO add version to event.
public interface EventStore {
    // // TODO use it
    // void append(final EventStream<?> eventStream) throws EventSavingException;
    void append(final EventStream<?> eventStream);
    <ID> EventStream<ID> getAllEvents(final ID id);
}
