package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.infrastructure.eventsourcing.EventStream;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;

public final class LogEventOnNewPuzzleCreatedHandler extends LoggingToEventStoreHandler implements DomainEventHandler<NewPuzzleCreatedEvent> {

    public LogEventOnNewPuzzleCreatedHandler(final EventStore eventStore) {
        super(eventStore);
    }
    
    public void handle(final NewPuzzleCreatedEvent event) {
        var eventStream = new EventStream<SessionId>(event.sessionId, new DomainEvent[] { event });
        eventStore.append(eventStream);
    }
}
