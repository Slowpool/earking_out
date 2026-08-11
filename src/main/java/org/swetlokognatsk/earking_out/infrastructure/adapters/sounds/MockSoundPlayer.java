package org.swetlokognatsk.earking_out.infrastructure.adapters.sounds;

import java.io.Serializable;

public final class MockSoundPlayer implements SoundPlayer, Serializable {
    public boolean playIsPressed = false;
    public boolean stopIsPressed = false;
    public boolean stopAndPlayIsPressed = false;

    public void play() {
        playIsPressed = true;
    }

    public void stop() {
        stopIsPressed = true;
    }

    public void stopAndPlay() {
        stopAndPlayIsPressed = true;
    }
}