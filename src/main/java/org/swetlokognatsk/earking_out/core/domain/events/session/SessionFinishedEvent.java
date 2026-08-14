package org.swetlokognatsk.earking_out.core.domain.events.session;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionDTO;

public final class SessionFinishedEvent extends DomainEvent {
    private static final long serialVersionUID = 1L;

    public final SessionDTO<?, ?, ?, ?> sessionDto;

    public SessionFinishedEvent(final LocalDateTime timestamp, final SessionDTO<?, ?, ?, ?> sessionDto) {
        super(timestamp);

        this.sessionDto = sessionDto;
    }
}
