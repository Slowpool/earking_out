package org.swetlokognatsk.earking_out.core.domain.model.piano.key;

import static org.junit.Assert.*;
import org.junit.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;

// TODO generalize this test for all value object tests
public final class PianoKeyNumberTest {

    @Test
    public void correctValue1() {
        // if there's no exception, then `valueOf(4)` works fine
        var test = FIRST_NOTE_NUMBER;
    }

    @Test
    public void correctValue2() {
        var test = LAST_NOTE_NUMBER;
    }

    @Test
    public void octaveScopedKeyNumber1() {
        var pianoKeyNumber = FIRST_NOTE_NUMBER;
        assertEquals((byte) 1, pianoKeyNumber.getOctaveScopedKeyNumber());
    }

    @Test
    public void octaveScopedKeyNumber2() {
        var pianoKeyNumber = FIRST_NOTE_NUMBER.add(KEYS_IN_OCTAVE);
        assertEquals((byte) 1, pianoKeyNumber.getOctaveScopedKeyNumber());
    }

    @Test
    public void addWithoutOverflow() {
        var number = 5;
        var keyNumber = FIRST_NOTE_NUMBER.add(number);
        assertEquals((byte) (FIRST_NOTE_NUMBER.value + number), keyNumber.value);
    }

    @Test
    public void addWithOverflow() {
        try {
            FIRST_NOTE_NUMBER.add(PIANO_KEYS_NUMBER);
            fail();
        } catch (ArithmeticException e) {
        }
    }

    @Test
    public void subtractWithoutOverflow() {
        var number = 5;
        var keyNumber = LAST_NOTE_NUMBER.subtract(number);
        assertEquals((byte) (LAST_NOTE_NUMBER.value - number), keyNumber.value);
    }

    @Test
    public void subtractWithOverflow() {
        try {
            FIRST_NOTE_NUMBER.subtract(PIANO_KEYS_NUMBER);
            fail();
        } catch (ArithmeticException e) {
        }
    }

    @Test
    public void incrementWithoutOverflow() {
        var keyNumber = FIRST_NOTE_NUMBER.increment();
        assertEquals((byte) (FIRST_NOTE_NUMBER.value + 1), keyNumber.value);
    }

    @Test
    public void incrementWithOverflow() {
        try {
            LAST_NOTE_NUMBER.increment();
            fail();
        } catch (ArithmeticException e) {
        }
    }

    @Test
    public void decrementWithoutOverflow() {
        var keyNumber = LAST_NOTE_NUMBER.decrement();
        assertEquals((byte) (LAST_NOTE_NUMBER.value - 1), keyNumber.value);
    }

    @Test
    public void decrementWithOverflow() {
        try {
            FIRST_NOTE_NUMBER.decrement();
            fail();
        } catch (ArithmeticException e) {
        }
    }

    @Test
    public void equalsTheSameValue() {
        var pianoKeyNumber1 = FIRST_NOTE_NUMBER;
        var pianoKeyNumber2 = FIRST_NOTE_NUMBER;
        assertTrue(pianoKeyNumber1.equals(pianoKeyNumber2));
    }

    @Test
    public void equalsDifferentValue() {
        var pianoKeyNumber1 = FIRST_NOTE_NUMBER;
        var pianoKeyNumber2 = FIRST_NOTE_NUMBER.increment();
        assertFalse(pianoKeyNumber1.equals(pianoKeyNumber2));
    }

    @Test
    public void equalsNull() {
        var pianoKeyNumber = FIRST_NOTE_NUMBER;
        assertFalse(pianoKeyNumber.equals(null));
    }

    @Test
    public void equalsAnotherClass() {
        var pianoKeyNumber = FIRST_NOTE_NUMBER;
        assertFalse(pianoKeyNumber.equals(new Dummy()));
    }

    protected record Dummy() {
    };

}
