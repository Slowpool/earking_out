package org.swetlokognatsk.earking_out.core.domain.model.piano.key;

import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardSoundMode;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundsPlayer;

public final class PianoKeysFactory {
    protected final PianoKeySoundsPlayer pianoKeySoundsPlayer;
    protected final PianoKeyColorService keyColorService;

    public PianoKeysFactory() {
        pianoKeySoundsPlayer = DI.get(PianoKeySoundsPlayer.class);
        keyColorService = DI.get(PianoKeyColorService.class);
    }

    // TODO passing PianoKeyboardSoundMode to PianoKey is wrong
    public PianoKey create(final PianoKeyNumber keyNumber, final PianoKeyMode mode, final boolean isSelected, final PianoKeyboardSoundMode soundMode) {
        var pianoKey = new PianoKey(keyNumber, mode, isSelected, soundMode, keyColorService, pianoKeySoundsPlayer);
        return pianoKey;
    }

    public PianoKey create(final PianoKeyNumber keyNumber, final PianoKeyMode mode, final PianoKeyboardSoundMode soundMode) {
        return create(keyNumber, mode, false, soundMode);
    }

    public PianoKey create(final PianoKeyNumber keyNumber, final PianoKeyMode mode) {
        return create(keyNumber, mode, false, PianoKeyboardSoundMode.USUAL);
    }

}
