package org.swetlokognatsk.earking_out.infrastructure.adapters.piano;

import static org.junit.Assert.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.infrastructure.adapters.base.SerializationCloner;
import org.swetlokognatsk.earking_out.infrastructure.adapters.piano.AudioClipPianoKeySoundsPlayer;

/**
 * Lightweight infrastructure test - just checks that files are in place and
 * there's no any exception during play, that's it.
 */
public final class AudioClipPianoKeySoundsPlayerTest {

    private AudioClipPianoKeySoundsPlayer audioClipPianoKeySoundsPlayer;

    @Before
    public void setup() {
        audioClipPianoKeySoundsPlayer = DI.get(AudioClipPianoKeySoundsPlayer.class);
    }

    private static void playEachKey(final AudioClipPianoKeySoundsPlayer audioClipPianoKeySoundsPlayer) {
        // if there's no exception, everything is fine enough
        PianoKeyNumber.forEachKey((PianoKeyNumber pianoKeyNumber) -> {
            audioClipPianoKeySoundsPlayer.play(pianoKeyNumber);
        });
    }

    @Test
    public void playWorks() {
        playEachKey(audioClipPianoKeySoundsPlayer);
    }

    @Test
    public void serializationTest() {
        var cloner = DI.get(SerializationCloner.class);
        var soundsPlayerCopy = cloner.clone(audioClipPianoKeySoundsPlayer);
        playEachKey(soundsPlayerCopy);
    }
}
