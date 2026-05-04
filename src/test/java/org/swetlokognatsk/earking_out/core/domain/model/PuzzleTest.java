package org.swetlokognatsk.earking_out.core.domain.model;

import static org.junit.Assert.*;

import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;

public class PuzzleTest {
    @Test
    public void createPerfectPitchPuzzle() {
        // arrange
        var exerciseName = ExerciseNames.PERFECT_PITCH;
        var exerciseType = ExerciseTypes.AUDIO;
        var exercise = ExercisesFactory.create(exerciseName, exerciseType);
        // act
        var puzzle = PuzzlesFactory.create(exercise, null);
        // assert
        assertTrue(puzzle instanceof Puzzle);
    }

    @Test
    public void checkPerfectPitchPuzzleSolution() {
        // TODO check puzzle.guess(new Guess("Cb"))
        fail();
    }
}
