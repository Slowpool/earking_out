package org.swetlokognatsk.earking_out.inftrastructure.adapters.sounds;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import org.swetlokognatsk.earking_out.core.ports.sounds.SoundPlayer;
import javafx.scene.media.AudioClip;

public final class FileSoundPlayer implements SoundPlayer {
    protected final String audioClipSource;
    protected transient AudioClip audioClip;

    public FileSoundPlayer(final File file) {
        var absoluteFilePath = file.getAbsolutePath();
        if (!file.exists()) {
            throw new RuntimeException("File does not exist: " + absoluteFilePath);
        }
        audioClipSource = buildAudioClipSource(absoluteFilePath);
        createAudioClip();
    }

    protected String buildAudioClipSource(final String absoluteFilePath) {
        return "file://" + absoluteFilePath;
    }

    protected void createAudioClip() {
        audioClip = new AudioClip(audioClipSource);
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

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        createAudioClip();
    }
}
