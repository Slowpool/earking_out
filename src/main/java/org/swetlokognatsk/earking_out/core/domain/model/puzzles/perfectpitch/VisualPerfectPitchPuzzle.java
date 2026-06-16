package org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.VisualPerfectPitchPuzzleGenerator;

public final class VisualPerfectPitchPuzzle extends PerfectPitchPuzzle<VisualPerfectPitchExercise, VisualPerfectPitchConfigDTO, UsualHint, VisualPerfectPitchPuzzleGenerator> {

    public VisualPerfectPitchPuzzle(VisualPerfectPitchConfigDTO config, VisualPerfectPitchPuzzleGenerator puzzleGenerator) {
        super(config, puzzleGenerator);

    }

}
