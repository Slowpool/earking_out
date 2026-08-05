package org.swetlokognatsk.earking_out.core.ports.eventsourcing;

import org.swetlokognatsk.earking_out.inftrastructure.eventsourcing.EventStream;

public interface EventStore {
    void append(final EventStream<?> eventStream);
}
