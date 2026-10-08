package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import static org.junit.jupiter.api.Assertions.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.FIRST_NOTE_NUMBER;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

@SpringBootTest
public class PerfectPitchNoteStatsTest {

    private static final PianoKeyNumber ANY_NOTE = FIRST_NOTE_NUMBER;
    private static final int VALID_APPEARANCES = 1;
    private static final int VALID_ALL_GUESSES = 0;
    private static final int VALID_PERFECT_GUESSES = 0;

    @Test
    public void negativeNumberOfAllAppearances() {
        try {
            new PerfectPitchNoteStats(ANY_NOTE, -1, VALID_ALL_GUESSES, VALID_PERFECT_GUESSES);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void negativeNumberOfAllGuesses() {
        try {
            new PerfectPitchNoteStats(ANY_NOTE, VALID_APPEARANCES, -1, VALID_PERFECT_GUESSES);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void nonZeroAllGuessesWithoutAppearances() {
        try {
            new PerfectPitchNoteStats(ANY_NOTE, 0, 1, VALID_PERFECT_GUESSES);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void perfectGuessesGreaterThanNumberOfAllGuesses() {
        try {
            new PerfectPitchNoteStats(ANY_NOTE, VALID_APPEARANCES, 0, 1);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void perfectGuessesGreaterThanAppearances() {
        try {
            // technically under the hood it fails due to `appearances < (allGuesses - 1)`
            new PerfectPitchNoteStats(ANY_NOTE, 0, 1, 1);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void allGuessesLessThanAppearancesByOne() {
        new PerfectPitchNoteStats(ANY_NOTE, 1, 0, VALID_PERFECT_GUESSES);
        new PerfectPitchNoteStats(ANY_NOTE, 2, 1, VALID_PERFECT_GUESSES);
        new PerfectPitchNoteStats(ANY_NOTE, 3, 2, VALID_PERFECT_GUESSES);
        new PerfectPitchNoteStats(ANY_NOTE, 9, 8, VALID_PERFECT_GUESSES);
    }

    @Test
    public void zeroPerfectGuessesRatio() {
        var stats = new PerfectPitchNoteStats(ANY_NOTE, 0, 0, 0);

        assertEquals(0.0, stats.perfectGuessesRatio, 0.0);
        assertFalse(Double.isNaN(stats.perfectGuessesRatio));
    }
}