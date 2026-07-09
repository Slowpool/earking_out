package org.swetlokognatsk.earking_out.core.domain.model.session;

import java.io.Serializable;
import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;

// TODO TDD, use OOP, leave it to be ValueObject
public final class SessionStats extends ValueObject implements Serializable {
    private static final long serialVersionUID = 1L;
    // TODO PositiveInt VO
    /**
     * If puzzle was successfully guessed on the first guess, it's considered to be
     * completed correctly. Otherwise, if several guesses were needed to guess
     * successfully, then puzzle is completed (puzzlesCompleted++), although not
     * correctly (puzzlesCompletedCorrectly remains as-is).
     */
    public final int puzzlesCompletedCorrectly;
    public final int puzzlesCompleted;

    public SessionStats(final int puzzlesCompletedCorrectly, final int puzzlesCompleted) {
        this.puzzlesCompletedCorrectly = puzzlesCompletedCorrectly;
        this.puzzlesCompleted = puzzlesCompleted;
    }

    public SessionStats incrementCorrectlyCompletedPuzzle() {
        return new SessionStats(puzzlesCompletedCorrectly + 1, puzzlesCompleted + 1);
    }

    public SessionStats incrementCompletedPuzzle() {
        return new SessionStats(puzzlesCompletedCorrectly, puzzlesCompleted + 1);
    }

}
