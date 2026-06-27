package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.perfectpitch.PerfectPitchHint;

public final class VisualPerfectPitchPuzzle extends PerfectPitchPuzzle<VisualPerfectPitchExercise> {

    public VisualPerfectPitchPuzzle(final VisualPerfectPitchExercise exercise, final Solution solution, final PerfectPitchHint hint) {
        super(exercise, solution, hint);
    }

}
