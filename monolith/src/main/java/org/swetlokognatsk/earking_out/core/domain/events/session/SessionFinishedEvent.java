package org.swetlokognatsk.earking_out.core.domain.events.session;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

public final class SessionFinishedEvent extends SessionEvent {
    private static final long serialVersionUID = 1L;

    public final boolean isAborted;

    public SessionFinishedEvent(final UserId userId, final LocalDateTime timestamp, final SessionId sessionId, final boolean isAborted) {
        super(userId, timestamp, sessionId);
        
        this.isAborted = isAborted;
    }
}
