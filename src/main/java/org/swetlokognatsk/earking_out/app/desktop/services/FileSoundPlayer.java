package org.swetlokognatsk.earking_out.app.desktop.services;

import java.io.File;
import javafx.scene.media.AudioClip;

public final class FileSoundPlayer implements SoundPlayerService {
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
