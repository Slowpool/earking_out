package org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.generators.perfectpitch.PerfectPitchPuzzleGenerator;

public class PerfectPitchPuzzle<E extends PerfectPitchExercise, PC extends PerfectPitchConfig<E>, H extends Hint, PG extends PerfectPitchPuzzleGenerator<PC>> extends Puzzle<E, PC, H, PG> {

    public PerfectPitchPuzzle(E exercise, PC config, PG puzzleGenerator) {
        super(exercise, config, puzzleGenerator);

    }
}
