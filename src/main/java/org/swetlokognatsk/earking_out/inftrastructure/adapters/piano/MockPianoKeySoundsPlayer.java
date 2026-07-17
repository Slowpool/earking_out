package org.swetlokognatsk.earking_out.inftrastructure.adapters.piano;

import java.io.Serializable;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;

public class MockPianoKeySoundsPlayer implements PianoKeySoundsPlayer, Serializable {
    public boolean playIsPressed = false;
    public boolean stopIsPressed = false;
    public boolean stopAndPlayIsPressed = false;

    public void play(final PianoKeyNumber keyNumber) {
        playIsPressed = true;
    }

    public void stop(final PianoKeyNumber keyNumber) {
        stopIsPressed = true;
    }

    public void stopAndPlay(final PianoKeyNumber keyNumber) {
        stopAndPlayIsPressed = true;
    }
}
