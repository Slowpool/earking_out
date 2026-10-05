package org.swetlokognatsk.earking_out.core.domain.model.music;

public enum Accidentals {
    SHARP((byte) 1, "#"), FLAT((byte) -1, "b"), NATURAL((byte) 0, "");

    public final byte shift;
    public final String character;

    private Accidentals(final byte shift, final String character) {
        this.shift = shift;
        this.character = character;
    }

    public static byte getShift(final Accidentals accidental) {
        var shift = accidental == null ? 0 : accidental.shift;
        return shift;
    }

}
