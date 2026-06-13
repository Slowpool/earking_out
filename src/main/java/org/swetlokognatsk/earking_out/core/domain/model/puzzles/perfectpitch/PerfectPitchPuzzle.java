package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.PerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.PerfectPitchPuzzleGenerator;

public abstract class PerfectPitchPuzzle<E extends PerfectPitchExercise, PCDTO extends PerfectPitchConfigDTO<E>, H extends Hint, PG extends PerfectPitchPuzzleGenerator<PCDTO>> extends Puzzle<E, PCDTO, H, PG> {

    public PerfectPitchPuzzle(final PCDTO puzzleConfigDto, final PG puzzleGenerator) {
        super(puzzleConfigDto, puzzleGenerator);

    }
}
