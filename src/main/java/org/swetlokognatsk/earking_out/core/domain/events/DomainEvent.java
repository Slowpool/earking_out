package org.swetlokognatsk.earking_out.core.domain.events;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class DomainEvent {
    LocalDateTime timestamp;

    public DomainEvent(LocalDateTime timestamp) {
        Objects.requireNonNull(timestamp, "Timestamp cannot be null");

        this.timestamp = timestamp;
    }
}
