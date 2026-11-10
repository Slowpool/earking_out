package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import static org.junit.jupiter.api.Assertions.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.FIRST_NOTE_NUMBER;
import org.swetlokognatsk.earking_out.EOSpringBootTest;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import net.jqwik.api.*;

@EOSpringBootTest
public class PerfectPitchNoteStatsPBTTest {
    private static final PianoKeyNumber ANY_NOTE = FIRST_NOTE_NUMBER;

    @Provide
    Arbitrary<AllGuessesAndPerfectGuessesPair> allGuessesAndPerfectGuesses() {
        var someAllGuesses = Arbitraries.integers()
                .greaterOrEqual(0);
        var pair = someAllGuesses.flatMap(allGuesses -> Arbitraries.integers()
                .between(0, allGuesses)
                .map(perfectGuesses -> new AllGuessesAndPerfectGuessesPair(allGuesses, perfectGuesses)));

        return pair;
    }

    @Property
    public void perfectGuessesRatio(@ForAll("allGuessesAndPerfectGuesses") final AllGuessesAndPerfectGuessesPair allGuessesAndPerfectGuesses) {
        var allGuesses = allGuessesAndPerfectGuesses.numberOfAllGuesses();
        var perfectGuesses = allGuessesAndPerfectGuesses.numberOfPerfectGuesses();

        var stats = new PerfectPitchNoteStats(ANY_NOTE, allGuesses, allGuesses, perfectGuesses);

        if (allGuesses == 0) {
            return;
        }
        var expectedRatio = ((double) perfectGuesses) / allGuesses;
        assertEquals(expectedRatio, stats.perfectGuessesRatio, 0.01);
    }

    public static record AllGuessesAndPerfectGuessesPair(int numberOfAllGuesses, int numberOfPerfectGuesses) {

    }
}
