package org.swetlokognatsk.earking_out.core.domain.model.session;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardTestHelper.assertNoSelectedKeys;

import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchSolutionGenerator;

public final class AudioPerfectPitchSessionAggregateTest {
    protected SessionAggregatesFactory sessionAggregatesFactory;

    protected static final AudioPerfectPitchSolution SOLUTION = new AudioPerfectPitchSolution(FIRST_NOTE_NUMBER);
    protected static final AudioPerfectPitchSolution WRONG_SOLUTION = new AudioPerfectPitchSolution(SOLUTION.keyNumber.increment());

    @Before
    public void setup() {
        DI.deleteSingletons();
        sessionAggregatesFactory = DI.get(SessionAggregatesFactory.class);
    }

    protected AudioPerfectPitchExercise getExercise() {
        return new AudioPerfectPitchExercise();
    }

    protected AudioPerfectPitchSessionAggregate createAggregate() {
        setFakeSolution(SOLUTION);
        return sessionAggregatesFactory.create(getExercise());
    }

    protected void setFakeSolution(final AudioPerfectPitchSolution fakeSolution) {
        FakeAudioPerfectPitchSolutionGenerator.fakeSolution = fakeSolution;
    }

    protected AudioPerfectPitchSessionAggregate createAggregateAndAbort() {
        var aggregate = createAggregate();
        aggregate.abort();
        return aggregate;
    }

    @Test
    public void pianoKeyboardDoesNotHaveSelectedNotesAfterCreate() {
        var aggregate = createAggregate();
        var guessingPianoKeyboard = aggregate.getGuessingPianoKeyboard();

        assertNoSelectedKeys(guessingPianoKeyboard);
    }

    @Test
    public void pianoKeyboardSelectedKeysAfterCorrectPianoKeyPressing() {
        var aggregate = createAggregate();

        aggregate.guessViaPianoKeyPressing(SOLUTION.keyNumber);

        var guessingPianoKeyboard = aggregate.getGuessingPianoKeyboard();
        assertArrayEquals(new PianoKeyNumber[] { SOLUTION.keyNumber }, guessingPianoKeyboard.getSelectedKeyNumbers());
    }

    @Test
    public void pianoKeyboardSelectedKeysAfterWrongPianoKeyPressing() {
        var aggregate = createAggregate();

        aggregate.guessViaPianoKeyPressing(WRONG_SOLUTION.keyNumber);

        var guessingPianoKeyboard = aggregate.getGuessingPianoKeyboard();
        assertArrayEquals(new PianoKeyNumber[] { WRONG_SOLUTION.keyNumber }, guessingPianoKeyboard.getSelectedKeyNumbers());
    }

    @Test
    public void pianoKeyboardSelectedKeysAfterCorrectPianoKeyTouching() {
        var aggregate = createAggregate();

        aggregate.guessViaPianoKeyPressing(SOLUTION.keyNumber);
        aggregate.releasePianoKey();

        var guessingPianoKeyboard = aggregate.getGuessingPianoKeyboard();
        assertArrayEquals(new PianoKeyNumber[0], guessingPianoKeyboard.getSelectedKeyNumbers());
    }

    @Test
    public void pianoKeyboardSelectedKeysAfterWrongPianoKeyTouching() {
        var aggregate = createAggregate();

        aggregate.guessViaPianoKeyPressing(WRONG_SOLUTION.keyNumber);
        aggregate.releasePianoKey();

        var guessingPianoKeyboard = aggregate.getGuessingPianoKeyboard();
        assertArrayEquals(new PianoKeyNumber[0], guessingPianoKeyboard.getSelectedKeyNumbers());
    }

    @Test
    public void releasePianoKeyWhenItIsNotYetPressed() {
        var aggregate = createAggregate();

        try {
            aggregate.releasePianoKey();
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void demonstrateHintAfterAbort() {
        // // TODO check that 1. hearAgain() generates event 2. event's puzzle corresponds to the aggregate's puzzle 3. hearAgain() gives error after abort()
        // var aggregate = createAggregateAndAbort();

        // try {
        //     aggregate.demonstrateHint();
        //     fail();
        // } catch (IllegalStateException e) {
        // }
    }

    @Test
    public void getNumberOfGuessesOfCurrentPuzzleAfterAbort() {
        var aggregate = createAggregateAndAbort();

        try {
            aggregate.getNumberOfGuessesOfCurrentPuzzle();
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void getPuzzleAfterAbort() {
        var aggregate = createAggregateAndAbort();

        try {
            aggregate.getPuzzle();
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void guessAfterAbort() {
        var aggregate = createAggregateAndAbort();

        try {
            aggregate.guess(WRONG_SOLUTION);
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void releasePianoKeyAfterAbort() {
        var aggregate = createAggregateAndAbort();

        try {
            aggregate.releasePianoKey();
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void guessViaPianoKeyPressingAfterAbort() {
        var aggregate = createAggregateAndAbort();

        try {
            aggregate.guessViaPianoKeyPressing(SOLUTION.keyNumber);
            fail();
        } catch (IllegalStateException e) {
        }
    }
}
