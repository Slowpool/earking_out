package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.VisualPerfectPitchPuzzleGenerator;

public final class RandomVisualPerfectPitchPuzzleGenerator extends RandomPerfectPitchPuzzleGenerator<VisualPerfectPitchConfig> implements VisualPerfectPitchPuzzleGenerator {

    public RandomVisualPerfectPitchPuzzleGenerator(VisualPerfectPitchConfig puzzleConfig) {
        super(puzzleConfig);

    }
}
