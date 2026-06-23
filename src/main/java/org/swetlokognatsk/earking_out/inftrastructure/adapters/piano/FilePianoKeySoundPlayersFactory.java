package org.swetlokognatsk.earking_out.inftrastructure.adapters.piano;

import java.io.File;
import java.util.Map;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundPlayersFactory;
import org.swetlokognatsk.earking_out.core.ports.sounds.SoundPlayer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.sounds.FileSoundPlayer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.sounds.PianoKeySoundsFromHints;

public final class FilePianoKeySoundPlayersFactory implements PianoKeySoundPlayersFactory {
    // TODO it feels like overenginering, but pianoKeySounds and constructor can be encapsulated inside `PianoKeySoundPlayersFactory` class
    protected final Map<PianoKeyNumber, String> pianoKeySounds;

    public FilePianoKeySoundPlayersFactory() {
        var pianoKeySoundsService = DI.get(PianoKeySoundsFromHints.class);
        pianoKeySounds = pianoKeySoundsService.getMap();
    }

    public SoundPlayer create(final PianoKeyNumber keyNumber) {
        var sound = pianoKeySounds.get(keyNumber);
        var soundFile = new File(sound);
        var fileSoundPlayer = new FileSoundPlayer(soundFile);
        return fileSoundPlayer;
    }
}
