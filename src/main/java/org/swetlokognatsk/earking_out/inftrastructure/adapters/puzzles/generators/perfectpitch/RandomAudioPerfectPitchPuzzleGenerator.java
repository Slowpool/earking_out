package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;

public class RandomAudioPerfectPitchPuzzleGenerator extends PerfectPitchPuzzleGeneratorImpl<AudioPerfectPitchConfig> implements AudioPerfectPitchPuzzleGenerator {

    public RandomAudioPerfectPitchPuzzleGenerator(AudioPerfectPitchConfig puzzleConfig) {
        super(puzzleConfig);

    }

}
