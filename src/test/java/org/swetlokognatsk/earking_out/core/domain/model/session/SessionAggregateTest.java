package org.swetlokognatsk.earking_out.core.domain.model.session;

import org.swetlokognatsk.earking_out.core.domain.model.Guess;
import static org.junit.Assert.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.FakeSolutionGenerator;

public final class SessionAggregateTest {
    protected static final String FAKE_SOLUTION = "any";
    protected static final String WRONG_FAKE_SOLUTION = "not any";

    // TODO create aggregateRoot, use it everywhere
    protected final SessionAggregatesFactory sessionAggregatesFactory;

    public SessionAggregateTest() {
        sessionAggregatesFactory = DI.get(SessionAggregatesFactory.class);
    }

    protected SessionAggregate<AudioPerfectPitchConfigDTO> createAudioPerfectPitchSession(final String fakeSolution) {
        FakeSolutionGenerator.fakeSolution = fakeSolution;

        return sessionAggregatesFactory.create(new AudioPerfectPitchExercise());
    }

    protected SessionAggregate<AudioPerfectPitchConfigDTO> createAudioPerfectPitchSession() {
        return createAudioPerfectPitchSession(FAKE_SOLUTION);
    }

    protected SessionAggregate<?> createSomeSession() {
        return createAudioPerfectPitchSession();
    }

    protected SessionAggregate<?> createSomeSession(final String fakeSolution) {
        return createAudioPerfectPitchSession(fakeSolution);
    }

    protected SessionAggregate<?> createSomeAggregateAndGuess(final String guess) {
        // TODO why aggregate is considered to have cqrs design?
        var sessionAggregate = createSomeSession();
        sessionAggregate.guess(new Guess(guess));
        return sessionAggregate;
    }

    @Test
    public void stateAfterCreating() {
        var sessionAggregate = createAudioPerfectPitchSession();
        assertEquals(sessionAggregate.getState(), SessionStates.IN_PROGRESS);
    }

    @Test
    public void puzzleClassCorresponds() {
        var sessionAggregate = createAudioPerfectPitchSession();
        assertTrue(sessionAggregate.getPuzzle() instanceof AudioPerfectPitchPuzzle);
    }

    @Test
    public void puzzleConfigClassCorresponds() {
        var sessionAggregate = createAudioPerfectPitchSession();
        var puzzleConfig = sessionAggregate.getPuzzleConfig();
        assertTrue(puzzleConfig instanceof AudioPerfectPitchConfigDTO);
    }

    @Test
    public void hintExistsAfterCreating() {
        var sessionAggregate = createAudioPerfectPitchSession();
        var hint = sessionAggregate.getHint();
        assertNotNull(hint);
    }

    @Test
    public void prevGuessIsSuccessfullBeforeAnyGuessMade() {
        var sessionAggregate = createSomeAggregate();

        try {
            sessionAggregate.getPrevGuessIsSuccessful();
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void prevGuessIsSuccessfullAfterSuccessfulGuess() {
        var sessionAggregate = createSomeAggregateAndGuess(FAKE_SOLUTION);

        assertTrue(sessionAggregate.getPrevGuessIsSuccessful());
    }

    @Test
    public void prevGuessIsSuccessfullAfterWrongGuess() {
        var sessionAggregate = createSomeAggregateAndGuess(WRONG_FAKE_SOLUTION);

        assertFalse(sessionAggregate.getPrevGuessIsSuccessful());
    }

    @Test
    public void puzzlesCompletedBeforeAnyGuessMade() {
        var sessionAggregate = createSomeAggregate();

        assertEquals(0, sessionAggregate.getPuzzlesCompleted());
    }

    @Test
    public void puzzlesCompletedAfterSuccessfulGuess() {
        var sessionAggregate = createSomeAggregateAndGuess(FAKE_SOLUTION);

        assertEquals(1, sessionAggregate.getPuzzlesCompleted());
    }

    @Test
    public void puzzlesCompletedAfterWrongGuess() {
        var sessionAggregate = createSomeAggregateAndGuess(WRONG_FAKE_SOLUTION);

        assertEquals(0, sessionAggregate.getPuzzlesCompleted());
    }

    @Test
    public void isCompletedBeforeLastSuccessfulGuess() {
        var sessionAggregate = createSomeAggregateAndGuess(WRONG_FAKE_SOLUTION);

        assertEquals(SessionStates.IN_PROGRESS, sessionAggregate.getState());
    }

    @Test
    public void isCompletedAfterLastSuccessfulGuess() {
        var puzzleConfigRepository = DI.get(PuzzleConfigRepository.class);
        var puzzleConfig = puzzleConfigRepository.get(new AudioPerfectPitchExercise());
        puzzleConfig.updateProperty(PuzzleConfigAggregate.TARGET_NUMBER_OF_PUZZLES_PROP, 1);
        puzzleConfigRepository.save(puzzleConfig);

        var sessionAggregate = createAudioPerfectPitchSession();
        sessionAggregate.guess(new Guess(FAKE_SOLUTION));

        assertEquals(SessionStates.COMPLETED, sessionAggregate.getState());
        // just checking that it is incremented even when it's finish. kinda support test
        assertEquals(1, sessionAggregate.getPuzzlesCompleted());
    }

}
