package org.swetlokognatsk.earking_out.core.ports.eventsourcing;

import org.swetlokognatsk.earking_out.infrastructure.EventSavingException;
import org.swetlokognatsk.earking_out.infrastructure.eventsourcing.EventStream;

public interface EventStore {
    // // TODO use it
    // void append(final EventStream<?> eventStream) throws EventSavingException;
    void append(final EventStream<?> eventStream);
    // TODO <ID> EventStream<ID> getAllEvents(final ID id, final ??? type);
}
