package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;

public final class RandomAudioPerfectPitchPuzzleGenerator extends RandomPerfectPitchPuzzleGenerator<AudioPerfectPitchConfigAggregate> implements AudioPerfectPitchPuzzleGenerator {

    public RandomAudioPerfectPitchPuzzleGenerator(AudioPerfectPitchConfigAggregate puzzleConfig) {
        super(puzzleConfig);

    }

}
