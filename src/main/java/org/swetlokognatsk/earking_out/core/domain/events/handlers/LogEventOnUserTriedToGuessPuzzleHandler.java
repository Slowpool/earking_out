package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.EventStream;
import org.swetlokognatsk.earking_out.core.ports.config.PersistentPuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

@Service
public final class LogEventOnUserTriedToGuessPuzzleHandler extends LoggingSessionEventToEventStoreHandler<UserTriedToGuessPuzzleEvent> {

    public LogEventOnUserTriedToGuessPuzzleHandler(final EventStore eventStore, final PersistentPuzzleConfigRepository puzzleConfigRepository, final SessionRepositoryDelegator sessionRepositoryDelegator) {
        super(eventStore, puzzleConfigRepository, sessionRepositoryDelegator);
    }

    public void handle(final UserTriedToGuessPuzzleEvent event) {
        if (loggingIsEnabled(event.sessionId, event.userId)) {
            var eventStream = new EventStream<SessionId>(event.sessionId, new DomainEvent[] { event });
            eventStore.append(eventStream);
        }
    }
}
