package org.swetlokognatsk.earking_out.core.domain.model.piano.key;

import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;

@Service
public final class PianoKeysFactory {
    private final PianoKeyColorService pianoKeyColorService;

    // TODO update constructors
    public PianoKeysFactory(final PianoKeyColorService pianoKeyColorService) {
        this.pianoKeyColorService = pianoKeyColorService;
    }

    public PianoKey create(final PianoKeyNumber keyNumber, final PianoKeyMode mode, final boolean isSelected) {
        var color = pianoKeyColorService.getColor(keyNumber);
        var pianoKey = new PianoKey(keyNumber, mode, isSelected, color);
        return pianoKey;
    }

    public PianoKey create(final PianoKeyNumber keyNumber, final PianoKeyMode mode) {
        return create(keyNumber, mode, false);
    }
}
