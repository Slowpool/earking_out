package org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.ports.puzzles.IPuzzleGenerator;

public class PerfectPitchPuzzle<E extends Exercise, PC extends PuzzleConfig<E>> extends Puzzle<E, PC, Hint> {
    public PerfectPitchPuzzle(E exercise, PC config, IPuzzleGenerator puzzleGenerator) {
        super(exercise, config, puzzleGenerator);
    }
}
