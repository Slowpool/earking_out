package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public final class VisualPerfectPitchPuzzle extends PerfectPitchPuzzle<VisualPerfectPitchExercise, Solution> {
    private static final long serialVersionUID = 1L;

    public VisualPerfectPitchPuzzle(final VisualPerfectPitchExercise exercise, final Solution solution) {
        super(exercise, solution);
    }

    public int hashCode() {
        return exercise.hashCode() + solution.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof VisualPerfectPitchPuzzle)) {
            return false;
        }
        var other = (VisualPerfectPitchPuzzle) obj;
        return exercise.equals(other.exercise) && solution.equals(other.solution);
    }
}
