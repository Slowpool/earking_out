package org.swetlokognatsk.earking_out.infrastructure.web.adapters.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;

// TODO yet it's just a latch
public class FakeKeySoundsPlayer implements PianoKeySoundsPlayer {

    public void play(final PianoKeyNumber keyNumber) {
    }

    public void stop(final PianoKeyNumber keyNumber) {
    }

    public void stopAndPlay(final PianoKeyNumber keyNumber) {
    }

}
