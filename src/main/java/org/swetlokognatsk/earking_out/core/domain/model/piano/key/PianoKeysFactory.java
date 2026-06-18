package org.swetlokognatsk.earking_out.core.domain.model.piano.key;

import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.ports.DI;

public final class PianoKeysFactory {

    private PianoKeysFactory() {
    }

    public static PianoKey create(final byte keyNumber, final PianoKeyMode mode, final boolean isSelected) {
        var keyColorService = DI.get(PianoKeyColorService.class);
        var pianoKey = new PianoKey(keyNumber, mode, isSelected, keyColorService);
        return pianoKey;
    }

    public static PianoKey create(final byte keyNumber, final PianoKeyMode mode) {
        return create(keyNumber, mode, false);
    }

}
