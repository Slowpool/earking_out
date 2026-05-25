package org.swetlokognatsk.earking_out.app.desktop.services;

import java.io.File;
import javafx.scene.media.AudioClip;

public class FileSoundPlayer implements SoundPlayer {
    protected AudioClip audioClip;

    public FileSoundPlayer(File file) {
        if (!file.exists()) {
            // TODO uncomment in prod
            // TODO maybe another exception?
            // throw new IllegalArgumentException("FileSoundPlayer requires existing file");
            return;
        }
        audioClip = new AudioClip("file://" + file.getAbsolutePath());
    }

    public void play() {
        audioClip.play();
    }

    public void stop() {
        audioClip.stop();
    }
}
