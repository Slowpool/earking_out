package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch;

import java.util.function.IntFunction;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import static org.swetlokognatsk.earking_out.SpringProfiles.*;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchSolutionGenerator;

@Component
@Profile({ DESKTOP, WEB })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
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
