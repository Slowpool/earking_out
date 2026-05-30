package org.swetlokognatsk.earking_out.core.domain.model.music;

public enum Accidentals {
    SHARP((byte)1), FLAT((byte)-1), NATURAL((byte)0);

    public final byte shift;

    private Accidentals(byte shift) {
        this.shift = shift;
    }

    public static byte getShift(Accidentals accidental) {
        var shift = accidental == null ? 0 : accidental.shift;
        return shift;
    }
}
