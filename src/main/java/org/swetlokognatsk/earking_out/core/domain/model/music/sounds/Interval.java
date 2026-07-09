package org.swetlokognatsk.earking_out.core.domain.model.music.sounds;

import java.io.Serializable;

import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;

public abstract class Interval extends ValueObject implements Serializable {
    public final Note firstNote;
    public final Note secondNote;

    public Interval(final Note firstNote, final Note secondNote) {
        this.firstNote = firstNote;
        this.secondNote = secondNote;
    }
}
