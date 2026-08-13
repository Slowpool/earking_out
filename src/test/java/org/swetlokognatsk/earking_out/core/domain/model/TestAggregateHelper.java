package org.swetlokognatsk.earking_out.core.domain.model;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Array;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;

public final class TestAggregateHelper {

    /**
     * returns only one thrown event WHICH IS INSTANCEOF PROVIDED eventClass. e.g. when aggregate generated events [NewPuzzleCreatedEvent, SessionStartedEvent, HintRepeatingRequested], this method returns only SessionStartedEvent. Whereas if event generated several events of eventClass like [NewPuzzleCreatedEvent, SessionStartedEvent, SessionStartedEvent], it'll throw assert exception (only one was expected)
     */
    public static <E extends DomainEvent> E getOnlyOneThrownEvent(final SessionAggregate<?, ?, ?, ?> aggregate, final Class<E> eventClass) {
        var events = aggregate.flushEvents();
        var stream = events.stream();
        var filteredStream = stream.filter((someEvent) -> someEvent.getClass().equals(eventClass));
        var eventsList = filteredStream.toList();
        assertEquals(1, eventsList.size());
        var event = eventsList.getFirst();
        return (E) event;
    }
}
