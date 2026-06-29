package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.demonstrators.sound;

import org.swetlokognatsk.earking_out.core.domain.model.solutions.sound.SingleSoundSolution;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.sound.SingleSoundHintDemonstrator;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;

public final class AudioClipSingleSoundHintDemonstrator implements SingleSoundHintDemonstrator {
    protected final PianoKeySoundsPlayer pianoKeySoundsPlayer;

    public AudioClipSingleSoundHintDemonstrator(final PianoKeySoundsPlayer pianoKeySoundsPlayer) {
        this.pianoKeySoundsPlayer = pianoKeySoundsPlayer;
    }

    public void demonstrateHint(final SingleSoundSolution solution) {
        var keyNumber = solution.sound.keyNumber;
        pianoKeySoundsPlayer.play(keyNumber);
    }
}
