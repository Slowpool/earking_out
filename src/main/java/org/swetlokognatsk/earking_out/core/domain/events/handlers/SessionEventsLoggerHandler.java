package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.inftrastructure.eventsourcing.EventStream;

// TODO quite awkward
public record SessionEventsLoggerHandler(EventStore eventStore) {

    // TODO add this event to aggregate
    public void handleSessionStartedEvent(final SessionStartedEvent event) {
        var eventStream = new EventStream<SessionId>(event.sessionId, new DomainEvent[] { event });
        eventStore.append(eventStream);
    }

    public void handleNewPuzzleCreatedEvent(final NewPuzzleCreatedEvent event) {
        var eventStream = new EventStream<SessionId>(event.sessionId, new DomainEvent[] { event });
        eventStore.append(eventStream);
    }

    public void handleUserTriedToGuessPuzzleEvent(final UserTriedToGuessPuzzleEvent event) {
        var eventStream = new EventStream<SessionId>(event.sessionId, new DomainEvent[] { event });
        eventStore.append(eventStream);
    }

    // TODO add this event to aggregate
    public void handleSessionFinishedEvent(final SessionFinishedEvent event) {
        var eventStream = new EventStream<SessionId>(event.sessionId, new DomainEvent[] { event });
        eventStore.append(eventStream);
    }

}
