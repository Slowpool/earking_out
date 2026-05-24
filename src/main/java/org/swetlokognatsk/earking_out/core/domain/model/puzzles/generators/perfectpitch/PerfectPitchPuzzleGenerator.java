package org.swetlokognatsk.earking_out.core.domain.model.puzzles.generators.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.generators.ConfigBasedPuzzleGenerator;

public class PerfectPitchPuzzleGenerator<PC extends PerfectPitchConfig<?>> extends ConfigBasedPuzzleGenerator<PC> {

    public PerfectPitchPuzzleGenerator(PC puzzleConfig) {
        super(puzzleConfig);

    }

    public Solution generateSolution() {
        // TODO
        return new Solution("");
    }
}
