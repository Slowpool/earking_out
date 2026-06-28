package org.swetlokognatsk.earking_out.core.domain.model.sounds;

public final class Chord extends Sound {
    public final SingleSound[] sounds;

    public Chord(final SingleSound[] sounds) {
        // TODO validate that sounds contains 2+ elements
        this.sounds = sounds;
    }
}
