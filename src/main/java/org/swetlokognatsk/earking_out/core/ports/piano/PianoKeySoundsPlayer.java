package org.swetlokognatsk.earking_out.core.ports.piano;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

// TODO implement via some lib that takes InputStream or kinda. as a replacement for AudioClipSoundPlayer. because the latter is not suitable for far .JAR file
public interface PianoKeySoundsPlayer {
    void play(final PianoKeyNumber keyNumber);
    void stop(final PianoKeyNumber keyNumber);
    void stopAndPlay(final PianoKeyNumber keyNumber);
}
