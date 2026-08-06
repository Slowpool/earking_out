package org.swetlokognatsk.earking_out.core.domain.events.session;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public final class UserTriedToGuessPuzzleEvent extends DomainEvent {
    private static final long serialVersionUID = 1L;

    public final SessionId sessionId;
    public final int puzzleNumber;
    // TODO generic?
    public final Solution guess;
    public final int attempt;
    public final boolean success;

    public UserTriedToGuessPuzzleEvent(final LocalDateTime timestamp, final SessionId sessionId, final int puzzleNumber, final Solution guess, final int attempt, final boolean success) {
        super(timestamp);

        this.sessionId = sessionId;
        this.puzzleNumber = puzzleNumber;
        this.guess = guess;
        this.attempt = attempt;
        this.success = success;
    }
}
