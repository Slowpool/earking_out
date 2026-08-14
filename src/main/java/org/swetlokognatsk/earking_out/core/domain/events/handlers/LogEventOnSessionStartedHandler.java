package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.infrastructure.eventsourcing.EventStream;

// TODO check whether `recordStats` is true and if it isn't then do not log
public final class LogEventOnSessionStartedHandler extends LoggingToEventStoreHandler<SessionStartedEvent> {

    public LogEventOnSessionStartedHandler(final EventStore eventStore, final PuzzleConfigRepository puzzleConfigRepository) {
        super(eventStore, puzzleConfigRepository);
    }

    // TODO add this event to aggregate
    public void handle(final SessionStartedEvent event) {
        var sessionDto = event.sessionDto;
        if (loggingIsEnabled(sessionDto.puzzleConfigDto)) {
            var eventStream = new EventStream<SessionId>(sessionDto.id, new DomainEvent[] { event });
            eventStore.append(eventStream);
        }
    }

}
