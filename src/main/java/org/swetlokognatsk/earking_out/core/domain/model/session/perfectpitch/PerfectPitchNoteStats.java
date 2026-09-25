package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;
import org.swetlokognatsk.earking_out.core.domain.model.music.sounds.Note;

public final class PerfectPitchNoteStats extends ValueObject {

    public final Note note;
    public final int numberOfAppearances;
    public final int numberOfAllGuesses;
    public final int numberOfPerfectGuesses;
    public final double perfectGuessesRatio;

    public PerfectPitchNoteStats(final Note note, final int numberOfAppearances, final int numberOfAllGuesses, final int numberOfPerfectGuesses, final double perfectGuessesRatio) {
        this.note = note;
        this.numberOfAppearances = numberOfAppearances;
        this.numberOfAllGuesses = numberOfAllGuesses;
        this.numberOfPerfectGuesses = numberOfPerfectGuesses;
        this.perfectGuessesRatio = perfectGuessesRatio;
    }
}
