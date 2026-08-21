package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.infrastructure.eventsourcing.EventStream;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

public final class LogEventOnUserTriedToGuessPuzzleHandler extends LoggingSessionEventToEventStoreHandler<UserTriedToGuessPuzzleEvent> {

    public LogEventOnUserTriedToGuessPuzzleHandler(final EventStore eventStore, final PuzzleConfigRepository puzzleConfigRepository, final SessionRepositoryDelegator sessionRepositoryDelegator) {
        super(eventStore, puzzleConfigRepository, sessionRepositoryDelegator);
    }

    // TODO make puzzleConfigDto caching to not ask for it from database each piano key pressing. keep all logged events as-is, without adding session to them.
    public void handle(final UserTriedToGuessPuzzleEvent event) {
        if (loggingIsEnabled(event.sessionId)) {
            var eventStream = new EventStream<SessionId>(event.sessionId, new DomainEvent[] { event });
            eventStore.append(eventStream);
        }
    }
}
