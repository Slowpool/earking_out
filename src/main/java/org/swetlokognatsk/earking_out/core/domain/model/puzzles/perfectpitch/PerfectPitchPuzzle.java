package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;

public abstract class PerfectPitchPuzzle<E extends PerfectPitchExercise, H extends Hint> extends Puzzle<E, H> {

    public PerfectPitchPuzzle(final E exercise, final Solution solution, final H hint) {
        super(exercise, solution, hint);
    }
}
