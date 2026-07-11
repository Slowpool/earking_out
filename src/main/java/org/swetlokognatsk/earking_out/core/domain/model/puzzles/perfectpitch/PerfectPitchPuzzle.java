package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.PerfectPitchSolution;

public abstract class PerfectPitchPuzzle<E extends PerfectPitchExercise, S extends PerfectPitchSolution> extends Puzzle<E, S> {
    private static final long serialVersionUID = 1L;

    public PerfectPitchPuzzle(final E exercise, final S solution) {
        super(exercise, solution);
    }
}
