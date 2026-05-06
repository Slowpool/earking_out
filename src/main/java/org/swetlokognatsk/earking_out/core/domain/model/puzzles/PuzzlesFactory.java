package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.puzzles.PerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.puzzles.IPuzzleGenerator;

// TODO all factories need refactoring
public final class PuzzlesFactory {
    public static <PC extends PuzzleConfig> Puzzle<?, ?, ?> create(Exercise exercise, PC puzzleConfig) {
        Puzzle<?, ?, ?> puzzle;
        if (exercise.name == ExerciseNames.PERFECT_PITCH) {
            var puzzleGenerator = DI.get(IPuzzleGenerator.class);
            puzzle = new PerfectPitchPuzzle(exercise, puzzleConfig, puzzleGenerator);
        }
        else {
            throw new RuntimeException("unknown exercise");
        }
        return puzzle;
        
    }
}
