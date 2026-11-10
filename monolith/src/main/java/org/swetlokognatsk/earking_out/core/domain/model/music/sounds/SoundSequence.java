package org.swetlokognatsk.earking_out.core.domain.model.music.sounds;

// TODO is it needed at all?
public class SoundSequence extends Sound {
    public final Sound[] sounds;

    public SoundSequence(final Sound[] sounds) {
        // TODO validation
        this.sounds = sounds;
    }
}
