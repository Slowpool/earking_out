package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;

// TODO make it working via tasks, in special thread or asynchronously
abstract class LoggingToEventStoreHandler<DE extends DomainEvent> extends DomainEventHandler<DE> {
    protected final EventStore eventStore;

    LoggingToEventStoreHandler(final EventStore eventStore) {
        this.eventStore = eventStore;
    }
}
