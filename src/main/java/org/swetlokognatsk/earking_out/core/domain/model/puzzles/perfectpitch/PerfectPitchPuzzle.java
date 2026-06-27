package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.perfectpitch.PerfectPitchHint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;

public abstract class PerfectPitchPuzzle<E extends PerfectPitchExercise> extends Puzzle<E, PerfectPitchHint> {

    public PerfectPitchPuzzle(final E exercise, final Solution solution, final PerfectPitchHint hint) {
        super(exercise, solution, hint);
    }
}
