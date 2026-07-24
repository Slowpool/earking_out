package org.swetlokognatsk.earking_out.core.domain.events;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class DomainEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    public final LocalDateTime timestamp;

    public DomainEvent(final LocalDateTime timestamp) {
        this.timestamp = Objects.requireNonNull(timestamp, "Timestamp cannot be null");
    }
}
