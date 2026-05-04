package org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.UserRestrictions;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;

public class PerfectPitchPuzzle extends Puzzle {
    public PerfectPitchPuzzle(Exercise exercise, UserRestrictions restrictions) {
        super(exercise, restrictions);
    }

    protected Solution generateSolution() {
        return new Solution("");
    }
}
