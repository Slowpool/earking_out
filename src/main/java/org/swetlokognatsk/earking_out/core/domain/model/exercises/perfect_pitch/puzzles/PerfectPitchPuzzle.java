package org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.UserRestrictions;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.ports.puzzles.IPuzzleGenerator;

public class PerfectPitchPuzzle extends Puzzle {
    public PerfectPitchPuzzle(Exercise exercise, UserRestrictions restrictions, IPuzzleGenerator puzzleGenerator) {
        super(exercise, restrictions, puzzleGenerator);
    }
}
