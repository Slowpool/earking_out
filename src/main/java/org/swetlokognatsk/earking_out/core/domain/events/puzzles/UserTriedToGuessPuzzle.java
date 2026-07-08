package org.swetlokognatsk.earking_out.core.domain.events.puzzles;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.guesses.Guess;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public final class UserTriedToGuessPuzzle extends DomainEvent {
    public final SessionId sessionId;
    public final Guess guess;
    public final Solution solution;
    public final boolean success;

    public UserTriedToGuessPuzzle(final LocalDateTime timestamp, final SessionId sessionId, final Guess guess, final Solution solution, final boolean success) {
        super(timestamp);

        this.sessionId = sessionId;
        this.guess = guess;
        this.solution = solution;
        this.success = success;
    }
}
