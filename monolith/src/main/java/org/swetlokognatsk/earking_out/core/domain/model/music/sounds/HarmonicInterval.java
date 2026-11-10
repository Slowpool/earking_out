package org.swetlokognatsk.earking_out.core.domain.model.music.sounds;

public class HarmonicInterval extends Interval {
    private static final long serialVersionUID = 1L;

    public HarmonicInterval(final Note firstNote, final Note secondNote) {
        super(firstNote, secondNote);
        // TODO validate
    }
}
