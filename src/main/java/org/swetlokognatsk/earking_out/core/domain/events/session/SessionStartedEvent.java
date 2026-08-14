package org.swetlokognatsk.earking_out.core.domain.events.session;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

// TODO SessionStartedEvent
public final class SessionStartedEvent extends DomainEvent {
    private static final long serialVersionUID = 1L;

    public final SessionId sessionId;
    public final PuzzleConfigDTO<?> puzzleConfigDto;

    public SessionStartedEvent(final LocalDateTime timestamp, final SessionId sessionId, final PuzzleConfigDTO<?> puzzleConfigDto) {
        super(timestamp);

        this.sessionId = sessionId;
        this.puzzleConfigDto = puzzleConfigDto;
    }
}
