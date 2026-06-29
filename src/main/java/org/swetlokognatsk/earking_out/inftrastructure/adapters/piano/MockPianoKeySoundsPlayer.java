package org.swetlokognatsk.earking_out.inftrastructure.adapters.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;

public class MockPianoKeySoundsPlayer implements PianoKeySoundsPlayer {
    public void play(final PianoKeyNumber keyNumber) {
    }

    public void stop(final PianoKeyNumber keyNumber) {
    }

    public void stopAndPlay(final PianoKeyNumber keyNumber) {
    }
}
