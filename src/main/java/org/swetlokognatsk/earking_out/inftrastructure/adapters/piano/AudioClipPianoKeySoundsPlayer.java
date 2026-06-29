package org.swetlokognatsk.earking_out.inftrastructure.adapters.piano;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.sounds.AudioClipSoundPlayer;

// TODO test
public final class AudioClipPianoKeySoundsPlayer implements PianoKeySoundsPlayer {
    protected Map<PianoKeyNumber, AudioClipSoundPlayer> soundPlayers;

    public AudioClipPianoKeySoundsPlayer() {
        var pianoKeySoundFilesBuilder = DI.get(PianoKeySoundFilesBuilder.class);
        soundPlayers = buildSoundPlayers(pianoKeySoundFilesBuilder.getFiles());
    }

    protected Map<PianoKeyNumber, AudioClipSoundPlayer> buildSoundPlayers(final Map<PianoKeyNumber, File> files) {
        // TODO refactoring via stream
        Map<PianoKeyNumber, AudioClipSoundPlayer> soundPlayers = new HashMap<>();
        AudioClipSoundPlayer soundPlayer;
        for (var pianoKeyNumber : files.keySet()) {
            soundPlayer = new AudioClipSoundPlayer(files.get(pianoKeyNumber));
            soundPlayers.put(pianoKeyNumber, soundPlayer);
        }
        return soundPlayers;
    }

    protected AudioClipSoundPlayer getSoundPlayer(final PianoKeyNumber keyNumber) {
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

}
