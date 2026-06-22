package org.swetlokognatsk.earking_out.inftrastructure.adapters.sounds;

import java.io.File;
import org.swetlokognatsk.earking_out.core.ports.sounds.SoundPlayer;
import javafx.scene.media.AudioClip;

public final class FileSoundPlayer implements SoundPlayer {
    protected AudioClip audioClip;

    public FileSoundPlayer(File file) {
        if (!file.exists()) {
            throw new RuntimeException("File does not exist: " + file.getAbsolutePath());
        }
        audioClip = new AudioClip("file://" + file.getAbsolutePath());
    }

    public void play() {
        audioClip.play();
    }

    public void stopAndPlay() {
        stop();
        play();
    }

    public void stop() {
        audioClip.stop();
    }
}
