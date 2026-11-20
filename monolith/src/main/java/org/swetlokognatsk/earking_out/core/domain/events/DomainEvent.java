package org.swetlokognatsk.earking_out.core.domain.events;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import lombok.Data;

// TODO test all events equality. it must be by value, not by reference
@Data
public abstract class DomainEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    public final UserId userId;
    public final LocalDateTime timestamp;

    public DomainEvent(final UserId userId, final LocalDateTime timestamp) {
        this.userId = userId;
        this.timestamp = Objects.requireNonNull(timestamp, "Timestamp cannot be null");
    }
}
