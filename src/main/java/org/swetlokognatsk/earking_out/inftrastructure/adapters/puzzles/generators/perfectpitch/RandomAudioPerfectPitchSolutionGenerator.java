package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import java.util.function.IntFunction;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchSolutionGenerator;

public final class RandomAudioPerfectPitchSolutionGenerator extends RandomPerfectPitchSolutionGenerator<AudioPerfectPitchSolution, AudioPerfectPitchConfigDTO> implements AudioPerfectPitchSolutionGenerator {

    public RandomAudioPerfectPitchSolutionGenerator(final AudioPerfectPitchConfigDTO puzzleConfigDto) {
        super(puzzleConfigDto);
    }

    protected AudioPerfectPitchSolution buildPossibleSolution(final PianoKeyNumber keyNumber) {
        return new AudioPerfectPitchSolution(keyNumber);
    }

    protected IntFunction<AudioPerfectPitchSolution[]> getArrayConstructor() {
        return AudioPerfectPitchSolution[]::new;
    }

}
