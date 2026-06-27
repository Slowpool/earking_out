package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.perfectpitch.PerfectPitchHint;
import org.swetlokognatsk.earking_out.core.domain.model.hints.perfectpitch.VisualPerfectPitchHint;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public final class VisualPerfectPitchPuzzle extends PerfectPitchPuzzle<VisualPerfectPitchExercise, VisualPerfectPitchHint> {

    public VisualPerfectPitchPuzzle(final VisualPerfectPitchExercise exercise, final Solution solution, final VisualPerfectPitchHint hint) {
        super(exercise, solution, hint);
    }
}
