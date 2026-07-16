package org.swetlokognatsk.earking_out.core.domain.model.piano.key;

import org.swetlokognatsk.earking_out.core.domain.model.base.ValueObject;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// `record` is not suitable because of extra `octaveScopedKeyNumber` field
/**
 * `Note number` is synonym for `key number`. Both of them mean both the key on
 * keyboard and according note.
 */
public final class PianoKeyNumber extends ValueObject implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Map<Byte, PianoKeyNumber> innerStorage;

    public static final PianoKeyNumber FIRST_NOTE_NUMBER;
    public static final PianoKeyNumber LAST_NOTE_NUMBER;

    public final byte value;
    public final byte octaveScopedKeyNumber;

    // singleton-like optimization
    static {
        var tempInnerStorage = new HashMap<Byte, PianoKeyNumber>();
        for (byte i = BYTE_FIRST_NOTE_NUMBER; i <= BYTE_LAST_NOTE_NUMBER; i++) {
            tempInnerStorage.put(i, new PianoKeyNumber(i));
        }
        // to avoid static initializers wrong order. the wrong order error will be more obvious, kinda `innerStorage uninitialized variable using`
        innerStorage = tempInnerStorage;

        FIRST_NOTE_NUMBER = PianoKeyNumber.valueOf(BYTE_FIRST_NOTE_NUMBER);
        LAST_NOTE_NUMBER = PianoKeyNumber.valueOf(BYTE_LAST_NOTE_NUMBER);
    }

    protected byte calculateOctaveScopedKeyNumber() {
        return (byte) ((value - SHIFT - 1) % KEYS_IN_OCTAVE + 1);
    }

    public byte getOctaveScopedKeyNumber() {
        return octaveScopedKeyNumber;
    }

    // TODO add ref to valueOf()
    /** Use valueOf() if you wanna some PianoKeyNumber. It optimizes the memory */
    private PianoKeyNumber(final byte value) {
        validate(value);
        this.value = (byte) value;
        octaveScopedKeyNumber = calculateOctaveScopedKeyNumber();
    }

    public static PianoKeyNumber valueOf(final int value) {
        validate(value);

        var ByteValue = Byte.valueOf((byte) value);
        var pianoKeyNumber = innerStorage.get(ByteValue);
        if (pianoKeyNumber == null) {
            throw new IllegalArgumentException("pianoKeyNumber not found: " + value);
        }
        return pianoKeyNumber;
    }

    public static PianoKeyNumber valueOf(final byte value) {
        return valueOf((int) value);
    }

    public static void validate(final int value) {
        if (value < BYTE_FIRST_NOTE_NUMBER) {
            throw new IllegalArgumentException("keyNumber is too small: " + value);
        }
        if (value > BYTE_LAST_NOTE_NUMBER) {
            throw new IllegalArgumentException("keyNumber is too big: " + value);
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
        var newValue = Math.addExact(value, number);
        try {
            var newKeyNumber = PianoKeyNumber.valueOf(newValue);
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
        var newValue = Math.subtractExact(value, number);
        try {
            var newKeyNumber = PianoKeyNumber.valueOf(newValue);
            return newKeyNumber;
        } catch (IllegalArgumentException e) {
            throw new ArithmeticException("piano key number overflow");
        }
    }

    public PianoKeyNumber decrement() {
        return subtract(1);
    }

}
