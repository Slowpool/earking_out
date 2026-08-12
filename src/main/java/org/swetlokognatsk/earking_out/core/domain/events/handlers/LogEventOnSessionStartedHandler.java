package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.infrastructure.eventsourcing.EventStream;

// TODO check whether `recordStats` is true and if it isn't then do not log
public final class LogEventOnSessionStartedHandler extends LoggingToEventStoreHandler implements DomainEventHandler<SessionStartedEvent> {

    public LogEventOnSessionStartedHandler(final EventStore eventStore) {
        super(eventStore);
    }

    // TODO add this event to aggregate
    public void handle(final SessionStartedEvent event) {
        var eventStream = new EventStream<SessionId>(event.sessionId, new DomainEvent[] { event });
        eventStore.append(eventStream);
    }

}
