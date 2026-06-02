package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;

public final class RandomAudioPerfectPitchPuzzleGenerator extends RandomPerfectPitchPuzzleGenerator<AudioPerfectPitchConfig> implements AudioPerfectPitchPuzzleGenerator {

    public RandomAudioPerfectPitchPuzzleGenerator(AudioPerfectPitchConfig puzzleConfig) {
        super(puzzleConfig);

    }

}
