package org.swetlokognatsk.earking_out.core.domain.events.session;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

// TODO SessionFinishedEvent
public final class SessionFinishedEvent extends DomainEvent {
    public final SessionId sessionId;

    public SessionFinishedEvent(final LocalDateTime timestamp, final SessionId sessionId) {
        super(timestamp);

        this.sessionId = sessionId;
    }
}
