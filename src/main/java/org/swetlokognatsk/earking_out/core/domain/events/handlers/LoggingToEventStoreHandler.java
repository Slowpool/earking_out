package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;

abstract class LoggingToEventStoreHandler {
    protected final EventStore eventStore;

    LoggingToEventStoreHandler(final EventStore eventStore) {
        this.eventStore = eventStore;
    }
}
