package org.swetlokognatsk.earking_out.core.domain.model.music.sounds;

public final class MelodicInterval extends Interval {
    private static final long serialVersionUID = 1L;

    public MelodicInterval(final Note firstNote, final Note secondNote) {
        super(firstNote, secondNote);
        // TODO validate
    }
}
