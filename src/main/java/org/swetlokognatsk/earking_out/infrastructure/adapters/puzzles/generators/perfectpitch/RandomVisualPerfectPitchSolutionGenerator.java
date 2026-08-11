package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.VisualPerfectPitchSolutionGenerator;

public final class RandomVisualPerfectPitchSolutionGenerator extends RandomPerfectPitchSolutionGenerator<VisualPerfectPitchConfigDTO> implements VisualPerfectPitchSolutionGenerator {

    public RandomVisualPerfectPitchSolutionGenerator(final VisualPerfectPitchConfigDTO puzzleConfig) {
        super(puzzleConfig);
    }
}
