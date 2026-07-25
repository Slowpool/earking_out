package org.swetlokognatsk.earking_out.core.domain.events.puzzles;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;

public final class NewPuzzleDisplayedEvent extends DomainEvent {
    public final Puzzle<?, ?> puzzle;

    public NewPuzzleDisplayedEvent(final LocalDateTime timestamp, final Puzzle<?, ?> puzzle) {
        super(timestamp);
        this.puzzle = puzzle;
    }

}
