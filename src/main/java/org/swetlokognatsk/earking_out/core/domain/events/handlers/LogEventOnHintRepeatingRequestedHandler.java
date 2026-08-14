package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.infrastructure.eventsourcing.EventStream;

public final class LogEventOnHintRepeatingRequestedHandler extends LoggingToEventStoreHandler<HintRepeatingRequestedEvent> {

    public LogEventOnHintRepeatingRequestedHandler(final EventStore eventStore) {
        super(eventStore);
    }

    public void handle(final HintRepeatingRequestedEvent event) {
        var eventStream = new EventStream<SessionId>(event.sessionId, new DomainEvent[] { event });
        eventStore.append(eventStream);
    }
}
