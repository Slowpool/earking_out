package org.swetlokognatsk.earking_out.core.domain.events;

import java.util.Iterator;

// TODO use immutable collection for events
public record EventStream<ID>(ID id, DomainEvent[] events) implements Iterable<DomainEvent> {

    public Iterator<DomainEvent> iterator() {
        return this.new EventStreamIterator();
    }

    // the most interesting using of inner classes yet i've seen
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
