package org.swetlokognatsk.earking_out.web_infrastructure.adapters.eventsourcing;

import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.infrastructure.eventsourcing.EventStream;

public class JpaEventStore implements EventStore {

    public void append(final EventStream<?> eventStream) {
        // TODO JpaEventStore.append 
    }

}
