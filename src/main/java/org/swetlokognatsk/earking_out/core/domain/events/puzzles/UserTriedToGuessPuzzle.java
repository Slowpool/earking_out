package org.swetlokognatsk.earking_out.core.domain.events.puzzles;

import java.time.LocalDateTime;
import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.Guess;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;

public final class UserTriedToGuessPuzzle extends DomainEvent {
    public final UUID sessionId;
    public final Guess guess;
    public final Solution solution;
    public final boolean success;

    public UserTriedToGuessPuzzle(final LocalDateTime timestamp, final UUID sessionId, final Guess guess, final Solution solution, final boolean success) {
        super(timestamp);

        this.sessionId = sessionId;
        this.guess = guess;
        this.solution = solution;
        this.success = success;
    }
}
