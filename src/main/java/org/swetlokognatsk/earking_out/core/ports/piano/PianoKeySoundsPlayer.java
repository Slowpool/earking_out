package org.swetlokognatsk.earking_out.core.ports.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public interface PianoKeySoundsPlayer {
    void play(final PianoKeyNumber keyNumber);
    void stop(final PianoKeyNumber keyNumber);
    void stopAndPlay(final PianoKeyNumber keyNumber);
}
