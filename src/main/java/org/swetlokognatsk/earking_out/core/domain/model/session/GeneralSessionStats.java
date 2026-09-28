package org.swetlokognatsk.earking_out.core.domain.model.session;

import java.io.Serializable;
import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;

public final class GeneralSessionStats extends ValueObject implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * If puzzle was successfully guessed on the first guess, it's considered to be
     * completed perfectly. Otherwise, if several guesses were needed to guess
     * successfully, then puzzle is completed (puzzlesCompleted++), although not
     * perfectly (puzzlesCompletedPerfectly remains as-is).
     */
    public final int puzzlesCompletedPerfectly;
    public final int puzzlesCompleted;

    public GeneralSessionStats(final int puzzlesCompletedPerfectly, final int puzzlesCompleted) {
        this.puzzlesCompletedPerfectly = puzzlesCompletedPerfectly;
        this.puzzlesCompleted = puzzlesCompleted;
    }

    public GeneralSessionStats incrementPerfectlyCompletedPuzzles() {
        return new GeneralSessionStats(puzzlesCompletedPerfectly + 1, puzzlesCompleted + 1);
    }

    public GeneralSessionStats incrementCompletedPuzzles() {
        return new GeneralSessionStats(puzzlesCompletedPerfectly, puzzlesCompleted + 1);
    }

    // TODO test
    public double getPerfectlyCompletedPuzzlesRate() {
        return ((double) puzzlesCompletedPerfectly) / puzzlesCompleted;
    }

}
