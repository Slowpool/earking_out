package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.FIRST_NOTE_NUMBER;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.*;
import org.junit.Assert.*;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

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

    @Provide
    private Arbitrary<AllGuessesAndPerfectGuessesPair> allGuessesAndPerfectGuesses() {
        var allPairs = generateAllGuessesAndPerfectGuessesPairs();
        return Arbitraries.of(allPairs)
                .list();
    }

    private Collection<AllGuessesAndPerfectGuessesPair> generateAllGuessesAndPerfectGuessesPairs() {
        var allGuesses = randomPositiveOrZeroInt();
        var perfectGuesses = randomPositiveOrZeroInt(allGuesses);
        return new AllGuessesAndPerfectGuessesPair(allGuesses, perfectGuesses);
    }

    private int randomPositiveOrZeroInt() {
        return randomPositiveOrZeroInt(Integer.MAX_VALUE);
    }

    private int randomPositiveOrZeroInt(final int includedUpperBoundary) {
        return ThreadLocalRandom.current().nextInt(0, includedUpperBoundary);
    }

    @Property
    public void perfectGuessesRatio(@ForAll("allGuessesAndPerfectGuesses") final AllGuessesAndPerfectGuessesPair allGuessesAndPerfectGuesses) {

    }

    static record AllGuessesAndPerfectGuessesPair(int numberOfAllGuesses, int numberOfPerfectGuesses) {

    }
}
