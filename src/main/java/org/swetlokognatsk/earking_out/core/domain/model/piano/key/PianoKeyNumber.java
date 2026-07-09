package org.swetlokognatsk.earking_out.core.domain.model.piano.key;

import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * `Note number` is synonym for `key number`. Both of them mean both the key on
 * keyboard and according note.
 */
public final class PianoKeyNumber extends ValueObject implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Map<Byte, PianoKeyNumber> innerStorage = new HashMap<>();

    public final byte value;
    public final byte octaveScopedKeyNumber;

    // singleton-like optimization
    static {
        for (byte i = BYTE_FIRST_NOTE_NUMBER; i <= BYTE_LAST_NOTE_NUMBER; i++) {
            innerStorage.put(i, new PianoKeyNumber(i));
        }
    }

    protected byte calculateOctaveScopedKeyNumber() {
        return (byte) ((value - SHIFT - 1) % KEYS_IN_OCTAVE + 1);
    }

    public byte getOctaveScopedKeyNumber() {
        return octaveScopedKeyNumber;
    }

    private PianoKeyNumber(final int keyNumber) {
        validate(keyNumber);
        value = (byte) keyNumber;
        octaveScopedKeyNumber = calculateOctaveScopedKeyNumber();
    }

    public static PianoKeyNumber valueOf(final int keyNumber) {
        validate(keyNumber);

        var ByteKeyNumber = Byte.valueOf((byte) keyNumber);
        var pianoKeyNumber = innerStorage.get(ByteKeyNumber);
        return pianoKeyNumber;
    }

    public static PianoKeyNumber valueOf(final byte keyNumber) {
        return valueOf((int) keyNumber);
    }

    public static void validate(final int keyNumber) {
        if (keyNumber < BYTE_FIRST_NOTE_NUMBER) {
            throw new IllegalArgumentException("keyNumber is too small: " + keyNumber);
        }
        if (keyNumber > BYTE_LAST_NOTE_NUMBER) {
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

    public PianoKeyNumber add(final byte number) {
        return add((int) number);
    }

    public PianoKeyNumber add(final int number) {
        var keyNumber = Math.addExact(value, number);
        try {
            var newKeyNumber = PianoKeyNumber.valueOf(keyNumber);
            return newKeyNumber;
        } catch (IllegalArgumentException e) {
            throw new ArithmeticException("piano key number overflow: " + number);
        }
    }

    public PianoKeyNumber increment() {
        return add(1);
    }

    public PianoKeyNumber subtract(final byte number) {
        return subtract((int) number);
    }

    public PianoKeyNumber subtract(final int number) {
        var keyNumber = Math.subtractExact(value, number);
        try {
            var newKeyNumber = PianoKeyNumber.valueOf(keyNumber);
            return newKeyNumber;
        } catch (IllegalArgumentException e) {
            throw new ArithmeticException("piano key number overflow");
        }
    }

    public PianoKeyNumber decrement() {
        return subtract(1);
    }

}
