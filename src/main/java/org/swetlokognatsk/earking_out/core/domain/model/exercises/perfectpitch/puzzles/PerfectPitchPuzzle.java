package org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;

public class PerfectPitchPuzzle<E extends PerfectPitchExercise, PC extends PerfectPitchConfig<E>, H extends Hint> extends Puzzle<E, PC, H> {
    public PerfectPitchPuzzle(E exercise, PC config, PuzzleGenerator puzzleGenerator) {
        super(exercise, config, puzzleGenerator);
    }
}
