package org.swetlokognatsk.earking_out.core.domain.model.piano.key;

import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;

public final class PianoKeysFactory {
    protected final PianoKeySoundsPlayer pianoKeyPlayers;
    protected final PianoKeyColorService keyColorService;

    public PianoKeysFactory() {
        pianoKeyPlayers = DI.get(PianoKeySoundsPlayer.class);
        keyColorService = DI.get(PianoKeyColorService.class);
    }

    public PianoKey create(final PianoKeyNumber keyNumber, final PianoKeyMode mode, final boolean isSelected) {
        var pianoKey = new PianoKey(keyNumber, mode, isSelected, keyColorService, pianoKeyPlayers);
        return pianoKey;
    }

    public PianoKey create(final PianoKeyNumber keyNumber, final PianoKeyMode mode) {
        return create(keyNumber, mode, false);
    }

}
