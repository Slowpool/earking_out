package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.UserRestrictions;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.puzzles.PerfectPitchPuzzle;

public final class PuzzlesFactory {
    public static Puzzle create(Exercise exercise, UserRestrictions restrictions) {
        var puzzle = new PerfectPitchPuzzle(exercise, restrictions);
        return (Puzzle) puzzle;
    }
}
