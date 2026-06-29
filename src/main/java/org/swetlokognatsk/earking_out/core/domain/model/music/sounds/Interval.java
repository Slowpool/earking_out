package org.swetlokognatsk.earking_out.core.domain.model.music.sounds;

public abstract class Interval extends Sound {
    public final SingleSound firstSound;
    public final SingleSound secondSound;

    public Interval(final SingleSound firstSound, final SingleSound secondSound) {
        this.firstSound = firstSound;
        this.secondSound = secondSound;
    }
}
