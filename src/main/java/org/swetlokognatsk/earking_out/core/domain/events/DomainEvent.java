package org.swetlokognatsk.earking_out.core.domain.events;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class DomainEvent {
    public final LocalDateTime timestamp;

    public DomainEvent(final LocalDateTime timestamp) {
        this.timestamp = Objects.requireNonNull(timestamp, "Timestamp cannot be null");
    }
}
