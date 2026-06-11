package org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.puzzles.PuzzleGenerator;

public interface PerfectPitchPuzzleGenerator<PC extends PerfectPitchConfigAggregate<? extends PerfectPitchExercise>> extends PuzzleGenerator {

}
