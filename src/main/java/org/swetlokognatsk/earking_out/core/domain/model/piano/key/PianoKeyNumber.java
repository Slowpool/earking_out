package org.swetlokognatsk.earking_out.core.domain.model.piano.key;

import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Invariants.*;

public final class PianoKeyNumber extends ValueObject {
    public final byte value;

    private PianoKeyNumber(final int keyNumber) {
        validate(keyNumber);
        this.value = (byte) keyNumber;
    }

    // TODO try to use the same optimization as in any Object-version of primitive type (Integer, Byte)
    public static PianoKeyNumber valueOf(final byte keyNumber) {
        return new PianoKeyNumber((int) keyNumber);
    }

    public static PianoKeyNumber valueOf(final int keyNumber) {
        return new PianoKeyNumber(keyNumber);
    }

    public static void validate(final int keyNumber) {
        if (keyNumber < FIRST_NOTE_NUMBER) {
            throw new IllegalArgumentException("keyNumber is too small: " + keyNumber);
        }
        var lastKeyNumber = FIRST_NOTE_NUMBER + PIANO_KEYS_NUMBER - 1;
        if (keyNumber > lastKeyNumber) {
            throw new IllegalArgumentException("keyNumber is too big: " + keyNumber);
        }
    }

    public int hashCode() {
        return value;
    }

    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PianoKeyNumber)) {
            return false;
        }
        var other = (PianoKeyNumber) obj;
        return other.value == value;
    }
}
