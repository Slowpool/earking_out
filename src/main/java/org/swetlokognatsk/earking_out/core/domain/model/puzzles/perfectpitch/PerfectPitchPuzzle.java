package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public abstract class PerfectPitchPuzzle<E extends PerfectPitchExercise, S extends Solution> extends Puzzle<E, S> {

    public PerfectPitchPuzzle(final E exercise, final S solution) {
        super(exercise, solution);
    }
}
