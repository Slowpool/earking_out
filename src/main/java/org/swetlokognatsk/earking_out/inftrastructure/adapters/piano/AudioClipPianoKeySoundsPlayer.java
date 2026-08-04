package org.swetlokognatsk.earking_out.inftrastructure.adapters.piano;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.sounds.AudioClipSoundPlayer;

public final class AudioClipPianoKeySoundsPlayer implements PianoKeySoundsPlayer, Serializable {
    private transient Map<PianoKeyNumber, AudioClipSoundPlayer> soundPlayers;

    public AudioClipPianoKeySoundsPlayer() {
        buildSoundPlayers();
    }

    private void buildSoundPlayers() {
        var files = getFiles();
        final var soundPlayers = new HashMap<PianoKeyNumber, AudioClipSoundPlayer>();
        var filesStream = files.entrySet().stream();
        filesStream.forEach((Map.Entry<PianoKeyNumber, File> fileEntry) -> {
            var pianoKeyNumber = fileEntry.getKey();
            var file = fileEntry.getValue();
            var soundPlayer = new AudioClipSoundPlayer(file);
            soundPlayers.put(pianoKeyNumber, soundPlayer);
        });
        this.soundPlayers = soundPlayers;
    }

    private Map<PianoKeyNumber, File> getFiles() {
        var pianoKeySoundFilesBuilder = DI.get(PianoKeySoundFilesBuilder.class);
        var files = pianoKeySoundFilesBuilder.getFiles();
        return files;
    }

    private AudioClipSoundPlayer getSoundPlayer(final PianoKeyNumber keyNumber) {
        var soundPlayer = soundPlayers.get(keyNumber);
        if (soundPlayer == null) {
            throw new IllegalArgumentException();
        }
        return soundPlayer;
    }

    public void play(final PianoKeyNumber keyNumber) {
        var soundPlayer = getSoundPlayer(keyNumber);
        soundPlayer.play();
    }

    public void stop(final PianoKeyNumber keyNumber) {
        var soundPlayer = getSoundPlayer(keyNumber);
        soundPlayer.stop();
    }

    public void stopAndPlay(final PianoKeyNumber keyNumber) {
        var soundPlayer = getSoundPlayer(keyNumber);
        soundPlayer.stop();
        soundPlayer.play();
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        buildSoundPlayers();
    }

}
