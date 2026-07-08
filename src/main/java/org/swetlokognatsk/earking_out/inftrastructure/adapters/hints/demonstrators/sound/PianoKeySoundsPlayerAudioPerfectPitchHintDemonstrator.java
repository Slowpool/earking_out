package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.demonstrators.sound;

import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.sound.AudioPerfectPitchHintDemonstrator;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;

public final class PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator implements AudioPerfectPitchHintDemonstrator {
    protected final PianoKeySoundsPlayer pianoKeySoundsPlayer;

    public PianoKeySoundsPlayerAudioPerfectPitchHintDemonstrator(final PianoKeySoundsPlayer pianoKeySoundsPlayer) {
        this.pianoKeySoundsPlayer = pianoKeySoundsPlayer;
    }

    public void demonstrateHint(final AudioPerfectPitchSolution solution) {
        var keyNumber = solution.keyNumber;
        pianoKeySoundsPlayer.play(keyNumber);
    }
}
