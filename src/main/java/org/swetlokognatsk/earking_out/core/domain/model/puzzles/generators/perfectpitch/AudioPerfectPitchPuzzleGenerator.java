package org.swetlokognatsk.earking_out.core.domain.model.puzzles.generators.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.generators.ConfigBasedPuzzleGenerator;

public class AudioPerfectPitchPuzzleGenerator extends ConfigBasedPuzzleGenerator<AudioPerfectPitchConfig> {

    public AudioPerfectPitchPuzzleGenerator(AudioPerfectPitchConfig puzzleConfig) {
        super(puzzleConfig);

    }

    public Solution generateSolution() {
        // TODO
        return new Solution("");
    }

}
