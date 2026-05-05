package org.swetlokognatsk.earking_out.core.domain.model;

import static org.junit.Assert.*;

import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.FakePuzzleGenerator;
import org.swetlokognatsk.earking_out.core.domain.model.Guess;

public class PuzzleTest {

    private Puzzle createPuzzle(ExerciseNames exerciseName, ExerciseTypes exerciseType) {
        var exercise = ExercisesFactory.create(exerciseName, exerciseType);
        assertNotNull(exercise);
        return PuzzlesFactory.create(exercise, null);
    }

    @Test
    // TODO it checks whether puzzle.create indeed creates the object of `Puzzle` type, that's it. is it ok to test such a thing or it is redundant?
    public void createPerfectPitchPuzzle() {
        var puzzle = createPuzzle(ExerciseNames.PERFECT_PITCH, ExerciseTypes.AUDIO);
        assertTrue(puzzle instanceof Puzzle);
    }

    @Test
    public void checkPerfectPitchPuzzleSolution() {
        // TODO check puzzle.guess(new Guess("Cb"))
        var solution = "4";
        FakePuzzleGenerator.fakeSolution = solution;
        var puzzle = createPuzzle(ExerciseNames.PERFECT_PITCH, ExerciseTypes.AUDIO);
        boolean correctAnswer = puzzle.guess(new Guess(solution));
        assertTrue(correctAnswer);
    }
}
