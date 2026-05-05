package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.puzzles.PerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.puzzles.IPuzzleGenerator;

// TODO all factories need refactoring
public final class PuzzlesFactory {
    public static Puzzle create(Exercise exercise, PuzzleConfig puzzleConfig) {
        Puzzle puzzle = null;
        if (exercise.name == ExerciseNames.PERFECT_PITCH) {
            puzzle = new PerfectPitchPuzzle(exercise, puzzleConfig, DI.get(IPuzzleGenerator.class));
        }
        return puzzle;
    }
}
