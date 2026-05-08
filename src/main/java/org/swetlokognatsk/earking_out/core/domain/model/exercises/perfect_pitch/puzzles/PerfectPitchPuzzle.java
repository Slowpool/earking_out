package org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.ports.puzzles.IPuzzleGenerator;

public class PerfectPitchPuzzle extends Puzzle<Exercise, PuzzleConfig, Hint> {
    public PerfectPitchPuzzle(Exercise exercise, PuzzleConfig config, IPuzzleGenerator puzzleGenerator) {
        super(exercise, config, puzzleGenerator);
    }
}
