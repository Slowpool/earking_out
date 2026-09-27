package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import java.util.Objects;
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
        this.note = Objects.requireNonNull(note);

        validateNumberOfAppearances(numberOfAppearances);
        this.numberOfAppearances = numberOfAppearances;

        validateNumberOfAllGuesses(numberOfAppearances, numberOfAllGuesses);
        this.numberOfAllGuesses = numberOfAllGuesses;

        validateNumberOfPerfectGuesses(numberOfAllGuesses, numberOfAppearances, numberOfPerfectGuesses);
        this.numberOfPerfectGuesses = numberOfPerfectGuesses;

        this.perfectGuessesRatio = calcPerfectGuessesRatio(numberOfPerfectGuesses, numberOfAllGuesses);
    }

    private void validateNumberOfAppearances(final int numberOfAppearances) {
        if (numberOfAppearances < 0) {
            throw new IllegalArgumentException();
        }
    }

    private void validateNumberOfAllGuesses(final int numberOfAppearances, final int numberOfAllGuesses) {
        if (numberOfAllGuesses < 0) {
            throw new IllegalArgumentException();
        }
        // `numberOfAppearances - 1` because this case is possible:
        // 1. user starts session
        // 2. app generates 0 or many puzzles, user passes them (loop step)
        // 3. app generates next puzzle
        // 4. user aborts the session.
        // at this point, theoretically we can have x generated puzzles and x-1 all guesses
        if (numberOfAppearances == 0 && numberOfAllGuesses != 0) {
            throw new IllegalArgumentException();
        }
        if (numberOfAppearances - 1 > numberOfAllGuesses) {
            throw new IllegalArgumentException();
        }
    }

    private void validateNumberOfPerfectGuesses(final int numberOfAllGuesses, final int numberOfAppearances, final int numberOfPerfectGuesses) {
        if (numberOfPerfectGuesses < 0) {
            throw new IllegalArgumentException();
        }
        if (numberOfPerfectGuesses > numberOfAllGuesses) {
            throw new IllegalArgumentException();
        }
        if (numberOfPerfectGuesses > numberOfAppearances) {
            throw new IllegalArgumentException();
        }
    }

    private double calcPerfectGuessesRatio(final int numberOfPerfectGuesses, final int numberOfAllGuesses) {
        return numberOfAllGuesses == 0
                ? 0.0
                : ((double) numberOfPerfectGuesses) / numberOfAllGuesses;
    }

    public PerfectPitchNoteStats(final PianoKeyNumber note) {
        this(note, 0, 0, 0);
    }
}
