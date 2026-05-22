package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.puzzles.PerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.generators.PuzzleGeneratorsFactory;

// TODO all factories need refactoring
public final class PuzzlesFactory {
    public static <E extends Exercise, PC extends PuzzleConfig<E>, P extends Puzzle<E, PC, ?>> P create(E exercise, PC puzzleConfig) {
        var puzzleGenerator = PuzzleGeneratorsFactory.create(puzzleConfig);
        return switch (exercise.name) {
        case PERFECT_PITCH -> (P)new PerfectPitchPuzzle<>(exercise, puzzleConfig, puzzleGenerator);
        default -> throw new RuntimeException("unknown exercise for puzzle: " + exercise.name);
        };
    }
}
