package org.swetlokognatsk.earking_out.core.domain.model.session;

import java.io.Serializable;
import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;

// TODO TDD, use OOP, leave it to be ValueObject
public final class SessionStats extends ValueObject implements Serializable {
    private static final long serialVersionUID = 1L;
    // TODO PositiveInt VO
    /**
     * If puzzle was successfully guessed on the first guess, it's considered to be
     * completed perfectly. Otherwise, if several guesses were needed to guess
     * successfully, then puzzle is completed (puzzlesCompleted++), although not
     * perfectly (puzzlesCompletedPerfectly remains as-is).
     */
    public final int puzzlesCompletedPerfectly;
    public final int puzzlesCompleted;

    public SessionStats(final int puzzlesCompletedPerfectly, final int puzzlesCompleted) {
        this.puzzlesCompletedPerfectly = puzzlesCompletedPerfectly;
        this.puzzlesCompleted = puzzlesCompleted;
    }

    public SessionStats incrementPerfectlyCompletedPuzzles() {
        return new SessionStats(puzzlesCompletedPerfectly + 1, puzzlesCompleted + 1);
    }

    public SessionStats incrementCompletedPuzzles() {
        return new SessionStats(puzzlesCompletedPerfectly, puzzlesCompleted + 1);
    }

}
