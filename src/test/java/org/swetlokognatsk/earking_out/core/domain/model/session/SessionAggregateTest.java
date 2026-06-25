package org.swetlokognatsk.earking_out.core.domain.model.session;

import org.swetlokognatsk.earking_out.core.domain.model.Guess;
import static org.junit.Assert.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.exceptions.InvalidPuzzleConfigException;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.FakeSolutionGenerator;

public final class SessionAggregateTest {
    protected static final String SOLUTION = "any";
    protected static final String WRONG_SOLUTION = "not any";
    protected static final int SEVERAL_PUZZLES = 10;

    // TODO create aggregateRoot, use it everywhere
    protected SessionAggregatesFactory sessionAggregatesFactory;

    @Before
    public void setup() {
        DI.deleteSingletons();
        sessionAggregatesFactory = DI.get(SessionAggregatesFactory.class);
    }

    public SessionAggregateTest() {
    }

    protected SessionAggregate<?, ?, AudioPerfectPitchConfigDTO> createAudioPerfectPitchSession(final String fakeSolution) {
        FakeSolutionGenerator.fakeSolution = fakeSolution;

        return (SessionAggregate<?, ?, AudioPerfectPitchConfigDTO>) sessionAggregatesFactory.create(new AudioPerfectPitchExercise());
    }

    protected SessionAggregate<?, ?, AudioPerfectPitchConfigDTO> createAudioPerfectPitchSession() {
        return createAudioPerfectPitchSession(SOLUTION);
    }

    protected SessionAggregate<?, ?, ?> createSomeSession() {
        return createAudioPerfectPitchSession();
    }

    protected SessionAggregate<?, ?, ?> createSomeSessionAndGuess(final String guess) {
        // TODO why aggregate is considered to have cqrs design?
        var sessionAggregate = createSomeSession();
        sessionAggregate.guess(new Guess(guess));
        return sessionAggregate;
    }

    protected void updateTargetNumberOfPuzzlesOfSomeSession(int targetNumberOfPuzzles) {
        var puzzleConfigRepository = DI.get(PuzzleConfigRepository.class);
        var puzzleConfig = puzzleConfigRepository.get(new AudioPerfectPitchExercise());
        puzzleConfig.updateProperty(PuzzleConfigAggregate.TARGET_NUMBER_OF_PUZZLES_PROP, targetNumberOfPuzzles);
        puzzleConfigRepository.save(puzzleConfig);
    }

    @Test
    public void stateAfterCreating() {
        var sessionAggregate = createSomeSession();
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
        var sessionAggregate = createSomeSession();
        var hint = sessionAggregate.getPuzzle().hint;
        assertNotNull(hint);
    }

    @Test
    public void prevGuessIsSuccessfullBeforeAnyGuessMade() {
        var sessionAggregate = createSomeSession();

        try {
            sessionAggregate.getPrevGuessIsSuccessful();
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void prevGuessIsSuccessfullAfterSuccessfulGuess() {
        var sessionAggregate = createSomeSessionAndGuess(SOLUTION);

        assertTrue(sessionAggregate.getPrevGuessIsSuccessful());
    }

    @Test
    public void prevGuessIsSuccessfullAfterWrongGuess() {
        var sessionAggregate = createSomeSessionAndGuess(WRONG_SOLUTION);

        assertFalse(sessionAggregate.getPrevGuessIsSuccessful());
    }

    @Test
    public void puzzlesCompletedBeforeAnyGuessMade() {
        var sessionAggregate = createSomeSession();

        assertEquals(0, sessionAggregate.getPuzzlesCompleted());
    }

    @Test
    public void puzzlesCompletedAfterSuccessfulGuess() {
        var sessionAggregate = createSomeSessionAndGuess(SOLUTION);

        assertEquals(1, sessionAggregate.getPuzzlesCompleted());
    }

    @Test
    public void puzzlesCompletedAfterLastSuccessfulGuess() {
        updateTargetNumberOfPuzzlesOfSomeSession(1);
        var sessionAggregate = createSomeSessionAndGuess(SOLUTION);

        assertEquals(1, sessionAggregate.getPuzzlesCompleted());
    }

    @Test
    public void puzzlesCompletedAfterWrongGuess() {
        var sessionAggregate = createSomeSessionAndGuess(WRONG_SOLUTION);

        assertEquals(0, sessionAggregate.getPuzzlesCompleted());
    }

    @Test
    public void isNotCompletedAfterWrongGuess() {
        updateTargetNumberOfPuzzlesOfSomeSession(1);
        var sessionAggregate = createAudioPerfectPitchSession(WRONG_SOLUTION);

        assertEquals(SessionStates.IN_PROGRESS, sessionAggregate.getState());
    }

    @Test
    public void isCompletedAfterLastSuccessfulGuess() {
        updateTargetNumberOfPuzzlesOfSomeSession(1);

        var sessionAggregate = createSomeSessionAndGuess(SOLUTION);

        assertEquals(SessionStates.COMPLETED, sessionAggregate.getState());
    }

    @Test
    public void failToGuessAfterLastSuccessfulGuess() {
        updateTargetNumberOfPuzzlesOfSomeSession(1);
        var sessionAggregate = createSomeSessionAndGuess(SOLUTION);

        try {
            sessionAggregate.guess(new Guess(SOLUTION));
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void createSessionWith0TargetNumberOfPuzzles() {
        updateTargetNumberOfPuzzlesOfSomeSession(0);

        try {
            createSomeSession();
            fail();
        } catch (InvalidPuzzleConfigException e) {
        }
    }

    @Test
    public void numberOfGuessesOfCurrentPuzzleAfterCreation() {
        var sessionAggregate = createSomeSession();

        assertEquals(0, sessionAggregate.getNumberOfGuessesOfCurrentPuzzle());
    }

    @Test
    public void numberOfGuessesOfCurrentPuzzleAfterSuccessfulGuess() {
        updateTargetNumberOfPuzzlesOfSomeSession(SEVERAL_PUZZLES);
        var sessionAggregate = createSomeSessionAndGuess(SOLUTION);

        assertEquals(0, sessionAggregate.getNumberOfGuessesOfCurrentPuzzle());
    }

    @Test
    public void numberOfGuessesOfCurrentPuzzleAfterLastSuccessfulGuess() {
        updateTargetNumberOfPuzzlesOfSomeSession(1);
        var sessionAggregate = createSomeSessionAndGuess(SOLUTION);

        try {
            sessionAggregate.getNumberOfGuessesOfCurrentPuzzle();
            fail();
        } catch (IllegalStateException e) {
            // e.message = "puzzle guessing is already finished and there's no current puzzle", consequently there's no any guesses of current puzzle
        }
    }

    @Test
    public void numberOfGuessesOfCurrentPuzzleAfterWrongGuess() {
        var sessionAggregate = createSomeSessionAndGuess(WRONG_SOLUTION);
        assertEquals(1, sessionAggregate.getNumberOfGuessesOfCurrentPuzzle());

        sessionAggregate.guess(new Guess(WRONG_SOLUTION));
        assertEquals(2, sessionAggregate.getNumberOfGuessesOfCurrentPuzzle());
    }

    @Test
    public void puzzlesCompletedCorrectlyAfterCreation() {
        var sessionAggregate = createSomeSession();

        assertEquals(0, sessionAggregate.getPuzzlesCompletedCorrectly());
    }

    @Test
    public void puzzlesCompletedCorrectlyAfterLastSuccessfulGuess() {
        updateTargetNumberOfPuzzlesOfSomeSession(1);
        var sessionAggregate = createSomeSessionAndGuess(SOLUTION);

        assertEquals(1, sessionAggregate.getPuzzlesCompletedCorrectly());
    }

    @Test
    public void puzzlesCompletedCorrectlyAfterSuccessfulGuess() {
        updateTargetNumberOfPuzzlesOfSomeSession(SEVERAL_PUZZLES);

        var sessionAggregate = createSomeSessionAndGuess(SOLUTION);
        assertEquals(1, sessionAggregate.getPuzzlesCompletedCorrectly());

        sessionAggregate.guess(new Guess(SOLUTION));
        assertEquals(2, sessionAggregate.getPuzzlesCompletedCorrectly());
    }

    @Test
    public void puzzlesCompletedCorrectlyAfterWrongGuess() {
        var sessionAggregate = createSomeSessionAndGuess(WRONG_SOLUTION);
        assertEquals(0, sessionAggregate.getPuzzlesCompletedCorrectly());

        sessionAggregate.guess(new Guess(SOLUTION));
        assertEquals(0, sessionAggregate.getPuzzlesCompletedCorrectly());
    }

}
