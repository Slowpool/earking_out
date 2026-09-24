package org.swetlokognatsk.earking_out.web_infrastructure.adapters.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;

public class FakeKeySoundsPlayer implements PianoKeySoundsPlayer {

    public void play(final PianoKeyNumber keyNumber) {
    }

    public void stop(final PianoKeyNumber keyNumber) {
    }

    public void stopAndPlay(final PianoKeyNumber keyNumber) {
    }

}
