package org.swetlokognatsk.earking_out.inftrastructure.adapters.eventsourcing;

import org.swetlokognatsk.earking_out.core.ports.eventsourcing.EventStore;
import org.swetlokognatsk.earking_out.inftrastructure.eventsourcing.EventStream;

public final class SQLiteEventStore implements EventStore {

    public void append(final EventStream<?> eventStream) {
        // TODO SQLiteEventStore.append
    }
}
