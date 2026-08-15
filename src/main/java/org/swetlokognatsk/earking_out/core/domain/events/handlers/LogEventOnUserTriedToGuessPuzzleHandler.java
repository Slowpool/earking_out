package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.infrastructure.eventsourcing.EventStream;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

public final class LogEventOnUserTriedToGuessPuzzleHandler extends LoggingToEventStoreHandler<UserTriedToGuessPuzzleEvent> {

    public LogEventOnUserTriedToGuessPuzzleHandler(final EventStore eventStore) {
        super(eventStore);
    }

    // TODO make puzzleConfigDto caching to not ask for it from database each piano key pressing. keep all logged events as-is, without adding session to them.
    public void handle(final UserTriedToGuessPuzzleEvent event) {
        var eventStream = new EventStream<SessionId>(event.sessionId, new DomainEvent[] { event });
        eventStore.append(eventStream);
    }
}
