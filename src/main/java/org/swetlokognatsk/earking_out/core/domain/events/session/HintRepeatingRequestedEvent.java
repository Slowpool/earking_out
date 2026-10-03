package org.swetlokognatsk.earking_out.core.domain.events.session;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

public final class HintRepeatingRequestedEvent extends SessionEvent {
    private static final long serialVersionUID = 1L;

    public final Puzzle<?, ?> puzzle;

    public HintRepeatingRequestedEvent(final UserId userId, final LocalDateTime timestamp, final SessionId sessionId, final Puzzle<?, ?> puzzle) {
        super(userId, timestamp, sessionId);

        this.puzzle = puzzle;
    }
}
