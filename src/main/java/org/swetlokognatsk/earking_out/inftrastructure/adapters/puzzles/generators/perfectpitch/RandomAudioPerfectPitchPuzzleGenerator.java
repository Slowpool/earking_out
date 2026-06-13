package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;

public final class RandomAudioPerfectPitchPuzzleGenerator extends RandomPerfectPitchPuzzleGenerator<AudioPerfectPitchConfigDTO> implements AudioPerfectPitchPuzzleGenerator {

    public RandomAudioPerfectPitchPuzzleGenerator(final AudioPerfectPitchConfigDTO puzzleConfig) {
        super(puzzleConfig);

    }

}
