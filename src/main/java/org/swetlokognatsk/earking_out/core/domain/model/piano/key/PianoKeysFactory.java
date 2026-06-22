package org.swetlokognatsk.earking_out.core.domain.model.piano.key;

import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundPlayersFactory;

public final class PianoKeysFactory {
    protected final PianoKeySoundPlayersFactory keySoundPlayersFactory;
    protected final PianoKeyColorService keyColorService;

    public PianoKeysFactory() {
        keySoundPlayersFactory = DI.get(PianoKeySoundPlayersFactory.class);
        keyColorService = DI.get(PianoKeyColorService.class);
    }

    public PianoKey create(final PianoKeyNumber keyNumber, final PianoKeyMode mode, final boolean isSelected) {
        var soundPlayer = keySoundPlayersFactory.create(keyNumber);

        var pianoKey = new PianoKey(keyNumber, mode, isSelected, keyColorService, soundPlayer);
        return pianoKey;
    }

    public PianoKey create(final PianoKeyNumber keyNumber, final PianoKeyMode mode) {
        return create(keyNumber, mode, false);
    }

}
