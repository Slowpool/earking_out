package org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;

public class PerfectPitchPuzzle<E extends Exercise, PC extends PuzzleConfig<E>> extends Puzzle<E, PC, Hint> {
    public PerfectPitchPuzzle(E exercise, PC config, PuzzleGenerator puzzleGenerator) {
        super(exercise, config, puzzleGenerator);
    }
}
