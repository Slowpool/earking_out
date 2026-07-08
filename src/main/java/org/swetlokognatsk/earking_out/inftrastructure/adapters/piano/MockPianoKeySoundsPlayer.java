package org.swetlokognatsk.earking_out.inftrastructure.adapters.piano;

import java.io.Serializable;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;

public class MockPianoKeySoundsPlayer implements PianoKeySoundsPlayer, Serializable {
    public boolean stopAndPlayIsPressed = false;

    public void play(final PianoKeyNumber keyNumber) {
    }

    public void stop(final PianoKeyNumber keyNumber) {
    }

    public void stopAndPlay(final PianoKeyNumber keyNumber) {
        stopAndPlayIsPressed = true;
    }
}
