package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.PerfectPitchPuzzleGenerator;

public abstract class PerfectPitchPuzzle<E extends PerfectPitchExercise, PC extends PerfectPitchConfigAggregate<E>, H extends Hint, PG extends PerfectPitchPuzzleGenerator<PC>> extends Puzzle<E, PC, H, PG> {

    public PerfectPitchPuzzle(PC config, PG puzzleGenerator) {
        super(config, puzzleGenerator);

    }
}
