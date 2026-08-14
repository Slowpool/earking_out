package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.infrastructure.eventsourcing.EventStream;

public final class LogEventOnSessionFinishedHandler extends LoggingToEventStoreHandler <SessionFinishedEvent> {

    public LogEventOnSessionFinishedHandler(final EventStore eventStore) {
        super(eventStore);
    }

    // TODO add this event to aggregate
    public void handle(final SessionFinishedEvent event) {
        var eventStream = new EventStream<SessionId>(event.sessionId, new DomainEvent[] { event });
        eventStore.append(eventStream);
    }
}
