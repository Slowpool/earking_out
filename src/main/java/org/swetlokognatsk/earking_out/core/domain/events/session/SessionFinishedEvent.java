package org.swetlokognatsk.earking_out.core.domain.events.session;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

// TODO SessionFinishedEvent
public final class SessionFinishedEvent extends DomainEvent {
    private static final long serialVersionUID = 1L;

    public final SessionId sessionId;
    public final boolean isAborted;

    public SessionFinishedEvent(final LocalDateTime timestamp, final SessionId sessionId, final boolean isAborted) {
        super(timestamp);

        this.sessionId = sessionId;
        this.isAborted = isAborted;
    }
}
