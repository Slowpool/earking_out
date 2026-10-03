package org.swetlokognatsk.earking_out.infrastructure.adapters.hints.demonstrators.sound;

import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.sound.AudioPerfectPitchHintDemonstrator;

// TODO yet in web piano key sounds player logic is partially implemented on frontend, so this class is hack. it should be eliminated, and PianoKeySoundsPlayer should take this role instead
@Component
public class WebAudioPerfectPitchHintDemonstrator implements AudioPerfectPitchHintDemonstrator {

    // yep, temporal coupling
    public PianoKeyNumber pianoKeyNumberToPlay = null;

    public void demonstrateHint(final AudioPerfectPitchSolution solution) {
        pianoKeyNumberToPlay = solution.keyNumber;
    }

    public String getHintUrl() {
        return "/sounds/piano_keys/key%d.wav".formatted(pianoKeyNumberToPlay.value);
    }
}
