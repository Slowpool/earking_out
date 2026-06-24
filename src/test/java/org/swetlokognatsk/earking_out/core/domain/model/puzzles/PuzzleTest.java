package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import static org.junit.Assert.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.Guess;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.hints.HintFinder;

public class PuzzleTest {
    protected PuzzleTestHelper puzzleHelper;

    @Before
    public void setup() {
        DI.clear();
        puzzleHelper = new PuzzleTestHelper(DI.get(PuzzleConfigRepository.class));
    }

    @Test
    // TODO it checks whether puzzle.create indeed creates the object of `Puzzle` type, that's it. is it ok to test such a thing or it is redundant?
    public void createPerfectPitchPuzzle() {
        var puzzle = puzzleHelper.createPuzzle(new AudioPerfectPitchExercise());
        assertTrue(puzzle instanceof Puzzle);
    }

    @Test
    public void perfectPitchCorrectPuzzleGuess() {
        var solution = "4";
        var puzzle = puzzleHelper.createPuzzle(new AudioPerfectPitchExercise(), solution);
        boolean correctAnswer = puzzle.guess(new Guess(solution));
        assertTrue(correctAnswer);
    }

    @Test
    public void perfectPitchWrongPuzzleGuess() {
        var puzzle = puzzleHelper.createPuzzle(new AudioPerfectPitchExercise(), "4");
        boolean correctAnswer = puzzle.guess(new Guess("5"));
        assertFalse(correctAnswer);
    }

    // TODO is it normal to test X and then write the test Y which also does X?
    @Test
    /**
     * Well, actually the only thing this test does is checking that Puzzle()
     * constructor successfully assigned the object variable `IHintFinder` as a
     * dependency
     */
    public void hintCorrespondsToSolution() {
        var fakeSolution = "4";
        var exercise = new AudioPerfectPitchExercise();

        var puzzle = puzzleHelper.createPuzzle(exercise, fakeSolution);
        var hint = puzzle.hint;
        var hintFinder = DI.get(HintFinder.class);
        var correctHint = hintFinder.find(exercise, new Solution(fakeSolution));
        var correctHintValue = correctHint.getValue();

        assertEquals(correctHintValue, hint.getValue());
    }
}
