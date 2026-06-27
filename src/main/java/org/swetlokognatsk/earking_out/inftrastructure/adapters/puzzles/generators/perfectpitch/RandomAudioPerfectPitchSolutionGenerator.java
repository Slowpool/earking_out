package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchSolutionGenerator;

public final class RandomAudioPerfectPitchSolutionGenerator extends RandomPerfectPitchSolutionGenerator<AudioPerfectPitchConfigDTO> implements AudioPerfectPitchSolutionGenerator {

    public RandomAudioPerfectPitchSolutionGenerator(final AudioPerfectPitchConfigDTO puzzleConfigDto) {
        super(puzzleConfigDto);
    }

}
