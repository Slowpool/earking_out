package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.infrastructure.eventsourcing.EventStream;

public final class LogEventOnHintRepeatingRequestedHandler extends LoggingToEventStoreHandler<HintRepeatingRequestedEvent> {

    public LogEventOnHintRepeatingRequestedHandler(final EventStore eventStore, final PuzzleConfigRepository puzzleConfigRepository) {
        super(eventStore, puzzleConfigRepository);
    }

    public void handle(final HintRepeatingRequestedEvent event) {
        var sessionDto = event.sessionDto;
        if (loggingIsEnabled(sessionDto.puzzleConfigDto)) {
            var eventStream = new EventStream<SessionId>(sessionDto.id, new DomainEvent[] { event });
            eventStore.append(eventStream);
        }
    }
}
