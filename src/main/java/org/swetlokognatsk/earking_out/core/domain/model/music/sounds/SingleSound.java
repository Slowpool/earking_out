package org.swetlokognatsk.earking_out.core.domain.model.music.sounds;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public final class SingleSound extends Sound {
    public final PianoKeyNumber keyNumber;

    public SingleSound(final PianoKeyNumber keyNumber) {
        // TODO validation
        this.keyNumber = keyNumber;
    }
}
