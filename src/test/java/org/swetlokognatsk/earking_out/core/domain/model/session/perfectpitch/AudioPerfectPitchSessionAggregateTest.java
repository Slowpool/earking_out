package org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch;

import static org.swetlokognatsk.earking_out.core.domain.model.TestAggregateHelper.*;
import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardTestHelper.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.InvalidTextNoteException;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.OutOfRangeTextNoteException;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchSolutionGenerator;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;

public final class AudioPerfectPitchSessionAggregateTest {
    private SessionAggregatesFactory sessionAggregatesFactory;

    private static final AudioPerfectPitchSolution SOLUTION = new AudioPerfectPitchSolution(FIRST_NOTE_NUMBER.increment());
    private static final AudioPerfectPitchSolution WRONG_SOLUTION = new AudioPerfectPitchSolution(SOLUTION.keyNumber.increment());
    private static final String COMPLETELY_INVALID_TEXT_NOTE = "bazinga";
    private static final String TEXT_NOTE_SOLUTION_1 = "C#1";
    private static final String TEXT_NOTE_SOLUTION_2 = "Db1";
    private static final String WRONG_TEXT_NOTE_SOLUTION_1 = "C1";
    private static final String WRONG_TEXT_NOTE_SOLUTION_2 = "D1";
    private static final String OUT_OF_RANGE_TEXT_NOTE = "C#9";

    @Before
    public void setup() {
        DI.refreshDependencies();
        sessionAggregatesFactory = DI.get(SessionAggregatesFactory.class);
    }

    private AudioPerfectPitchExercise getExercise() {
        return AUDIO_PERFECT_PITCH_EXERCISE;
    }

    private AudioPerfectPitchSessionAggregate createAggregate() {
        setFakeSolution(SOLUTION);
        return sessionAggregatesFactory.create(getExercise());
    }

    private AudioPerfectPitchSessionAggregate createAggregateAndFlushEvents() {
        var aggregate = createAggregate();
        aggregate.flushEvents();
        return aggregate;
    }

    private void setFakeSolution(final AudioPerfectPitchSolution fakeSolution) {
        FakeAudioPerfectPitchSolutionGenerator.fakeSolution = fakeSolution;
    }

    private AudioPerfectPitchSessionAggregate createAggregateAndAbort() {
        var aggregate = createAggregate();
        aggregate.abort();
        return aggregate;
    }

    // TODO further methods should be in parent abstract `SessionAggregateTest` class
    private void assertNumberOfPuzzleInUserTriedToGuessEventEquals(final SessionAggregate<?, ?, ?, ?> aggregate, final int puzzleNumber) {
        var userTriedToGuessEvent = getOnlyOneThrownEvent(aggregate, UserTriedToGuessPuzzleEvent.class);
        assertEquals(puzzleNumber, userTriedToGuessEvent.puzzleNumber);
    }

    private void assertUserTriedToGuessEventHasAttempt(final SessionAggregate<?, ?, ?, ?> aggregate, final int attempt) {
        var event = getOnlyOneThrownEvent(aggregate, UserTriedToGuessPuzzleEvent.class);
        assertEquals(attempt, event.attempt);
    }

    private void assertUserTriedToGuessEventHasSuccess(final SessionAggregate<?, ?, ?, ?> aggregate) {
        var event = getOnlyOneThrownEvent(aggregate, UserTriedToGuessPuzzleEvent.class);
        assertEquals(true, event.success);
    }

    private void assertUserTriedToGuessEventDoesNotHaveSuccess(final SessionAggregate<?, ?, ?, ?> aggregate) {
        var event = getOnlyOneThrownEvent(aggregate, UserTriedToGuessPuzzleEvent.class);
        assertEquals(false, event.success);
    }

    @Test
    public void demonstrateHintGivesCorrectEvent() {
        var aggregate = createAggregateAndFlushEvents();

        aggregate.demonstrateHintAgain();
        var events = aggregate.flushEvents();
        assertEquals(1, events.size());

        var event = events.getFirst();
        assertTrue(HintRepeatingRequestedEvent.class.equals(event.getClass()));
    }

    @Test
    public void demonstrateHintGivesEventWithCorrespondingPuzzle() {
        var aggregate = createAggregateAndFlushEvents();

        aggregate.demonstrateHintAgain();
        var events = aggregate.flushEvents();
        var event = (HintRepeatingRequestedEvent) events.getFirst();

        var aggregatePuzzle = aggregate.getPuzzle();
        var eventPuzzle = event.puzzle;
        assertTrue(aggregatePuzzle.equals(eventPuzzle));
    }

    @Test
    public void demonstrateHintAfterAbort() {
        var aggregate = createAggregateAndAbort();

        try {
            aggregate.demonstrateHintAgain();
            fail();
        } catch (IllegalStateException e) {
        }
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
    public void guessViaPianoKeyPressingAfterAbort() {
        var aggregate = createAggregateAndAbort();

        try {
            aggregate.guessViaPianoKeyPressing(SOLUTION.keyNumber);
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void successfulGuessCreatesUserTriedToGuessEvent() {
        var aggregate = createAggregateAndFlushEvents();

        aggregate.guess(SOLUTION);

        var event = getOnlyOneThrownEvent(aggregate, UserTriedToGuessPuzzleEvent.class);
        assertTrue(event instanceof UserTriedToGuessPuzzleEvent);
    }

    @Test
    public void guessCreatesUserTriedToGuessEventWithCorrectSessionId() {
        var aggregate = createAggregateAndFlushEvents();
        var aggregateSessionId = aggregate.getId();

        aggregate.guess(SOLUTION);

        var event = getOnlyOneThrownEvent(aggregate, UserTriedToGuessPuzzleEvent.class);
        var eventSessionId = event.sessionId;
        assertEquals(aggregateSessionId, eventSessionId);
    }

    @Test
    public void successfulGuessCreatesUserTriedToGuessEventWithCorrectPuzzleNumber() {
        var aggregate = createAggregateAndFlushEvents();

        var targetNumberOfPuzzles = aggregate.getPuzzleConfig().targetNumberOfPuzzles;

        for (var puzzleNumber = 1; puzzleNumber <= targetNumberOfPuzzles; puzzleNumber++) {
            aggregate.guess(SOLUTION);
            assertNumberOfPuzzleInUserTriedToGuessEventEquals(aggregate, puzzleNumber);
        }
    }

    @Test
    public void puzzleNumberInUserTriedToGuessEventRemainsAsIsAfterWrongGuess() {
        var aggregate = createAggregateAndFlushEvents();

        var targetNumberOfPuzzles = aggregate.getPuzzleConfig().targetNumberOfPuzzles;

        for (var puzzleNumber = 1; puzzleNumber <= targetNumberOfPuzzles; puzzleNumber++) {
            for (int i = 0; i < 3; i++) {
                aggregate.guess(WRONG_SOLUTION);
                assertNumberOfPuzzleInUserTriedToGuessEventEquals(aggregate, puzzleNumber);
            }
            aggregate.guess(SOLUTION);
            aggregate.flushEvents();
        }
    }

    @Test
    public void guessCorrespondsToOriginalGuessInUserTriedToGuessEvent() {
        var aggregate = createAggregateAndFlushEvents();

        aggregate.guess(SOLUTION);
        var event = getOnlyOneThrownEvent(aggregate, UserTriedToGuessPuzzleEvent.class);
        assertEquals(SOLUTION, event.guess);
    }

    @Test
    public void attemptIsIncrementedAfterWrongGuessInUserTriedToGuessEvent() {
        var aggregate = createAggregateAndFlushEvents();

        for (int expectedAttempt = 1; expectedAttempt <= 3; expectedAttempt++) {
            aggregate.guess(WRONG_SOLUTION);
            assertUserTriedToGuessEventHasAttempt(aggregate, expectedAttempt);
        }
    }

    @Test
    public void attemptIsResetAfterSucccessfulGuessInUserTriedToGuessEvent() {
        var aggregate = createAggregateAndFlushEvents();

        var targetNumberOfPuzzles = aggregate.getPuzzleConfig().targetNumberOfPuzzles;

        for (var i = 0; i < targetNumberOfPuzzles; i++) {
            aggregate.guess(SOLUTION);
            assertUserTriedToGuessEventHasAttempt(aggregate, 1);
        }
    }

    @Test
    public void attemptIsResetAfterWrongThenSucccessfulGuessInUserTriedToGuessEvent() {
        var aggregate = createAggregateAndFlushEvents();

        var targetNumberOfPuzzles = aggregate.getPuzzleConfig().targetNumberOfPuzzles;

        for (var i = 0; i < targetNumberOfPuzzles; i++) {
            aggregate.guess(WRONG_SOLUTION);
            assertUserTriedToGuessEventHasAttempt(aggregate, 1);

            aggregate.guess(SOLUTION);
            assertUserTriedToGuessEventHasAttempt(aggregate, 2);
        }
    }

    @Test
    public void successIsTrueAfterSucccessfulGuessInUserTriedToGuessEvent() {
        var aggregate = createAggregateAndFlushEvents();

        aggregate.guess(SOLUTION);
        assertUserTriedToGuessEventHasSuccess(aggregate);
    }

    @Test
    public void successIsFalseAfterWrongGuessInUserTriedToGuessEvent() {
        var aggregate = createAggregateAndFlushEvents();

        aggregate.guess(WRONG_SOLUTION);
        assertUserTriedToGuessEventDoesNotHaveSuccess(aggregate);
    }

    @Test
    public void guessViaTextNoteWithInvalidNote() {
        var aggregate = createAggregateAndFlushEvents();

        try {
            aggregate.guessViaTextNote(COMPLETELY_INVALID_TEXT_NOTE);
            fail();
        } catch (InvalidTextNoteException e) {
        } catch (OutOfRangeTextNoteException e) {
            fail();
        }
    }

    @Test
    public void guessViaTextWithOutOfRangeNote() {
        var aggregate = createAggregateAndFlushEvents();

        try {
            aggregate.guessViaTextNote(OUT_OF_RANGE_TEXT_NOTE);
            fail();
        } catch (OutOfRangeTextNoteException e) {
        } catch (InvalidTextNoteException e) {
            fail();
        }
    }

    @Test
    public void successfulGuessViaTextNote() {
        var aggregate = createAggregateAndFlushEvents();

        try {
            aggregate.guessViaTextNote(TEXT_NOTE_SOLUTION_1);
        } catch (Throwable e) {
            fail();
        }

        assertTrue(aggregate.getPrevGuessIsSuccessful());
    }

    @Test
    public void alternativeSuccessfulGuessViaTextNote() {
        var aggregate = createAggregateAndFlushEvents();

        try {
            aggregate.guessViaTextNote(TEXT_NOTE_SOLUTION_2);
        } catch (Throwable e) {
            fail();
        }

        assertTrue(aggregate.getPrevGuessIsSuccessful());
    }

    @Test
    public void wrongGuessViaTextNote() {
        var aggregate = createAggregateAndFlushEvents();

        try {
            aggregate.guessViaTextNote(WRONG_TEXT_NOTE_SOLUTION_1);
        } catch (Throwable e) {
            fail();
        }

        assertFalse(aggregate.getPrevGuessIsSuccessful());
    }

    @Test
    public void anotherWrongGuessViaTextNote() {
        var aggregate = createAggregateAndFlushEvents();

        try {
            aggregate.guessViaTextNote(WRONG_TEXT_NOTE_SOLUTION_2);
        } catch (Throwable e) {
            fail();
        }

        assertFalse(aggregate.getPrevGuessIsSuccessful());
    }

}
