package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.infrastructure.eventsourcing.EventStream;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

public final class LogEventOnUserTriedToGuessPuzzleHandler extends LoggingToEventStoreHandler<UserTriedToGuessPuzzleEvent> {

    public LogEventOnUserTriedToGuessPuzzleHandler(final EventStore eventStore, final PuzzleConfigRepository puzzleConfigRepository) {
        super(eventStore, puzzleConfigRepository);
    }

    public void handle(final UserTriedToGuessPuzzleEvent event) {
        var sessionDto = event.sessionDto;
        if (loggingIsEnabled(sessionDto.puzzleConfigDto)) {
            var eventStream = new EventStream<SessionId>(sessionDto.id, new DomainEvent[] { event });
            eventStore.append(eventStream);
        }
    }
}
