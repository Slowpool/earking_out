package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.infrastructure.eventsourcing.EventStream;

// TODO i forgot about tdd: create a single test for all of these `Log*Handler` classes
public final class LogEventOnSessionFinishedHandler extends LoggingSessionEventToEventStoreHandler<SessionFinishedEvent> {

    public LogEventOnSessionFinishedHandler(final EventStore eventStore, final PuzzleConfigRepository puzzleConfigRepository, final SessionRepositoryDelegator sessionRepositoryDelegator) {
        super(eventStore, puzzleConfigRepository, sessionRepositoryDelegator);
    }

    // TODO add this event to aggregate
    public void handle(final SessionFinishedEvent event) {
        if (loggingIsEnabled(event.sessionId)) {
            var eventStream = new EventStream<SessionId>(event.sessionId, new DomainEvent[] { event });
            eventStore.append(eventStream);
        }
    }
}
