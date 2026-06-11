package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.VisualPerfectPitchPuzzleGenerator;

public final class VisualPerfectPitchPuzzle extends PerfectPitchPuzzle<VisualPerfectPitchExercise, VisualPerfectPitchConfigAggregate, UsualHint, VisualPerfectPitchPuzzleGenerator> {

    public VisualPerfectPitchPuzzle(VisualPerfectPitchConfigAggregate config, VisualPerfectPitchPuzzleGenerator puzzleGenerator) {
        super(config, puzzleGenerator);

    }

}
