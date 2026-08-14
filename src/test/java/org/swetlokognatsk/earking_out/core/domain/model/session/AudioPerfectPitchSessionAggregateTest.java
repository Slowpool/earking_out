package org.swetlokognatsk.earking_out.core.domain.model.session;

import static org.swetlokognatsk.earking_out.core.domain.model.TestAggregateHelper.*;
import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardTestHelper.assertNoSelectedKeys;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchSolutionGenerator;

public final class AudioPerfectPitchSessionAggregateTest {
    private SessionAggregatesFactory sessionAggregatesFactory;

    private static final AudioPerfectPitchSolution SOLUTION = new AudioPerfectPitchSolution(FIRST_NOTE_NUMBER);
    private static final AudioPerfectPitchSolution WRONG_SOLUTION = new AudioPerfectPitchSolution(SOLUTION.keyNumber.increment());

    @Before
    public void setup() {
        DI.refreshDependencies();
        sessionAggregatesFactory = DI.get(SessionAggregatesFactory.class);
    }

    private AudioPerfectPitchExercise getExercise() {
        return new AudioPerfectPitchExercise();
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
        var eventSessionId = event.sessionDto.id;
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

}
