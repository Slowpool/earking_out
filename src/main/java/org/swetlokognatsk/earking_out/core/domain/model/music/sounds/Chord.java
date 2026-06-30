package org.swetlokognatsk.earking_out.core.domain.model.music.sounds;

import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;

public final class Chord extends ValueObject {
    public final Note[] notes;

    public Chord(final Note[] notes) {
        // TODO validate that sounds contains 2+ elements
        this.notes = notes;
    }
}
