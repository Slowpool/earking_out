package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;

abstract class LoggingSessionEventToEventStoreHandler<DE extends DomainEvent> extends LoggingToEventStoreHandler<DE> {

    private final SessionRepositoryDelegator sessionRepositoryDelegator;

    public LoggingSessionEventToEventStoreHandler(final EventStore eventStore, final PuzzleConfigRepository puzzleConfigRepository, final SessionRepositoryDelegator sessionRepositoryDelegator) {
        super(eventStore, puzzleConfigRepository);

        this.sessionRepositoryDelegator = sessionRepositoryDelegator;
    }

    protected final boolean loggingIsEnabled(final SessionId sessionId) {
        var session = sessionRepositoryDelegator.get(sessionId);
        return loggingIsEnabled(session.getPuzzleConfig());
    }
}
