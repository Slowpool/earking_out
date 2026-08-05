package org.swetlokognatsk.earking_out.core.domain.events.session;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;

public final class NewPuzzleCreatedEvent extends DomainEvent {
    public final SessionId sessionId;
    public final Puzzle<?, ?> puzzle;

    public NewPuzzleCreatedEvent(final LocalDateTime timestamp, final SessionId sessionId, final Puzzle<?, ?> puzzle) {
        super(timestamp);

        this.sessionId = sessionId;
        this.puzzle = puzzle;
    }
}
