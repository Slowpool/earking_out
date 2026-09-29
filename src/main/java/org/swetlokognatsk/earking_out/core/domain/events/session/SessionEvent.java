package org.swetlokognatsk.earking_out.core.domain.events.session;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

public abstract class SessionEvent extends DomainEvent {
    private static final long serialVersionUID = 1L;

    public final SessionId sessionId;

    SessionEvent(final UserId userId, final LocalDateTime timestamp, final SessionId sessionId) {
        super(userId, timestamp);
        this.sessionId = sessionId;
    }
}
