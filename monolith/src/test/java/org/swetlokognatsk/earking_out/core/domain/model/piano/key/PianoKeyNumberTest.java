package org.swetlokognatsk.earking_out.core.domain.model.piano.key;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.swetlokognatsk.earking_out.EOSpringBootTest;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;

// TODO generalize this test for all value object tests
public final class PianoKeyNumberTest {

    @Test
    void correctValue1() {
        // if there's no exception, then `valueOf(4)` works fine
        var test = FIRST_NOTE_NUMBER;
    }

    @Test
    void correctValue2() {
        var test = LAST_NOTE_NUMBER;
    }

    @Test
    void octaveScopedKeyNumber1() {
        var pianoKeyNumber = FIRST_NOTE_NUMBER;
        assertEquals((byte) 1, pianoKeyNumber.getOctaveScopedKeyNumber());
    }

    @Test
    void octaveScopedKeyNumber2() {
        var pianoKeyNumber = FIRST_NOTE_NUMBER.add(KEYS_IN_OCTAVE);
        assertEquals((byte) 1, pianoKeyNumber.getOctaveScopedKeyNumber());
    }

    @Test
    void addWithoutOverflow() {
        var number = 5;
        var keyNumber = FIRST_NOTE_NUMBER.add(number);
        assertEquals((byte) (FIRST_NOTE_NUMBER.value + number), keyNumber.value);
    }

    @Test
    void addWithOverflow() {
        try {
            FIRST_NOTE_NUMBER.add(PIANO_KEYS_NUMBER);
            fail();
        } catch (ArithmeticException e) {
        }
    }

    @Test
    void subtractWithoutOverflow() {
        var number = 5;
        var keyNumber = LAST_NOTE_NUMBER.subtract(number);
        assertEquals((byte) (LAST_NOTE_NUMBER.value - number), keyNumber.value);
    }

    @Test
    void subtractWithOverflow() {
        try {
            FIRST_NOTE_NUMBER.subtract(PIANO_KEYS_NUMBER);
            fail();
        } catch (ArithmeticException e) {
        }
    }

    @Test
    void incrementWithoutOverflow() {
        var keyNumber = FIRST_NOTE_NUMBER.increment();
        assertEquals((byte) (FIRST_NOTE_NUMBER.value + 1), keyNumber.value);
    }

    @Test
    void incrementWithOverflow() {
        try {
            LAST_NOTE_NUMBER.increment();
            fail();
        } catch (ArithmeticException e) {
        }
    }

    @Test
    void decrementWithoutOverflow() {
        var keyNumber = LAST_NOTE_NUMBER.decrement();
        assertEquals((byte) (LAST_NOTE_NUMBER.value - 1), keyNumber.value);
    }

    @Test
    void decrementWithOverflow() {
        try {
            FIRST_NOTE_NUMBER.decrement();
            fail();
        } catch (ArithmeticException e) {
        }
    }

    @Test
    void equalsTheSameValue() {
        var pianoKeyNumber1 = FIRST_NOTE_NUMBER;
        var pianoKeyNumber2 = FIRST_NOTE_NUMBER;
        assertTrue(pianoKeyNumber1.equals(pianoKeyNumber2));
    }

    @Test
    void equalsDifferentValue() {
        var pianoKeyNumber1 = FIRST_NOTE_NUMBER;
        var pianoKeyNumber2 = FIRST_NOTE_NUMBER.increment();
        assertFalse(pianoKeyNumber1.equals(pianoKeyNumber2));
    }

    @Test
    void equalsNull() {
        var pianoKeyNumber = FIRST_NOTE_NUMBER;
        assertFalse(pianoKeyNumber.equals(null));
    }

    @Test
    void equalsAnotherClass() {
        var pianoKeyNumber = FIRST_NOTE_NUMBER;
        assertFalse(pianoKeyNumber.equals(new Dummy()));
    }

    private record Dummy() {
    };

    // // WAIT, i've remembered why there was no such a toString() logic. because there are several ways to represent the same PianoKeyNumber - e.g. PianoKeyNumber with number 4 can be either B#0 or C1
    // @Test
    // void toStringTest1() {
    //     var pianoKeyNumber = FIRST_NOTE_NUMBER;

    //     var stringedKeyNumber = pianoKeyNumber.toString();

    //     assertEquals("C#1", stringedKeyNumber);
    // }

    // @Test
    // void toStringTest2() {
    //     var pianoKeyNumber = LAST_NOTE_NUMBER;

    //     var stringedKeyNumber = pianoKeyNumber.toString();

    //     assertEquals("B7", stringedKeyNumber);
    // }

}
