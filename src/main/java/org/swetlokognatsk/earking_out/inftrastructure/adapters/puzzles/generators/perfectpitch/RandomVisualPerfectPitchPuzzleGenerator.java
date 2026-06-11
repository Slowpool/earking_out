package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.VisualPerfectPitchPuzzleGenerator;

public final class RandomVisualPerfectPitchPuzzleGenerator extends RandomPerfectPitchPuzzleGenerator<VisualPerfectPitchConfigAggregate> implements VisualPerfectPitchPuzzleGenerator {

    public RandomVisualPerfectPitchPuzzleGenerator(VisualPerfectPitchConfigAggregate puzzleConfig) {
        super(puzzleConfig);

    }
}
