package org.swetlokognatsk.earking_out.inftrastructure.adapters.sounds;

import org.swetlokognatsk.earking_out.core.ports.sounds.SoundPlayer;

public final class MockSoundPlayer implements SoundPlayer {
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