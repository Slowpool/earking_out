package org.swetlokognatsk.earking_out.inftrastructure.adapters.sounds;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javafx.scene.media.AudioClip;

public final class AudioClipSoundPlayer implements SoundPlayer, Serializable {
    private String audioClipSource;
    private transient AudioClip audioClip;

    public AudioClipSoundPlayer(final File file) {
        var absoluteFilePath = file.getAbsolutePath();
        if (!file.exists()) {
            throw new RuntimeException("File does not exist: " + absoluteFilePath);
        }
        audioClipSource = buildAudioClipSource(absoluteFilePath);
        createAudioClip();
    }

    private String buildAudioClipSource(final String absoluteFilePath) {
        return "file://" + absoluteFilePath;
    }

    private void createAudioClip() {
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
