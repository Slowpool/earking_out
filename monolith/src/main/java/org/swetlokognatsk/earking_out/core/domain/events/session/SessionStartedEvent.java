package org.swetlokognatsk.earking_out.core.domain.events.session;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

public final class SessionStartedEvent extends SessionEvent {
    private static final long serialVersionUID = 1L;

    public final PuzzleConfigDTO<?> puzzleConfigDto;

    public SessionStartedEvent(final UserId userId, final LocalDateTime timestamp, final SessionId sessionId, final PuzzleConfigDTO<?> puzzleConfigDto) {
        super(userId, timestamp, sessionId);

        this.puzzleConfigDto = puzzleConfigDto;
    }
}
