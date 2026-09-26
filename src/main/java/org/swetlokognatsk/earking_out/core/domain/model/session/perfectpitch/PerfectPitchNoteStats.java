package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;
import org.swetlokognatsk.earking_out.core.domain.model.music.sounds.Note;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public final class PerfectPitchNoteStats extends ValueObject {

    public final PianoKeyNumber note;
    public final int numberOfAppearances;
    public final int numberOfAllGuesses;
    public final int numberOfPerfectGuesses;
    public final double perfectGuessesRatio;

    public PerfectPitchNoteStats(final PianoKeyNumber note, final int numberOfAppearances, final int numberOfAllGuesses, final int numberOfPerfectGuesses) {
        this.note = note;
        this.numberOfAppearances = numberOfAppearances;
        this.numberOfAllGuesses = numberOfAllGuesses;
        this.numberOfPerfectGuesses = numberOfPerfectGuesses;
        this.perfectGuessesRatio = calcPerfectGuessesRatio(numberOfPerfectGuesses, numberOfAllGuesses);
    }

    private double calcPerfectGuessesRatio(final int numberOfPerfectGuesses, final int numberOfAllGuesses) {
        // TODO
        return 0.0;
    }

    public PerfectPitchNoteStats(final PianoKeyNumber note) {
        this(note, 0, 0, 0);
    }
}
