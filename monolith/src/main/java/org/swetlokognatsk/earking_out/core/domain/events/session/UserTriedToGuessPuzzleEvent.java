package org.swetlokognatsk.earking_out.core.domain.events.session;

import java.time.LocalDateTime;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public final class UserTriedToGuessPuzzleEvent extends SessionEvent {
    private static final long serialVersionUID = 1L;

    public final int puzzleNumber;
    public final Solution guess;
    public final int attempt;
    public final boolean success;

    public UserTriedToGuessPuzzleEvent(final UserId userId, final LocalDateTime timestamp, final SessionId sessionId, final int puzzleNumber, final Solution guess, final int attempt, final boolean success) {
        super(userId, timestamp, sessionId);

        this.puzzleNumber = puzzleNumber;
        this.guess = guess;
        this.attempt = attempt;
        this.success = success;
    }
}
