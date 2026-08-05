package org.swetlokognatsk.earking_out.core.domain.events.session;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

// TODO SessionStartedEvent
public final class SessionStartedEvent extends DomainEvent {
    public final SessionId sessionId;

    public SessionStartedEvent(final LocalDateTime timestamp, final SessionId sessionId) {
        super(timestamp);

        this.sessionId = sessionId;
    }
}
