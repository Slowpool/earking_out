package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.LatchHint;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public final class VisualPerfectPitchPuzzle extends PerfectPitchPuzzle<VisualPerfectPitchExercise, LatchHint> {

    public VisualPerfectPitchPuzzle(final VisualPerfectPitchExercise exercise, final Solution solution, final LatchHint hint) {
        super(exercise, solution, hint);
    }
}
