package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.solutions.sound.SingleSoundSolution;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchSolutionGenerator;

public final class FakeAudioPerfectPitchSolutionGenerator implements AudioPerfectPitchSolutionGenerator {
    public static SingleSoundSolution fakeSolution;

    public SingleSoundSolution generate() {
        return fakeSolution;
    }
}
