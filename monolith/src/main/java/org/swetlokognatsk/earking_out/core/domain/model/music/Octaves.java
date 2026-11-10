package org.swetlokognatsk.earking_out.core.domain.model.music;

/**
 * Domain-restricted list of octaves. Physically the number of them is not
 * limited. `FIRST` is also called C1 in science
 * 
 * @see https://en.wikipedia.org/wiki/Octave
 */
public enum Octaves {
    FIRST(1), SECOND(2), THIRD(3), FOURTH(4), FIFTH(5), SIXTH(6), SEVENTH(7);

    public final int number;

    private Octaves(int number) {
        this.number = number;
    }
}
