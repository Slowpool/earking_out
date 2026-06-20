package org.swetlokognatsk.earking_out.core.domain.model.piano.key;

import static org.junit.Assert.*;
import org.junit.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Invariants.*;

// TODO generalize this test for all value object tests
public final class PianoKeyNumberTest {

    @Test
    public void tooSmallValue() {
        try {
            byte tooSmallKeyNumber = FIRST_NOTE_NUMBER - 1;
            PianoKeyNumber.valueOf(tooSmallKeyNumber);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void tooBigValue() {
        try {
            byte tooBigKeyNumber = (byte) (LAST_NOTE_NUMBER + 1);
            PianoKeyNumber.valueOf(tooBigKeyNumber);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void correctValue1() {
        PianoKeyNumber.valueOf(FIRST_NOTE_NUMBER);
    }

    @Test
    public void correctValue2() {
        PianoKeyNumber.valueOf(LAST_NOTE_NUMBER);
    }

    @Test
    public void equalsTheSameValue() {
        var pianoKeyNumber1 = PianoKeyNumber.valueOf(FIRST_NOTE_NUMBER);
        var pianoKeyNumber2 = PianoKeyNumber.valueOf(FIRST_NOTE_NUMBER);
        assertTrue(pianoKeyNumber1.equals(pianoKeyNumber2));
    }

    @Test
    public void equalsDifferentValue() {
        var pianoKeyNumber1 = PianoKeyNumber.valueOf(FIRST_NOTE_NUMBER);
        var pianoKeyNumber2 = PianoKeyNumber.valueOf(FIRST_NOTE_NUMBER + 1);
        assertFalse(pianoKeyNumber1.equals(pianoKeyNumber2));
    }

    @Test
    public void equalsNull() {
        var pianoKeyNumber = PianoKeyNumber.valueOf(FIRST_NOTE_NUMBER);
        assertFalse(pianoKeyNumber.equals(null));
    }

    @Test
    public void equalsAnotherClass() {
        var pianoKeyNumber = PianoKeyNumber.valueOf(FIRST_NOTE_NUMBER);
        assertFalse(pianoKeyNumber.equals(new Dummy()));
    }

    protected record Dummy() {
    };

}
