package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.FIRST_NOTE_NUMBER;
import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchSolutionGenerator;

public final class FakeAudioPerfectPitchSolutionGenerator implements AudioPerfectPitchSolutionGenerator {
    public static AudioPerfectPitchSolution fakeSolution;

    public AudioPerfectPitchSolution generate() {
        return Objects.requireNonNullElse(fakeSolution, new AudioPerfectPitchSolution(FIRST_NOTE_NUMBER));
    }
}
