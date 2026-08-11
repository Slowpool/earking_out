package org.swetlokognatsk.earking_out.infrastructure.eventsourcing;

import java.util.Iterator;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;

// TODO use immutable collection for events
public record EventStream<ID>(ID id, DomainEvent[] events) implements Iterable<DomainEvent> {

    public Iterator<DomainEvent> iterator() {
        return this.new EventStreamIterator();
    }

    // the most interesting using of inner classes yet
    class EventStreamIterator implements Iterator<DomainEvent> {

        private int currentEventIndex = -1;

        public boolean hasNext() {
            return currentEventIndex + 1 < events.length;
        }

        public DomainEvent next() {
            currentEventIndex++;
            return events[currentEventIndex];
        }
    }
}
