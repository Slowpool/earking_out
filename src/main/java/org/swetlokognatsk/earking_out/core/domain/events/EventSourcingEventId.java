package org.swetlokognatsk.earking_out.core.domain.events;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record EventSourcingEventId(UUID id) implements Serializable {
    private static final long serialVersionUID = 1L;

    public EventSourcingEventId {
        Objects.requireNonNull(id);
    }

    public static EventSourcingEventId random() {
        return new EventSourcingEventId(UUID.randomUUID());
    }

    public String toString() {
        return id.toString();
    }
}
