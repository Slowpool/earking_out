package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.domain.services.app.SessionService;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.AudioPerfectPitchSessionService;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardTestHelper.*;

public final class AudioPerfectPitchNotesGuessingPianoKeyboardAndSessionAggregateIntegrationTest {

    private static final PianoKeyNumber ANY_PIANO_KEY = FIRST_NOTE_NUMBER;
    private static final PianoKeyNumber SOLUTION = ANY_PIANO_KEY;
    // TODO rename WRONG_SOLUTION to WRONG_GUESS everywhere
    private static final PianoKeyNumber WRONG_SOLUTION = SOLUTION.increment();

    private PianoKeyboardService pianoKeyboardService;
    private PianoKeyboardRepository pianoKeyboardRepository;
    private AudioPerfectPitchSessionService sessionService;
    private AudioPerfectPitchSessionRepository sessionRepository;
    private PuzzleConfigRepository puzzleConfigRepository;

    private PianoKeyboardId pianoKeyboardId;
    private AudioPerfectPitchExercise exercise;

    /**
     * make it dirty - means changing it's state by pressing some key, not releasing
     * it
     */
    private void selectSomePianoKeyboardKey() {
        pressPianoKey(ANY_PIANO_KEY);
    }

    private void assertPianoKeyboardDoesNotHaveSelectedKeys() {
        var guessingPianoKeyboard = pianoKeyboardRepository.get(pianoKeyboardId);
        assertNoSelectedKeys(guessingPianoKeyboard);
    }

    private void pressPianoKey(final PianoKeyNumber pianoKeyNumber) {
        pianoKeyboardService.pressPianoKey(pianoKeyboardId, pianoKeyNumber);
    }

    private void assertActiveSessionHasCompletedPuzzles(final int expectedCompletedPuzzles) {
        var session = sessionRepository.getActiveSession();
        assertEquals(expectedCompletedPuzzles, session.getPuzzlesCompleted());
    }

    private void updateNormalizedNotesForPuzzle(final PianoKeyNumber newNormalizedNote) {
        updateNormalizedNotesForPuzzle(new PianoKeyNumber[] { newNormalizedNote });
    }

    private void updateNormalizedNotesForPuzzle(final PianoKeyNumber[] newNormalizedNotes) {
        var puzzleConfig = puzzleConfigRepository.get(exercise);
        puzzleConfig.updateProperty(PerfectPitchConfigAggregate.NORMALIZED_NOTES_FOR_PUZZLE_PROP, newNormalizedNotes);
        puzzleConfigRepository.save(puzzleConfig);
    }

    @Before
    public void setup() {
        pianoKeyboardService = DI.get(PianoKeyboardService.class);
        pianoKeyboardRepository = DI.get(PianoKeyboardRepository.class);
        sessionService = DI.get(AudioPerfectPitchSessionService.class);
        sessionRepository = DI.get(AudioPerfectPitchSessionRepository.class);
        puzzleConfigRepository = DI.get(PuzzleConfigRepository.class);

        pianoKeyboardId = PianoKeyboardId.AUDIO_PERFECT_PITCH_NOTES_GUESSING;
        exercise = (AudioPerfectPitchExercise) pianoKeyboardId.exercise;
    }

    @Test
    public void pianoKeyboardDoesNotHaveSelectedKeysAfterSessionStarting() {
        selectSomePianoKeyboardKey();

        sessionService.start();

        assertPianoKeyboardDoesNotHaveSelectedKeys();
    }

    @Test
    public void pianoKeyboardSelectedKeysAfterCorrectGuess() {
        updateNormalizedNotesForPuzzle(SOLUTION);
        sessionService.start();
        assertActiveSessionHasCompletedPuzzles(0);

        pressPianoKey(SOLUTION);

        assertActiveSessionHasCompletedPuzzles(1);
    }

    @Test
    public void pianoKeyboardSelectedKeysAfterWrongPianoKeyPressing() {
        updateNormalizedNotesForPuzzle(SOLUTION);
        sessionService.start();
        assertActiveSessionHasCompletedPuzzles(0);

        pressPianoKey(WRONG_SOLUTION);

        assertActiveSessionHasCompletedPuzzles(0);
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
    public void releasePianoKeyAfterAbort() {
        var aggregate = createAggregateAndAbort();

        try {
            aggregate.releasePianoKey();
            fail();
        } catch (IllegalStateException e) {
        }
    }
}
