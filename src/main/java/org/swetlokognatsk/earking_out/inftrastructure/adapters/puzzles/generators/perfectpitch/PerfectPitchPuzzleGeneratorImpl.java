package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.services.puzzles.generators.ConfigBasedPuzzleGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.PerfectPitchPuzzleGenerator;

public abstract class PerfectPitchPuzzleGeneratorImpl<PC extends PerfectPitchConfig<?>> extends ConfigBasedPuzzleGenerator<PC> implements PerfectPitchPuzzleGenerator<PC> {

    public PerfectPitchPuzzleGeneratorImpl(PC puzzleConfig) {
        super(puzzleConfig);

    }

    public Solution generateSolution() {
        // TODO
        return new Solution("");
    }
}
