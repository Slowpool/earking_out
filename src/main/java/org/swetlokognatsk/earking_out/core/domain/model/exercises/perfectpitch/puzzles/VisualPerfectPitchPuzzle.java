package org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.puzzles;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.VisualPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.VisualPerfectPitchPuzzleGenerator;

public class VisualPerfectPitchPuzzle extends PerfectPitchPuzzle<VisualPerfectPitchExercise, VisualPerfectPitchConfig, UsualHint, VisualPerfectPitchPuzzleGenerator> {

    public VisualPerfectPitchPuzzle(VisualPerfectPitchExercise exercise, VisualPerfectPitchConfig config, VisualPerfectPitchPuzzleGenerator puzzleGenerator) {
        super(exercise, config, puzzleGenerator);

    }

}
