package org.swetlokognatsk.earking_out.core.ports.sounds;

import java.io.Serializable;

public interface SoundPlayer extends Serializable {
    void play();
    void stopAndPlay();
    void stop();
}
