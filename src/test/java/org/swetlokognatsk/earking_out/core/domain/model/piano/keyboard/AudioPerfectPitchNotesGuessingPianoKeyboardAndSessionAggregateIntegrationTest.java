package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import static org.junit.Assert.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.DomainEventHandlers;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.AudioPerfectPitchSessionService;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.SessionService;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardTestHelper.*;

public final class AudioPerfectPitchNotesGuessingPianoKeyboardAndSessionAggregateIntegrationTest {

    private static final PianoKeyNumber ANY_PIANO_KEY = FIRST_NOTE_NUMBER;
    private static final PianoKeyNumber SOLUTION = ANY_PIANO_KEY;
    private static final PianoKeyNumber WRONG_SOLUTION = SOLUTION.increment();

    private PianoKeyboardService pianoKeyboardService;
    private PianoKeyboardRepository pianoKeyboardRepository;
    private AudioPerfectPitchSessionService sessionService;
    private AudioPerfectPitchSessionRepository sessionRepository;
    private PuzzleConfigRepository puzzleConfigRepository;

    private PianoKeyboardId pianoKeyboardId;
    private AudioPerfectPitchExercise exercise;

    private void pressSomePianoKeyboardKey() {
        pressPianoKey(ANY_PIANO_KEY);
    }

    private void selectSomePianoKeyboardKey() {
        pressSomePianoKeyboardKey();
    }

    private void assertPianoKeyboardDoesNotHavePressedKeys() {
        var guessingPianoKeyboard = pianoKeyboardRepository.get(pianoKeyboardId);
        assertNoSelectedKeys(guessingPianoKeyboard);
    }

    private void assertPianoKeyboardDoesNotHaveSelectedKeys() {
        var guessingPianoKeyboard = pianoKeyboardRepository.get(pianoKeyboardId);
        assertNoSelectedKeys(guessingPianoKeyboard);
    }

    private void pressPianoKey(final PianoKeyNumber pianoKeyNumber) {
        pianoKeyboardService.pressPianoKey(pianoKeyboardId, pianoKeyNumber);
    }

    private void releasePianoKey() {
        pianoKeyboardService.releasePianoKey(pianoKeyboardId);
    }

    private void assertActiveSessionHasCompletedPuzzles(final int expectedCompletedPuzzles) {
        var session = getActiveSession();
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

    private AudioPerfectPitchSessionAggregate getActiveSession() {
        return sessionRepository.getActiveSession();
    }

    private AudioPerfectPitchSessionAggregate startSession() {
        sessionService.start();
        return sessionRepository.getActiveSession();
    }

    private AudioPerfectPitchSessionAggregate startAndAbortSession() {
        var activeSession = startSession();
        var activeSessionId = activeSession.getId();
        sessionService.abort(activeSessionId);
        var session = sessionRepository.get(activeSessionId);
        return session;
    }

    private void abortSession(final SessionId sessionId) {
        sessionService.abort(sessionId);
    }

    @Before
    public void setup() {
        DI.refreshDependencies();
        DomainEventHandlers.registerDomainEventHandlers();
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
        var session = startSession();
        selectSomePianoKeyboardKey();
        abortSession(session.getId());

        startSession();

        assertPianoKeyboardDoesNotHaveSelectedKeys();
    }

    @Test
    public void pianoKeyboardDoesNotHavePressedKeysAfterSessionStarting() {
        var session = startSession();
        pressSomePianoKeyboardKey();
        abortSession(session.getId());

        startSession();

        assertPianoKeyboardDoesNotHavePressedKeys();
    }

    @Test
    public void sessionCountsThePuzzleAsCompletedAfterCorrectGuess() {
        updateNormalizedNotesForPuzzle(SOLUTION);
        sessionService.start();
        assertActiveSessionHasCompletedPuzzles(0);

        pressPianoKey(SOLUTION);

        assertActiveSessionHasCompletedPuzzles(1);
    }

    @Test
    public void sessionDoesNotCountThePuzzleAsCompletedAfterWrongGuess() {
        updateNormalizedNotesForPuzzle(SOLUTION);
        sessionService.start();
        assertActiveSessionHasCompletedPuzzles(0);

        pressPianoKey(WRONG_SOLUTION);

        assertActiveSessionHasCompletedPuzzles(0);
    }

    @Test
    public void pressPianoKeyAfterSessionAbort() {
        startAndAbortSession();

        try {
            pressPianoKey(ANY_PIANO_KEY);
            fail();
            // TODO NoActiveSessionException
        } catch (IllegalStateException e) {
            int i = 0;
        }
    }

    @Test
    public void releasePianoKeyAfterAbort() {
        startAndAbortSession();

        try {
            releasePianoKey();
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void pressPianoKeyWhenThereIsNoActiveSession() {
        try {
            pressPianoKey(ANY_PIANO_KEY);
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void releasePianoKeyWhenThereIsNoActiveSession() {
        try {
            releasePianoKey();
            fail();
        } catch (IllegalStateException e) {
        }
    }
}
