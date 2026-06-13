package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.VisualPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.VisualPerfectPitchPuzzleGenerator;

public final class RandomVisualPerfectPitchPuzzleGenerator extends RandomPerfectPitchPuzzleGenerator<VisualPerfectPitchConfigDTO> implements VisualPerfectPitchPuzzleGenerator {

    public RandomVisualPerfectPitchPuzzleGenerator(VisualPerfectPitchConfigDTO puzzleConfig) {
        super(puzzleConfig);

    }
}
