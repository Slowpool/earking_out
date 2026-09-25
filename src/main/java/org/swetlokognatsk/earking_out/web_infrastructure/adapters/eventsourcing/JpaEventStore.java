package org.swetlokognatsk.earking_out.web_infrastructure.adapters.eventsourcing;

import org.swetlokognatsk.earking_out.core.domain.events.EventStream;
import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;

public class JpaEventStore implements EventStore {

    public void append(final EventStream<?> eventStream) {
        // TODO JpaEventStore.append 
    }

}
