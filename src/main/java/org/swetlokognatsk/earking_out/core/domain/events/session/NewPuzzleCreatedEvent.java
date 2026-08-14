package org.swetlokognatsk.earking_out.core.domain.events.session;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionDTO;

public final class NewPuzzleCreatedEvent extends DomainEvent {
    private static final long serialVersionUID = 1L;

    public final SessionDTO<?, ?, ?, ?> sessionDto;
    public final Puzzle<?, ?> puzzle;

    public NewPuzzleCreatedEvent(final LocalDateTime timestamp, final SessionDTO<?, ?, ?, ?> sessionDto, final Puzzle<?, ?> puzzle) {
        super(timestamp);

        this.sessionDto = sessionDto;
        this.puzzle = puzzle;
    }
}
